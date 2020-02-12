import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.management.RuntimeErrorException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clases.*;
import clases.ordenTrabajo;

public class asignarOperarios {

	private JLabel lblTarea1 = new JLabel("");
	private JLabel lblTarea2 = new JLabel("");
	private JLabel lblTarea3 = new JLabel("");
	private JLabel lblTarea4 = new JLabel("");
	private JLabel lblTarea5 = new JLabel("");
	private JTextField txtTarea1 = new JTextField(20);
	private JTextField txtTarea2 = new JTextField(20);
	private JTextField txtTarea3 = new JTextField(20);
	private JTextField txtTarea4 = new JTextField(20);
	private JTextField txtTarea5 = new JTextField(20);
	private JLabel mensaje1 = new JLabel("");
	private JLabel mensaje2 = new JLabel("");
	private JLabel mensaje3 = new JLabel("");
	
	private JLabel mensaje4 = new JLabel("");
	private JLabel mensaje5 = new JLabel("");
	private JFrame frame = new JFrame("asignacion supervisor");
	private int ANCHO_TEXTO=20;
	private MyDataAcces conexion ;
	private JButton btnAceptar = new JButton("ACEPTAR");
	private ordenTrabajo orden  = null;
	private usuario userr;
	private JButton btnVolver = new JButton("VOLVER A MENU PRINCIPAL");
	public asignarOperarios(usuario usuario,ordenTrabajo ot) {
		userr=usuario;
		orden=ot;
		try {
			conexion = new MyDataAcces();
		} catch (InstantiationException | IllegalAccessException e1) {
			
		}
		File arcPasos = new File("pasos.txt");
		if(arcPasos.exists()){
			try {
				Scanner scPasos = new Scanner(arcPasos);
				while(scPasos.hasNextLine()){
					String linea = scPasos.nextLine();
					System.out.println(linea);
					int top=linea.length();
					String nombre = linea.substring(0,20);
					String nombreP=calcularNombre(orden.getProducto().getNombre());
					System.out.println(nombre+"______"+nombreP);
					if(nombre.equalsIgnoreCase(nombreP)){
						int pos=20;
						int idTarea=Integer.parseInt(linea.substring(pos,pos+3));
						pos=pos+3;
							String descripcion = linea.substring(pos,pos+25);
							System.out.println(descripcion+"  "+descripcion.length());
							pos=pos+25;
							ArrayList<material> materiales = new ArrayList<>();
							while(top>pos){
							System.out.println(linea.substring(pos,pos+4));
							if(!linea.substring(pos,pos+4).equals("null")){
								System.out.println(linea.substring(pos,pos+20)+"     "+linea.substring(pos+20,pos+23));
								material aux1 = new material(linea.substring(pos,pos+20),Integer.parseInt(linea.substring(pos+20,pos+23)));
								pos= pos+23;
								System.out.println(aux1.getCantidad()+"   "+aux1.getNombre());
								materiales.add(aux1);
						}else{
							pos=top;
						}
					}
							orden.getProducto().setPasos(idTarea,descripcion, materiales);
					}

				}
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("______________________");
		int i=0;
		for (Iterator iterator = orden.getProducto().iterator(); iterator.hasNext();) {
			paso p = (paso) iterator.next();
			i++;
			if(i==0){
				lblTarea1.setText(p.getDescripcion()+": ");
				frame.add(lblTarea1);
				frame.add(txtTarea1);
				frame.add(mensaje1);
			}
			if(i==1){
				lblTarea2.setText(p.getDescripcion()+": ");
				frame.add(lblTarea2);
				frame.add(txtTarea2);
				frame.add(mensaje2);
			}
			if(i==2){
				lblTarea3.setText(p.getDescripcion()+": ");
				frame.add(lblTarea3);
				frame.add(txtTarea3);
				frame.add(mensaje3);
			}
			if(i==3){
				lblTarea4.setText(p.getDescripcion()+": ");
				frame.add(lblTarea4);
				frame.add(txtTarea4);
				frame.add(mensaje4);
			}
			if(i==4){
				lblTarea5.setText(p.getDescripcion()+": ");
				frame.add(lblTarea5);
				frame.add(txtTarea5);
				frame.add(mensaje5);
			}
			
		}
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int cantPasos = orden.getProducto().getSize();
				int j=0;
				for (Iterator iterator = orden.getProducto().iterator(); iterator.hasNext();) {
					paso p = (paso) iterator.next();
					if(j==0){
						if(!txtTarea1.equals("")){
							try{
								int legajo1=Integer.parseInt(txtTarea1.getText());
								mensaje1.setText("");int i1=0;
								ResultSet r1= conexion.GetQuery("select* from empleado where legajo='"+legajo1+"' and tipoEmpleado='operario'");
								while(r1.next()){
									i1=1;
									p.setOperario(r1.getString("nombre"), r1.getString("apellido"), r1.getInt("legajo"));
									ResultSet bsq= conexion.GetQuery("select * from tareasAsignadas where idTarea='"+p.getIdTarea()+"' and idOT='"+orden.getIdOrden()+"'");
									int i=0;
									while(bsq.next()){
										i++;
									}
									if(i==0){
										String linea = "insert into tareasAsignadas(idTarea,legajoOperario,idOT,estado) VALUES("+p.getIdTarea()+","+p.getOperario().getLegajo()+",'"+orden.getIdOrden()+"','sin empezar')";
										boolean guardar=conexion.ejecutar(linea);
									}
			
								}
								if(i1==0){
									mensaje1.setText("legajo no existente");
								}
								}catch(NumberFormatException a1){
									mensaje1.setText("dato invalido");
								} catch (SQLException e1) {
									
								}
						}else{
							mensaje1.setText("falta completar");
						}
					}
					//...
					if(j==1){
						if(!txtTarea2.equals("")){
							try{
								int legajo2=Integer.parseInt(txtTarea2.getText());
								mensaje2.setText("");int i1=0;
								ResultSet r2= conexion.GetQuery("select* from empleado where legajo='"+legajo2+"' and tipoEmpleado='operario'");
								while(r2.next()){
									i1=1;
									p.setOperario(r2.getString("nombre"), r2.getString("apellido"), r2.getInt("legajo"));
						
									int i=0;
									ResultSet bsq= conexion.GetQuery("select * from tareasAsignadas where idTarea='"+p.getIdTarea()+"' and idOT='"+orden.getIdOrden()+"'");
									while(bsq.next()){
										i++;
									}
									if(i==0){
										String linea = "insert into tareasAsignadas(idTarea,legajoOperario,idOT,estado)  VALUES("+p.getIdTarea()+","+p.getOperario().getLegajo()+",'"+orden.getIdOrden()+"','sin empezar')";
										System.out.println(linea);
										boolean guardar=conexion.ejecutar(linea);
									}
								}
								if(i1==0){
									mensaje2.setText("legajo no existente");
								}
								}catch(NumberFormatException a1){
									mensaje2.setText("dato invalido");
								} catch (SQLException e1) {
									
								}
						}else{
							mensaje2.setText("falta completar");
						}
					}
					if(j==2){
						if(!txtTarea3.equals("")){
							try{
								int legajo3=Integer.parseInt(txtTarea3.getText());
								mensaje3.setText("");int i1=0;
								ResultSet r3= conexion.GetQuery("select* from empleado where legajo='"+legajo3+"' and tipoEmpleado='operario'");
								while(r3.next()){
									i1=1;
									p.setOperario(r3.getString("nombre"), r3.getString("apellido"), r3.getInt("legajo"));
									
									int i=0;
									ResultSet bsq= conexion.GetQuery("select * from tareasAsignadas where idTarea='"+p.getIdTarea()+"' and idOT='"+orden.getIdOrden()+"'");
									while(bsq.next()){
										i++;
									}
									if(i==0){
										String linea = "insert into tareasAsignadas(idTarea,legajoOperario,idOT,estado) VALUES("+p.getIdTarea()+","+p.getOperario().getLegajo()+",'"+orden.getIdOrden()+"','sin empezar')";
										System.out.println(linea);
										boolean guardar=conexion.ejecutar(linea);
									}
								}
								if(i1==0){
									mensaje3.setText("legajo no existente");
								}
								}catch(NumberFormatException a1){
									mensaje3.setText("dato invalido");
								} catch (SQLException e1) {
									
								}
						}else{
							mensaje3.setText("falta completar");
						}
					}
					//...
					if(j==3){
						if(!txtTarea4.equals("")){
							try{
								int legajo4=Integer.parseInt(txtTarea4.getText());
								mensaje4.setText("");int i1=0;
								ResultSet r4= conexion.GetQuery("select* from empleado where legajo='"+legajo4+"' and tipoEmpleado='operario'");
								while(r4.next()){
									i1=1;
									int i=0;
									ResultSet bsq= conexion.GetQuery("select * from tareasAsignadas where idTarea='"+p.getIdTarea()+"' and idOT='"+orden.getIdOrden()+"'");
									while(bsq.next()){
										i++;
									}
									if(i==0){
										String linea = "insert into tareasAsignadas(idTarea,legajoOperario,idOT,estado) VALUES("+p.getIdTarea()+","+p.getOperario().getLegajo()+",'"+orden.getIdOrden()+"','sin empezar')";
										System.out.println(linea);
										boolean guardar=conexion.ejecutar(linea);
									}
									p.setOperario(r4.getString("nombre"), r4.getString("apellido"), r4.getInt("legajo"));
									
								}
								if(i1==0){
									mensaje4.setText("legajo no existente");
								}
								}catch(NumberFormatException a1){
									mensaje4.setText("dato invalido");
								} catch (SQLException e1) {
									
								}
						}else{
							mensaje4.setText("falta completar");
						}
					}
					//...
					if(j==4){
						if(!txtTarea5.equals("")){
							try{
								int legajo5=Integer.parseInt(txtTarea5.getText());
								mensaje5.setText("");int i1=0;
								ResultSet r5= conexion.GetQuery("select* from empleado where legajo='"+legajo5+"' and tipoEmpleado='operario'");
								while(r5.next()){
									i1=1;
									p.setOperario(r5.getString("nombre"), r5.getString("apellido"), r5.getInt("legajo"));
									int i=0;
									ResultSet bsq= conexion.GetQuery("select * from tareasAsignadas where idTarea='"+p.getIdTarea()+"' and idOT='"+orden.getIdOrden()+"'");
									while(bsq.next()){
										i++;
									}
									if(i==0){
										String linea = "insert into tareasAsignadas(idTarea,legajoOperario,idOT,estado) VALUES("+p.getIdTarea()+","+p.getOperario().getLegajo()+",'"+orden.getIdOrden()+"','sin empezar')";
										System.out.println(linea);
										boolean guardar=conexion.ejecutar(linea);
									}
								}
								if(i1==0){
									mensaje5.setText("legajo no existente");
								}
								}catch(NumberFormatException a1){
									mensaje5.setText("dato invalido");
								} catch (SQLException e1) {
									
								}
						}else{
							mensaje5.setText("falta completar");
						}
					}
					//...
					j++;
				}
				
			if(mensaje1.getText().equals("") && mensaje2.getText().equals("") && mensaje3.getText().equals("") && mensaje4.getText().equals("") && mensaje5.getText().equals("") ){
				JOptionPane.showMessageDialog(null, "guardado exitoso");
				menuSupervisor m = new menuSupervisor(userr);
				frame.dispose();
			}
			}
		});
		frame.add(btnAceptar);
		frame.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuSupervisor menu = new menuSupervisor(userr);
				frame.dispose();				
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}
	public String calcularNombre(String linea){
		int largo=20-linea.length();
		for (int i = 0; i < largo; i++) {
			linea=linea+" ";
		}
		return linea;
	}
}
