package clases;

import java.util.ArrayList;
import java.util.Iterator;

public class paso {

	private ArrayList<material> materiales = new ArrayList<>();
	private String descripcion;
	private int idTarea;
	private empleado operario;
	private String estado;
	
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public empleado getOperario() {
		return operario;
	}
	public void setOperario(String nombre,String apellido,int legajo) {
		empleado e = new operario();
		e.setApellido(apellido);
		e.setNombre(nombre);
		e.setLegajo(legajo);
		this.operario = e;
	}
	public paso(int idTarea,ArrayList<material> materiales, String descripcion) {
		super();
		this.materiales = materiales;
		this.descripcion = descripcion;
		this.idTarea = idTarea;
	}
	public paso() {
		super();
	}
	public ArrayList<material> getMateriales() {
		return materiales;
	}
	public void setMateriales(ArrayList<material> materiales) {
		this.materiales = materiales;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdTarea() {
		return idTarea;
	}
	public void setIdTarea(int idTarea) {
		this.idTarea = idTarea;
	}
	public Iterator iterator(){
		return materiales.iterator();
	}
	
}
