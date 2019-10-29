package uniandes.isis2304.epsandes.negocio;

public class Receta implements VOReceta {
	
	private String diagnostico;

	private String medicamentos;
	
	private long id;
	
	private long idCita;
	
	
	/**
     * Constructor por defecto
     */
	public Receta() {
		
		diagnostico = "";
		medicamentos = "";
		id = 0;
		idCita = 0;
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El numero de identificacion de la receta
	 * @param diagnostico - Diagnostico que da el medico acerca de la condicion del paciente
	 * @param receta - La serie de medicamentos recomendados para darle al paciente por su situacion
	 * @param tratamiento - Serie de actividades que debe realizar el paciente por cierto tiempo
	 */
    public Receta(String diagnostico, String medicamentos, long id, long idCita) 
    {
    	this.diagnostico = diagnostico;
    	this.medicamentos = medicamentos;
    	this.id = id;
    	this.idCita = idCita;
		
	}
	
	

	@Override
	public String getDiagnostico() {
		// TODO Auto-generated method stub
		return diagnostico;
	}


	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	

	@Override
	public String getMedicamentos() {
		// TODO Auto-generated method stub
		return medicamentos;
	}


	@Override
	public long getIdCita() {
		// TODO Auto-generated method stub
		return idCita;
	}


	@Override
	public String toString() {
		return "Receta [diagnostico=" + diagnostico + ", medicamentos=" + medicamentos + ", id=" + id + ", idCita="
				+ idCita + "]";
	}
	
	
}
