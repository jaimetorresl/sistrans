package uniandes.isis2304.epsandes.negocio;

public class Resultado implements VOResultado {
	
	private String diagnostico;
	
	private String receta;
	
	private String tratamiento;
	
	private long id;
	
	/**
     * Constructor por defecto
     */
	public Resultado() {
		
		diagnostico = "";
		receta = "";
		tratamiento = "";
		id = 0;
		
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El numero de identificacion de la receta
	 * @param diagnostico - Diagnostico que da el medico acerca de la condicion del paciente
	 * @param receta - La serie de medicamentos recomendados para darle al paciente por su situacion
	 * @param tratamiento - Serie de actividades que debe realizar el paciente por cierto tiempo
	 */
    public Resultado(long id, String diagnostico, String receta, String tratamiento) 
    {
    	this.id = id;
		this.diagnostico = diagnostico;
		this.receta = receta;
		this.tratamiento = tratamiento;
		
	}
	
	

	@Override
	public String getDiagnostico() {
		// TODO Auto-generated method stub
		return diagnostico;
	}

	@Override
	public String getReceta() {
		// TODO Auto-generated method stub
		return receta;
	}

	@Override
	public String getTratamiento() {
		// TODO Auto-generated method stub
		return tratamiento;
	}


	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del Registro
	 */
	public String toString() 
	{
		return "Registro [id= " + id + ", tratamiento= " + tratamiento + ", receta= " + receta + ", diagnostico= " + diagnostico + "]";
	}

}
