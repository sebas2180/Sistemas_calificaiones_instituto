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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import clases.*;

public class visualizarTareasSupervisor {
	private JFrame frame = new JFrame("Visualizar tareas Grilla");
	private JScrollPane miBarra = new JScrollPane();
	private JTable miTabla= new JTable();
	private MyDataAcces conexion;
	private JButton btnVolver = new JButton("VOLVER A MENU PRINCIPAL");
	private usuario userr;
	public visualizarTareasSupervisor(usuario usuario,String ot){
		userr=usuario;
			crearTabla(ot);
			frame.add(miBarra);
			frame.add(miTabla);
		frame.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuSupervisor menu = new menuSupervisor(null);
				frame.dispose();				
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}

	private void crearTabla(String ot) {
		String[] titulos={"ID OT","Id Tarea","legajo operario","estado"};
		String informacion[][] = obtenerMatriz(ot);
		DefaultTableModel dm=new DefaultTableModel(informacion,titulos);
		miTabla.setModel(dm);
		miBarra.setViewportView(miTabla);
	}

	private String[][] obtenerMatriz(String ot) {
		try {
			conexion = new MyDataAcces();
		} catch (InstantiationException | IllegalAccessException e) {
			
		}
		String[][] informacion=null;
		ordenTrabajo orden = new ordenTrabajo();
		orden.setIdOrden(ot);
		String linea="select * from tareasAsignadas where idOT='"+ot+"'";
		System.out.println(linea);
		ResultSet rs = conexion.GetQuery(linea);
		try {
			ArrayList<paso> pasos = new ArrayList<>();
			while(rs.next()){
				paso aux = new paso();
				aux.setEstado(rs.getString("estado"));
				aux.setOperario(null, null,  rs.getInt("legajoOperario"));
			
				
				aux.setIdTarea(rs.getInt("idTarea"));
				pasos.add(aux);
				
			}
			informacion=new String[pasos.size()][4];
		for (int i = 0; i < informacion.length; i++) {
			informacion[i][0] =orden.getIdOrden()+"";
			informacion[i][1] = pasos.get(i).getIdTarea()+"";
			informacion[i][2] =(pasos.get(i)).getOperario().getLegajo()+"";
			informacion[i][3] =(pasos.get(i)).getEstado()+"";
			
		}
	
		
		} catch (SQLException e1) {
			
		}
	
		
		return informacion;
	}
}
