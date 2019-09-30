package uniandes.isis2304.epsandes.negocio;


public class TipoSS implements VOTipoSS{
	
	
	private String codigo;
	
	private String nombre;
	
	private boolean requiereReserva;
	
	
	/**
     * Constructor por defecto
     */
	
	public TipoSS() {
		
		codigo = "";
		nombre = "";
		requiereReserva = false;
	}
	
	/**
	 * Constructor con valores
	 * @param id - El id de la consulta
	 * @param tipoConsulta - El tipo de la consulta 
	 * @param ordenPrevia - Si es necesario tener una orden previa para poder hacer uso de la consulta
	 */
	public TipoSS(String codigo, String nombre, boolean requiereReserva) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.requiereReserva = requiereReserva;
	}

	public String codigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String nombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean requiereReserva() {
		return requiereReserva;
	}

	public void setRequiereReserva(boolean requiereReserva) {
		this.requiereReserva = requiereReserva;
	}

	@Override
	public String toString() {
		return "TipoSS [codigo=" + codigo + ", nombre=" + nombre + ", requiereReserva=" + requiereReserva + "]";
	}


	
	

}
