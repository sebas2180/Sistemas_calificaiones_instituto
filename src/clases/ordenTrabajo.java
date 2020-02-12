package clases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class ordenTrabajo {

	private String idOrden;
	private Calendar alta;
	private int cantidad;
	private Calendar finEstimado;
	private String comentario;
	private boolean esUrgente;
	private producto producto;
	
	
	
	
	public ordenTrabajo(String idOrden, Calendar alta, int cantidad, Calendar finEstimado, String comentario,
			boolean esUrgente, clases.producto producto) {
		super();
		this.idOrden = idOrden;
		this.alta = alta;
		this.cantidad = cantidad;
		this.finEstimado = finEstimado;
		this.comentario = comentario;
		this.esUrgente = esUrgente;
		this.producto = producto;
	}



	public producto getProducto() {
		return producto;
	}



	public void setProducto(producto producto) {
		this.producto = producto;
	}



	public ordenTrabajo() {
		super();
	}



	public String getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(String idOrden) {
		this.idOrden = idOrden;
	}
	public Calendar getAlta() {
		return alta;
	}
	public void setAlta(Calendar alta) {
		this.alta = alta;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Calendar getFinEstimado() {
		return finEstimado;
	}
	public void setFinEstimado(Calendar finEstimado) {
		this.finEstimado = finEstimado;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public boolean isEsUrgente() {
		return esUrgente;
	}
	public void setEsUrgente(boolean esUrgente) {
		this.esUrgente = esUrgente;
	}
	
	
}
