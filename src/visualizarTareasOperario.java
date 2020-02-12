import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import clases.*;

public class visualizarTareasOperario {
	private JScrollPane miBarra = new JScrollPane();
	private JTable miTabla= new JTable();
	private MyDataAcces conexion;
	private usuario userr;
	private JButton btnVolver = new JButton("VOLVER A MENU PRINCIPAL");
	private JFrame frame = new JFrame("visualizar mis tareas");
	private int ANCHO_TEXTO=20;
	public visualizarTareasOperario(usuario usuario){
		userr=usuario;
		crearTabla();
		frame.add(miBarra);
		frame.add(miTabla);
	frame.add(btnVolver);
	btnVolver.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			menuOperario menu = new menuOperario(userr);
			frame.dispose();				
		}
	});
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
		
	}
	
	private void crearTabla() {
		String[] titulos={"ID OT","Id Tarea","legajo operario","estado"};
		String informacion[][] = obtenerMatriz();
		DefaultTableModel dm=new DefaultTableModel(informacion,titulos);
		miTabla.setModel(dm);
		miBarra.setViewportView(miTabla);
		
	}
	private String[][] obtenerMatriz() {
		int ii=0;
		String[][] informacion=null;
		try {
			conexion = new MyDataAcces();
		} catch (InstantiationException | IllegalAccessException e) {
			
		}
		ArrayList<ordenTrabajo> ordenes = new ArrayList<>();
		ResultSet rsTareas = conexion.GetQuery("select* from tareasAsignadas where legajoOperario='"+userr.getEmpleado().getLegajo()+"'");
		try {
			while(rsTareas.next()){
				System.out.println("tarea encontrada   :"+rsTareas.getInt("idTarea"));
				paso aux = new paso();
				aux.setEstado(rsTareas.getString("estado"));
				aux.setOperario(null, null,  rsTareas.getInt("legajoOperario"));
				aux.setIdTarea(rsTareas.getInt("idTarea"));
				int i=0;
				for (ordenTrabajo o : ordenes) {
					if(o.getIdOrden().equals(rsTareas.getString("idOT"))){
						o.getProducto().setTareaAsiganda(aux);
						System.out.println("seteo la orden vieja");
						i++;
						ii++;
					}
				}
				if(i==0){
					ordenTrabajo auxOrden = new ordenTrabajo();
					System.out.println("seteo la orden nueva");
					auxOrden.setIdOrden(rsTareas.getString("idOT"));
					producto auxP = new producto();
					auxP.setTareaAsiganda(aux);
					auxOrden.setProducto(auxP);
					ordenes.add(auxOrden);
					ii++;
				}
			}
			informacion=new String[ii][4];
			int t=0;
			for (ordenTrabajo orden : ordenes) {
				int j=0;
				for (paso paso : orden.getProducto().getPasos()) {
					informacion[t][0] =orden.getIdOrden()+"";
					informacion[j][1] =paso.getIdTarea()+"";
					informacion[j][2] =paso.getOperario().getLegajo()+"";
					informacion[j][3] =paso.getEstado()+"";
					j++;
				}
				t++;
			}

		} catch (SQLException e) {
			new RuntimeException(e);
		}
		return informacion;
	}

}
