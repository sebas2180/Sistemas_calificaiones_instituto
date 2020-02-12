package clases;

public class usuario {

	private String usuario;
	private String pass;
	private empleado empleado;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(empleado empleado) {
		this.empleado = empleado;
	}
	public usuario(String usuario, String pass, clases.empleado empleado) {
		super();
		this.usuario = usuario;
		this.pass = pass;
		this.empleado = empleado;
	}
	public usuario() {
		super();
	}
	
	
}
