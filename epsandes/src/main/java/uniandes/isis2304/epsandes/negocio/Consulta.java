package uniandes.isis2304.epsandes.negocio;


public class Consulta implements VOConsulta{
	
	
	public long ID;
	
	public String ESAFILIADO;
	
	public String ORDENPREVIA;
	
	public long IDIPS;
	
	public int CAPACIDAD;
	
	public String HORAINICIO;
	
	public String HORAFIN;
	
	public String FECHAINICIO;
	
	public String FECHAFIN;
	
	public String DIAINICIO;
	
	public String DIAFIN;
	
	public long IDRECEPCIONISTA;
	
	/**
     * Constructor por defecto
     */
	public Consulta() {
		
		ORDENPREVIA = "N";
		ID = 0;
		ESAFILIADO = "N";
		IDIPS = 0;
		CAPACIDAD = 0;
		HORAINICIO = "";
		HORAFIN = "";
		FECHAINICIO = "";
		FECHAFIN = "";
		DIAINICIO = "";
		DIAFIN = "";
		IDRECEPCIONISTA = 0;
		
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
    	this.ID = id;
    	this.ESAFILIADO = esAfiliado;
    	this.ORDENPREVIA = ordenPrevia;
    	this.IDIPS = idIPS;
    	this.CAPACIDAD = capacidad;
    	this.HORAINICIO = horaInicio;
    	this.HORAFIN = horaFin;
    	this.FECHAINICIO = fechaInicio;
    	this.FECHAFIN = fechaFin;
    	this.DIAINICIO = diaInicio;
    	this.DIAFIN = diaFin;
    	this.IDRECEPCIONISTA = idRecepcionista;
		
	}
	
	
	

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public String getOrdenPrevia() {
		// TODO Auto-generated method stub
		return ORDENPREVIA;
	}
	

	@Override
	public String getEsAfiliado() {
		// TODO Auto-generated method stub
		return ESAFILIADO;
	}


	@Override
	public long getIdIPS() {
		// TODO Auto-generated method stub
		return IDIPS;
	}


	@Override
	public int getCapacidad() {
		// TODO Auto-generated method stub
		return CAPACIDAD;
	}

	
	@Override
	public String getHoraInicio() {
		// TODO Auto-generated method stub
		return HORAINICIO;
	}


	@Override
	public String getHoraFin() {
		// TODO Auto-generated method stub
		return HORAFIN;
	}


	@Override
	public String getFechaInicio() {
		// TODO Auto-generated method stub
		return FECHAINICIO;
	}


	@Override
	public String getFechaFin() {
		// TODO Auto-generated method stub
		return FECHAFIN;
	}


	@Override
	public String getDiaInicio() {
		// TODO Auto-generated method stub
		return DIAINICIO;
	}


	@Override
	public String getDiaFin() {
		// TODO Auto-generated method stub
		return DIAFIN;
	}


	@Override
	public long getIdRecepcionistaIPS() {
		// TODO Auto-generated method stub
		return IDRECEPCIONISTA;
	}


	@Override
	public String toString() {
		return "Consulta [id=" + ID + ", esAfiliado=" + ESAFILIADO + ", ordenPrevia=" + ORDENPREVIA + ", idIPS=" + IDIPS
				+ ", capacidad=" + CAPACIDAD + ", horaInicio=" + HORAINICIO + ", horaFin=" + HORAFIN + ", fechaInicio="
				+ FECHAINICIO + ", fechaFin=" + FECHAFIN + ", diaInicio=" + DIAINICIO + ", diaFin=" + DIAFIN
				+ ", idRecepcionista=" + IDRECEPCIONISTA + "]";
	}


}
