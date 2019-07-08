package py.com.perseo.utilities;

public class Respuesta {

	String estado;
	String error;
	Object datos;

	public Respuesta() {
	}

	public Respuesta(String estado) {
		this.estado=estado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getDatos() {
		return datos;
	}

	public void setDatos(Object datos) {
		this.datos = datos;
	}

}
