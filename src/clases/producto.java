package clases;

import java.util.ArrayList;
import java.util.Iterator;

public class producto {
	private String nombre;
	private int codigo;
	private int stock;
	private ArrayList<paso> pasos = new ArrayList<>();
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public int getCodigo() {
		return codigo;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void setCodigo(int i) {
		this.codigo = i;
	}
	public ArrayList<paso> getPasos() {
		return pasos;
	}
	public void setPasos(int idTarea, String nombre,ArrayList<material>materiales) {
		pasos.add(new paso(idTarea,materiales,nombre));
	}
	
	public void setTareaAsiganda(paso aux) {
		pasos.add(aux);
	}
	public producto(String nombre, int codigo) {
		this.nombre = nombre;
		this.codigo = codigo;
	}
	public producto() {
		super();
	}
	
	public Iterator iterator(){
		return pasos.iterator();
		
	}
	public int getSize(){
		return pasos.size();
	}
	

}
