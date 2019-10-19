package uniandes.isis2304.epsandes.negocio;


public class Consulta implements VOConsulta{
	
	
	private long id;
	
	private String esAfiliado;
	
	private String ordenPrevia;
	
	private long idIPS;
	
	private int capacidad;
	
	private String horaInicio;
	
	private String horaFin;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String diaInicio;
	
	private String diaFin;
	
	private long idRecepcionista;
	
	/**
     * Constructor por defecto
     */
	public Consulta() {
		
		ordenPrevia = "N";
		id = 0;
		esAfiliado = "N";
		idIPS = 0;
		capacidad = 0;
		horaInicio = "";
		horaFin = "";
		fechaInicio = "";
		fechaFin = "";
		diaInicio = "";
		diaFin = "";
		idRecepcionista = 0;
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El id de la consulta
	 * @param tipoConsulta - El tipo de la consulta 
	 * @param ordenPrevia - Si es necesario tener una orden previa para poder hacer uso de la consulta
	 */
    public Consulta(long id, String esAfiliado, String ordenPrevia, long idIPS, int capacidad, 
    		String horaInicio, String horaFin, String fechaInicio, String fechaFin, 
    		String diaInicio, String diaFin, long idRecepcionista) 
    {
    	this.id = id;
    	this.esAfiliado = esAfiliado;
    	this.ordenPrevia = ordenPrevia;
    	this.idIPS = idIPS;
    	this.capacidad = capacidad;
    	this.horaInicio = horaInicio;
    	this.horaFin = horaFin;
    	this.fechaInicio = fechaInicio;
    	this.fechaFin = fechaFin;
    	this.diaInicio = diaInicio;
    	this.diaFin = diaFin;
    	this.idRecepcionista = idRecepcionista;
		
	}
	
	
	

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getOrdenPrevia() {
		// TODO Auto-generated method stub
		return ordenPrevia;
	}
	

	@Override
	public String getEsAfiliado() {
		// TODO Auto-generated method stub
		return esAfiliado;
	}


	@Override
	public long getIdIPS() {
		// TODO Auto-generated method stub
		return idIPS;
	}


	@Override
	public int getCapacidad() {
		// TODO Auto-generated method stub
		return capacidad;
	}

	
	@Override
	public String getHoraInicio() {
		// TODO Auto-generated method stub
		return horaInicio;
	}


	@Override
	public String getHoraFin() {
		// TODO Auto-generated method stub
		return horaFin;
	}


	@Override
	public String getFechaInicio() {
		// TODO Auto-generated method stub
		return fechaInicio;
	}


	@Override
	public String getFechaFin() {
		// TODO Auto-generated method stub
		return fechaFin;
	}


	@Override
	public String getDiaInicio() {
		// TODO Auto-generated method stub
		return diaInicio;
	}


	@Override
	public String getDiaFin() {
		// TODO Auto-generated method stub
		return diaFin;
	}


	@Override
	public long getIdRecepcionistaIPS() {
		// TODO Auto-generated method stub
		return idRecepcionista;
	}


	@Override
	public String toString() {
		return "Consulta [id=" + id + ", esAfiliado=" + esAfiliado + ", ordenPrevia=" + ordenPrevia + ", idIPS=" + idIPS
				+ ", capacidad=" + capacidad + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", diaInicio=" + diaInicio + ", diaFin=" + diaFin
				+ ", idRecepcionista=" + idRecepcionista + "]";
	}


}
