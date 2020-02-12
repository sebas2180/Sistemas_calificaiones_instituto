import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clases.usuario;

public class menuSupervisor {
	
	private JFrame frame = new JFrame("menu Supervisor");
	private int ANCHO_TEXTO=20;
	private JButton boton1 = new JButton("NUEVA ORDEN DE TRABAJO");
	private usuario userr;
	private JButton boton2 = new JButton("CONSULTAR TAREAS ASIGNADAS");
	private JButton boton3 = new JButton("ACTUALIZAR PROCEDIMIENTO");
	public menuSupervisor(usuario usuario){
		userr=usuario;
		frame.add(boton1);
		frame.add(boton2);
		frame.add(boton3);
		boton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizacionTareasDsdArchivo  ing = new actualizacionTareasDsdArchivo(userr);
				frame.dispose();				
			}
		});
		boton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ot=null;
				ot= JOptionPane.showInputDialog("Introduce el id de orden de trabajo");
				visualizarTareasSupervisor  ing = new visualizarTareasSupervisor(userr,ot);
				frame.dispose();
				
			}
		});
		boton1.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				ingresoOrden  ing = new ingresoOrden(userr);
				frame.dispose();
				
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}

}
