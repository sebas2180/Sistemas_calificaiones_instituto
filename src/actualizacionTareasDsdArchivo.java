import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import clases.*;

public class actualizacionTareasDsdArchivo {
	private JFrame frame = new JFrame("menu Supervisor");
	private int ANCHO_TEXTO=20;
	private usuario userr;
	public actualizacionTareasDsdArchivo(usuario usuario){
		ArrayList<paso> actualizacioones = new ArrayList<>();
		String nombre=null;
		nombre= JOptionPane.showInputDialog("Introduce el nombre del archivo desde donde se actualizara");
		File archivo= new File(nombre+".txt");
		if(archivo.exists()){
			try {
				Scanner scArc = new Scanner(archivo);
				while(scArc.hasNextLine()){
					String linea=scArc.nextLine();
					String[] subLinea = linea.split(";");
					ArrayList<material> materialesActualizacion = new ArrayList<>();
					paso actualizacionAux = new paso();
						actualizacionAux.setDescripcion(subLinea[1]);
						actualizacionAux.setIdTarea(Integer.parseInt(subLinea[0]));
						if(!subLinea[2].equals("null")){
							materialesActualizacion.add(new material(subLinea[2], Integer.parseInt(subLinea[3])));
						}
						if(!subLinea[4].equals("null")){
							materialesActualizacion.add(new material(subLinea[4], Integer.parseInt(subLinea[5])));
						}
						if(!subLinea[6].equals("null")){
							materialesActualizacion.add(new material(subLinea[6], Integer.parseInt(subLinea[7])));
						}
					actualizacionAux.setMateriales(materialesActualizacion);
					actualizacioones.add(actualizacionAux);
				}
				scArc.close();
						File pasosArc= new File("pasos.txt");
						ArrayList<producto> productos = new ArrayList<>();
						if(pasosArc.exists()){
							Scanner scPasos = new Scanner(pasosArc);
							while(scPasos.hasNextLine()){
								ArrayList<material> materiales = new ArrayList<>();
								String line= scPasos.nextLine();
								System.out.println(line);
								producto aux = new producto();
								aux.setNombre(line.substring(0,20));
								if(!line.substring(48,52).equals("null")){
									materiales.add(new material(line.substring(48,68), Integer.parseInt(line.substring(68,71))));
								}
								if(!line.substring(71,75).equals("null")){
									materiales.add(new material(line.substring(71,91), Integer.parseInt(line.substring(91,94))));
								}
								if(!line.substring(94,98).equals("null")){
									materiales.add(new material(line.substring(94,114), Integer.parseInt(line.substring(114,117))));
								}
								int i=0;
								for (producto p : productos) {
									if(p.getNombre().equals(aux.getNombre())){
										p.setPasos(Integer.parseInt(line.substring(20,23)), line.substring(23,48), materiales);
									}
								}
								if(i==0){
									aux.setPasos(Integer.parseInt(line.substring(20,23)), line.substring(23,48), materiales);
									productos.add(aux);
								}
							}
							scPasos.close();
							FileWriter wtFile = new FileWriter("pasos.txt");
							PrintWriter pwFile = new PrintWriter(wtFile);
							for (producto producto : productos) {
								String escribir=calcularEspacios20(producto.getNombre());
								for (Iterator iterator = producto.iterator(); iterator.hasNext();) {
									paso pasoo = (paso) iterator.next();
									for (paso act: actualizacioones) {
										if(act.getIdTarea()==pasoo.getIdTarea()){
											pasoo=act;
										}
									}
									if(pasoo.getIdTarea()<10){
										escribir=escribir+"00"+pasoo.getIdTarea()+calcularEspacios25(pasoo.getDescripcion());
																			
									}else{
										if(pasoo.getIdTarea()>=10 && pasoo.getIdTarea()<100){
											escribir=escribir+"0"+pasoo.getIdTarea()+calcularEspacios25(pasoo.getDescripcion());
										}else{
											escribir=escribir+pasoo.getIdTarea()+calcularEspacios25(pasoo.getDescripcion());
										}
									}
									int u=0;
									for (Iterator iterator2 = pasoo.iterator(); iterator2.hasNext();) {
										material mat = (material) iterator2.next();
										u++;
										escribir=escribir+calcularEspacios20(mat.getNombre());
										if(mat.getCantidad()<10){
											escribir=escribir+"00"+mat.getCantidad();
										}else{
											if(mat.getCantidad()>=10 && mat.getCantidad()<100){
												escribir=escribir+"0"+mat.getCantidad();
											}else{
												escribir=escribir+mat.getCantidad();
											}
										}
									}
									for (int i = 0; i < 3-u; i++) {
										escribir=escribir+calcularEspacios20("null")+"   ";
									}
									pwFile.println(escribir);
								}
							}
							pwFile.close();
							wtFile.close();
							JOptionPane.showMessageDialog(null, "SE HA ACTUALIZADO LAS TAREAS CON EXITO");
							menuSupervisor menu = new menuSupervisor(userr);
							frame.dispose();
						}else{
							JOptionPane.showMessageDialog(null, "ERROR. No se ha encontrado el archivo de pasos");
							menuSupervisor menu = new menuSupervisor(userr);
							frame.dispose();
						}
			
			} catch (FileNotFoundException e) {
				new RuntimeException(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}else{
			JOptionPane.showMessageDialog(null, "no se ha encontrado el archivo");
			
			frame.dispose();
		}
		
		userr=usuario;
		frame.setLayout(new FlowLayout());
		frame.setSize(800,800);
		frame.setVisible(true);
	}
	public String calcularEspacios25(String linea){
		int longitud = linea.length();
		int restante = 25-longitud;
		for (int i = 0; i < restante; i++) {
			linea=linea+"";
		}
		return linea;	
	}
	public String calcularEspacios20(String linea){
		int longitud = linea.length();
		int restante = 20-longitud;
		for (int i = 0; i < restante; i++) {
			linea=linea+" ";
		}
		return linea;	
	}
}
