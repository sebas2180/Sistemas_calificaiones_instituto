import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import clases.*;
import javax.swing.JTextField;

public class login {

	private JFrame frame = new JFrame("Login");
	private int ANCHO_TEXTO=20;
	private JLabel lblMensaje = new JLabel("Ingrese los datos para logearse como operario o supervisor");
	private JTextField txtUsuario = new JTextField(ANCHO_TEXTO);
	private JLabel lblUsuario = new JLabel("USUARIO");
	private JPasswordField txtPass = new JPasswordField(ANCHO_TEXTO);
	private JLabel lblContrasenia = new JLabel("CONTRASENIA");
	private JButton boton = new JButton("INGRESAR");
	private MyDataAcces conexion;
	public login(){
		frame.add(lblMensaje);
		frame.add(lblUsuario);
		frame.add(txtUsuario);
		frame.add(lblContrasenia);
		frame.add(txtPass);
		frame.add(boton);
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					usuario usuario = new usuario();
					if(!txtPass.getText().equals("") && !txtUsuario.getText().equals("")){
						usuario.setPass(txtPass.getText());
						usuario.setUsuario(txtUsuario.getText());
						conexion = new MyDataAcces();
						ResultSet busq= conexion.GetQuery("SELECT * FROM usuario where usuario='"+usuario.getUsuario()+"' and pass='"+usuario.getPass()+"'");
						int i=0;
						while(busq.next()){
							i++;
							if(busq.getString("tipoEmpleado").equalsIgnoreCase("operario")){
								empleado empl = new operario();
								empl.setLegajo(busq.getInt("legajo"));
								usuario.setEmpleado(empl);
								menuOperario menu = new menuOperario(usuario);
								frame.dispose();
							}else{
								empleado empl = new supervisor();
								usuario.setEmpleado(empl);
								empl.setLegajo(busq.getInt("legajo"));
								usuario.setEmpleado(empl);
								menuSupervisor menu = new menuSupervisor(usuario);
								frame.dispose();
							}
						}
						if(i==0){
							JOptionPane.showMessageDialog(null,"usuario/pass incorrectos");
						}
					}
				} catch (InstantiationException | IllegalAccessException | SQLException e1) {
					new RuntimeException();
				}
				
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}
}
