package uniandes.isis2304.epsandes.negocio;

public class Receta implements VOReceta {
	
	private String diagnostico;

	private String medicamentos;
	
	private long id;
	
	private long idConsulta;
	
	private long idTerapia;
	
	private long idProcedimientoEsp;
	
	private long idHospitalizacion;
	
	
	/**
     * Constructor por defecto
     */
	public Receta() {
		
		diagnostico = "";
		medicamentos = "";
		id = 0;
		idConsulta = 0;
		idTerapia = 0;
		idProcedimientoEsp = 0;
		idHospitalizacion = 0;
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El numero de identificacion de la receta
	 * @param diagnostico - Diagnostico que da el medico acerca de la condicion del paciente
	 * @param receta - La serie de medicamentos recomendados para darle al paciente por su situacion
	 * @param tratamiento - Serie de actividades que debe realizar el paciente por cierto tiempo
	 */
    public Receta(String diagnostico, String medicamentos, long id, long idConsulta, long idTerapia, long idProcedimientoEsp, long idHospitalizacion) 
    {
    	this.diagnostico = diagnostico;
    	this.medicamentos = medicamentos;
    	this.id = id;
    	this.idConsulta = idConsulta;
    	this.idTerapia = idTerapia;
    	this.idProcedimientoEsp = idProcedimientoEsp;
    	this.idHospitalizacion = idHospitalizacion;
		
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
	public long getIdConsulta() {
		// TODO Auto-generated method stub
		return idConsulta;
	}


	@Override
	public long getIdTerapia() {
		// TODO Auto-generated method stub
		return idTerapia;
	}


	@Override
	public long getIdProcedimientoEsp() {
		// TODO Auto-generated method stub
		return idProcedimientoEsp;
	}


	@Override
	public long getIdHospitalizacion() {
		// TODO Auto-generated method stub
		return idHospitalizacion;
	}


	@Override
	public String toString() {
		return "Receta [diagnostico=" + diagnostico + ", medicamentos=" + medicamentos + ", id=" + id + ", idConsulta="
				+ idConsulta + ", idTerapia=" + idTerapia + ", idProcedimientoEsp=" + idProcedimientoEsp
				+ ", idHospitalizacion=" + idHospitalizacion + "]";
	}
	


}
