import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;import com.sun.accessibility.internal.resources.accessibility;

import clases.*;
public class generarFormulario {
	private MyDataAcces conexion;
	private usuario userr;
	private JFrame frame = new JFrame("Menu Operario");
	private JLabel lblFaltante = new JLabel("FALTANTE: ");
	private JLabel lblcod = new JLabel("CODIGO: ");
	private JLabel lbl1 = new JLabel("");
	private JButton btn = new JButton("ENVIAR");
	private JLabel lbl2 = new JLabel("");
	public generarFormulario(material m , int faltante,usuario usuario) {
		userr = usuario;
		try {
			conexion = new MyDataAcces();
		} catch (InstantiationException | IllegalAccessException e1) {
			new RuntimeException(e1);
		}
		lbl1.setText(String.valueOf(m.getCodigo()));
		lbl2.setText(String.valueOf(faltante));
		frame.add(lblFaltante);
		frame.add(lbl2);
		frame.add(lblcod);
		frame.add(lbl1);
		frame.add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean insert = conexion.ejecutar("insert into formulario values("+m.getCodigo()+","+faltante+")");
				menuOperario m = new menuOperario(usuario);
				
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}
}
