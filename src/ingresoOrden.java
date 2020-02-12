import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

import clases.*;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.mysql.fabric.xmlrpc.base.Value;

public class ingresoOrden {
	
	private JFrame frame = new JFrame("nueva orden de trabajo");
	private int ANCHO_TEXTO=20;
	private MyDataAcces conexion;
	private SimpleDateFormat sdfY= new SimpleDateFormat("YY");
	private SimpleDateFormat sdfY2= new SimpleDateFormat("YYYY");
	private SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdf3= new SimpleDateFormat("yyy/MM/dd");
	private JTextField txtCantidad = new JTextField(ANCHO_TEXTO);
	private JLabel lblCantidad = new JLabel("Cantidad: ");
	private JLabel lblId = new JLabel("Id: ");
	private JLabel lblCodigoP = new JLabel("codigo producto: ");
	private JTextField txtCodigoP = new JTextField(ANCHO_TEXTO);
	private JLabel lblComentario = new JLabel("comentario: ");
	private JTextField txtComentario = new JTextField(ANCHO_TEXTO);
	private JTextField txtId = new JTextField(ANCHO_TEXTO);
	private JLabel lblAlta = new JLabel("Alta: ");
	private JTextField txtAlta = new JTextField(ANCHO_TEXTO);
	private JLabel lblFin = new JLabel("fin estimado(mm-dd-yyyy): ");
	private Calendar fechaActual= GregorianCalendar.getInstance();
	private JComboBox  cbMes  = new JComboBox();
	private JComboBox  cbDia = new JComboBox();
	private JComboBox  cbAnio = new JComboBox();
	private JLabel lblUrgente = new JLabel("Indique si es urgente: ");
	private JRadioButton rdSi = new JRadioButton("SI");
	private JRadioButton rdNo = new JRadioButton("NO");
	private ButtonGroup grupo = new ButtonGroup();
	private JButton btnAceptar = new JButton("ACEPTAR");
	private producto producto = new producto();
	private ordenTrabajo orden = new ordenTrabajo();
	private usuario userr;
	
	public ingresoOrden(usuario usuario){
		userr=usuario;
		cbMes.setModel(new DefaultComboBoxModel(new String[] {"1","2","3","4","5","6","7","8","9","10",
				"11","12"}));
			DefaultComboBoxModel modeloAnio = new DefaultComboBoxModel();
			
			cbMes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String mes = (String) cbMes.getSelectedItem();
					if(mes.equals("1")  ||mes.equals("3")||mes.equals("5")||mes.equals("7")
							|| mes.equals("7") || mes.equals("10") || mes.equals("12")){
						cbDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
									"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
						}
						if(mes.equals("2")){
							cbDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
										"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",}));
							}
						if(mes.equals("4")  ||mes.equals("6")||mes.equals("09")||mes.equals("11")){
							cbDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
										"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"}));
							}
					}
				
			});
			int[] anios=new int[3];
			for(int i=0;i<3;i++){
				anios[i]=Integer.parseInt(sdfY2.format(fechaActual.getTime()))+i;
				modeloAnio.addElement(anios[i]);
			}
			cbAnio.setModel(modeloAnio);
			
		txtAlta.setEditable(false);
		txtId.setEditable(false);
		orden.setAlta(fechaActual);
		txtAlta.setText(sdf.format(orden.getAlta().getTime()));
		try {
			int id=0;
			conexion = new MyDataAcces();
			ResultSet busq = conexion.GetQuery("select * from ot");
			while(busq.next()){
				String aux = busq.getString("id");
				String anioa=aux.substring(4,6);
				System.out.println(anioa+"      "+sdfY.format(fechaActual.getTime()));
				if(anioa.equals(String.valueOf(sdfY.format(fechaActual.getTime())))){
					id++;
				}
			}
		int aux = id+1;
		if(aux<10){
			orden.setIdOrden("00"+aux+"/"+sdfY.format(fechaActual.getTime()));
		}
		if(aux>=10 && aux<100){
			orden.setIdOrden("0"+aux+"/"+sdfY.format(fechaActual.getTime()));
		}
		if(aux>=100){
			orden.setIdOrden(aux+"/"+sdfY.format(fechaActual.getTime()));
		}
		txtId.setText(orden.getIdOrden());
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error");
		}
		
		grupo.add(rdNo);
		grupo.add(rdSi);
		
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)  {
				boolean codigoPOK=false;boolean urgenciaOK=false;boolean fechaFinOK=false;boolean cantidadOK=true;
				orden.setAlta(fechaActual);
				orden.setComentario(txtComentario.getText());
				if(cbMes.getSelectedItem()==null | cbDia.getSelectedItem()==null | cbAnio.getSelectedItem()==null){
					JOptionPane.showMessageDialog(null, "Falta seleccionar la fecha de nacimiento");
				}else{
					Calendar auxF= GregorianCalendar.getInstance();
					auxF.set(Calendar.MONTH, Integer.parseInt((String)cbMes.getSelectedItem()));
					auxF.set(Calendar.YEAR, ((int)cbAnio.getSelectedItem()));
					auxF.set(Calendar.DATE, Integer.parseInt((String)cbDia.getSelectedItem()));
					fechaFinOK=true;	
					orden.setFinEstimado(auxF);
				}
				if(!txtCantidad.equals("")){
					try{
						int cant = Integer.parseInt(txtCantidad.getText());
						if(cant<=0){
							JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero");
						}else{
							cantidadOK=true;
							orden.setCantidad(cant);
						}
					}catch(NumberFormatException eee){
						JOptionPane.showMessageDialog(null, "La cantidad de producto debe ser numerico");
					}
				}else{
					JOptionPane.showMessageDialog(null, "Falta la cantidad");
				}
				if(rdNo.isSelected()==true){
					urgenciaOK=true;
					orden.setEsUrgente(false);
				}else{
					if(rdSi.isSelected()==true){
						urgenciaOK=true;
						orden.setEsUrgente(true);
					}else{
						JOptionPane.showMessageDialog(null, "Falta seleccionar la urgencia");
					}
				}
					
				try {
					conexion = new MyDataAcces();
					if(!txtCodigoP.equals("")){
						try{
							producto.setCodigo(Integer.parseInt(txtCodigoP.getText()));
							ResultSet busqProducto = conexion.GetQuery("SELECT * FROM producto where codigo='"+producto.getCodigo()+"'");
							int i=0;
							while(busqProducto.next()){
								i++;
								producto.setNombre(busqProducto.getString("nombre"));
								System.out.println(producto.getNombre());
								orden.setProducto(producto);
							}
							if(i==0){
								JOptionPane.showMessageDialog(null, "No existe ningun producto con el codigo ingresado");
							}else{
								codigoPOK=true;
							}
						}catch(NumberFormatException ee){
							JOptionPane.showMessageDialog(null, "El codigo de producto debe ser numerico");
						} catch (SQLException e1) {
							new RuntimeException(e1);
						}
					
			}else{
						JOptionPane.showMessageDialog(null, "Falta codigo de producto");
					}
				} catch (InstantiationException | IllegalAccessException e1) {
					new RuntimeException(e1);
				}
				if(cantidadOK==true && codigoPOK==true && fechaFinOK==true && urgenciaOK==true){
					String linea="insert into ot(id,cantidad,idProducto,fechaAlta,finEstimado,esUrgente,comentario,estado,legajoSupervisor) VALUES('"
							+orden.getIdOrden()+"',"+orden.getCantidad()+","+orden.getProducto().getCodigo()+",'"+sdf3.format(orden.getAlta().getTime())+"','"+
							sdf3.format(orden.getFinEstimado().getTime())+"','"+orden.isEsUrgente()+"','"+orden.getComentario()+"','esperando asignacion','"+userr.getEmpleado().getLegajo()+"')";
					System.out.println(linea);
					boolean insert= conexion.ejecutar(linea);
					asignarOperarios ing = new asignarOperarios(userr,orden);
					frame.dispose();
				}
				
			}

	
		});
		frame.add(lblId);
		frame.add(txtId);
		frame.add(lblAlta);
		frame.add(txtAlta);
		frame.add(lblCantidad);
		frame.add(txtCantidad);
		frame.add(lblCodigoP);
		frame.add(txtCodigoP);
		frame.add(lblFin);
		frame.add(cbMes);
		frame.add(cbDia);
		frame.add(cbAnio);
		frame.add(lblUrgente);
		frame.add(rdNo);
		frame.add(rdSi);
		frame.add(btnAceptar);
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}
}
