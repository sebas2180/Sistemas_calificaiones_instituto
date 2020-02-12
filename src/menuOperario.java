import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import clases.usuario;

public class menuOperario {

	private JFrame frame = new JFrame("Menu Operario");
	private JButton btn1 = new JButton("INICIAR O FINALIZAR TAREA");
	private JButton btn2 = new JButton("CONSULTAR MIS TAREAS");
	private int ANCHO_TEXTO=20;
	private usuario userr;
	
	public menuOperario(usuario usuario){
		userr=usuario;
		frame.add(btn1);
		frame.add(btn2);
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualizarTareasOperario visualizar= new visualizarTareasOperario(userr);
				
			}
		});
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					realizarPaso rp= new realizarPaso(userr);
				} catch (InstantiationException | IllegalAccessException e1) {
				}
				
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}
	
}
