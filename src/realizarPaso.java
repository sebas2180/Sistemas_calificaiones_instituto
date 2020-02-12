import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import clases.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

public class realizarPaso {
	private JLabel leyenda = new JLabel("INGRESE EL NUMERO DE TAREA A INICIAR O FINALIZAR");
	private JLabel lblIdAsignacion = new JLabel();
	private JTextField txtID = new JTextField(7);
	private MyDataAcces conexion ;
	private JButton btnBuscar = new JButton("BUSCAR");
	private JButton btnEmpezar = new JButton("EMPEZAR");
	private JButton btnTerminar = new JButton("TERMINAR");
	private usuario userr;
	private JLabel mensaje1 = new JLabel("");
	private JFrame frame = new JFrame("iniciar o finalizar tarea");
	public realizarPaso(usuario usuario) throws InstantiationException, IllegalAccessException{
		userr=usuario;
		conexion = new MyDataAcces();
		frame.add(leyenda);
		frame.add(lblIdAsignacion);
		frame.add(txtID);
		frame.add(mensaje1);
		frame.add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ordenTrabajo orden = new ordenTrabajo();
				try{
					frame.add(btnEmpezar);btnEmpezar.setEnabled(false);
					frame.add(btnTerminar);btnTerminar.setEnabled(false);
					int id=Integer.parseInt(txtID.getText());
					mensaje1.setText("");
					ResultSet rs= conexion.GetQuery("select * from tareasAsignadas where idAsignacion='"+id+"' and legajoOperario='"+userr.getEmpleado().getLegajo()+"'");
					paso tarea = new paso();
					int i=0;
					while(rs.next()){
						orden.setIdOrden(rs.getString("idOT"));
						i++;
						tarea.setIdTarea(rs.getInt("idTarea"));
						tarea.setEstado( rs.getString("estado"));
						
						if(tarea.getEstado().equals("sin empezar")){
							btnEmpezar.setEnabled(true);
						}
						if(tarea.getEstado().equals("En proceso")){
							btnTerminar.setEnabled(true);
						}
						if(tarea.getEstado().equals("terminado")){
							JOptionPane.showMessageDialog(null, "esta actividad ya se encuentra finaliza");
							menuOperario menu = new menuOperario(userr);
							frame.dispose();
						}
						btnTerminar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								boolean c = conexion.ejecutar("UPDATE tareasAsignadas SET estado='terminado' where idAsignacion='"+id+"' ");
								ResultSet rsB = conexion.GetQuery("select* from tareasAsignadas where idOT='"+orden.getIdOrden()+"'");
								boolean isOrdenFinalizada=true;
								try {
									while(rsB.next()){
										paso pasoAux = new paso();
										pasoAux.setEstado(rsB.getString("estado"));
										if(!pasoAux.equals("finalizado")){
											isOrdenFinalizada=false;
										}
									}
									if(isOrdenFinalizada==true){
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
										Calendar fechaactual = GregorianCalendar.getInstance();
										boolean cc = conexion.ejecutar("UPDATE OT SET estado='terminado' and anioFinalizacion='"+
															sdf.format(fechaactual.getTime())+"' where id='"+orden.getIdOrden()+"' ");
									}
								} catch (SQLException e1) {
									new RuntimeException(e1);
								}
								menuOperario menu = new menuOperario(userr);
								frame.dispose();
							}
						});
						btnEmpezar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								boolean actualizarOK=false;
								File arcPasos = new File("pasos.txt");
								if(arcPasos.exists()){
									try {
										Scanner scPasos = new Scanner(arcPasos);
										while(scPasos.hasNextLine()){
											String linea = scPasos.nextLine();
											System.out.println(linea);
											int top=linea.length();
											String nombre = linea.substring(0,20);
												int pos=20;
												int idTarea=Integer.parseInt(linea.substring(pos,pos+3));
												pos=pos+3;
												System.out.println(tarea.getIdTarea()+"   "+idTarea);
												if(tarea.getIdTarea()==idTarea){
													String descripcion = linea.substring(pos,pos+25);
													System.out.println(descripcion+"  "+descripcion.length());
													pos=pos+25;
													ArrayList<material> materiales = new ArrayList<>();
													while(top>pos){
													if(!linea.substring(pos,pos+4).equals("null")){
														System.out.println(linea.substring(pos,pos+20)+"     "+linea.substring(pos+20,pos+23));
														material aux1 = new material(linea.substring(pos,pos+20),Integer.parseInt(linea.substring(pos+20,pos+23)));
														ResultSet rsStock = conexion.GetQuery("select * from stock where nombreProducto='"+aux1.getNombre()+"'");
														int y=0;int codigo=0;
														while(rsStock.next()){
															producto prod = new producto();
															prod.setCodigo(rsStock.getInt("codigo"));
															y++;codigo=rsStock.getInt("codigo");
															prod.setStock(rsStock.getInt("stock"));
															if(prod.getStock()<aux1.getCantidad()){
																int faltante = aux1.getCantidad()-prod.getStock();
																JOptionPane.showMessageDialog(null, "stock faltante, generar formulario");
																material m = new material();
																m.setCodigo(codigo);
																generarFormulario gf= new generarFormulario(m,faltante,userr);
																frame.dispose();
															}else{
																int restante = aux1.getCantidad()-prod.getStock();
																conexion.ejecutar("UPDATE stock SET stock='"+restante+"' where codigo='"+prod.getCodigo()+"'");
															}
														}
														if(y==0){
															int faltante = aux1.getCantidad();
															JOptionPane.showMessageDialog(null, "stock faltante,, generar formulario");
															material m = new material();
															m.setCodigo(codigo);
															generarFormulario gf= new generarFormulario(m,faltante,userr);
															frame.dispose();
														}else{
															actualizarOK=true;
														}
														pos= pos+23;
														System.out.println(aux1.getCantidad()+"   "+aux1.getNombre());
														materiales.add(aux1);
												}else{
													pos=top;
												}
											}
										}									
										}
										if(actualizarOK==true){
											boolean c = conexion.ejecutar("UPDATE tareasAsignadas SET estado='en proceso' where idAsignacion='"+id+"' ");
											JOptionPane.showMessageDialog(null, "ACTIVIDAD EMPEZADA CON EXITO");
											menuOperario menu = new menuOperario(userr);
											frame.dispose();
										}
									}catch (FileNotFoundException | SQLException we) {
									System.out.println(we.getMessage());
									new RuntimeException(we);
									}
								}
								
							}
						});
						
					}
					if(i==0){
						mensaje1.setText("No se han encontrado tareas con dicho id de asignacion");
					}
				}catch(NumberFormatException ee){
					mensaje1.setText("dato invalido");
				} catch (SQLException e1) {
					new RuntimeException(e1);
				}
				
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}
}
