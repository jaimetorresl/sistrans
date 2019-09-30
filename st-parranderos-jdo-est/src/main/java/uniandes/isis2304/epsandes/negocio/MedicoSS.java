package uniandes.isis2304.epsandes.negocio;


public class MedicoSS implements VOMedicoSS{
	
	
	private long idMedico;
	
	private long idServicioSalud;
	
	
	/**
     * Constructor por defecto
     */
	
	public MedicoSS() {
		
		idMedico = 0;
		idServicioSalud = 0;
	}
	
	/**
	 * Constructor con valores
	 * @param id - El id de la consulta
	 * @param tipoConsulta - El tipo de la consulta 
	 * @param ordenPrevia - Si es necesario tener una orden previa para poder hacer uso de la consulta
	 */
	public MedicoSS(long idMedico, long idServicioSalud) {
		super();
		this.idMedico = idMedico;
		this.idServicioSalud = idServicioSalud;
	}
	

	
	
	public long idMedico() {
		return idMedico;
	}

	public void setIdMedico(long idMedico) {
		this.idMedico = idMedico;
	}

	public long idServicio() {
		return idServicioSalud;
	}

	public void setIdServicioSalud(long idServicioSalud) {
		this.idServicioSalud = idServicioSalud;
	}

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la consulta
	 */
	public String toString() 
	{
		return "Consulta [idMedico= " + idMedico + ", idServicioSalud= " + idServicioSalud +"]";
	}


	
	

}
