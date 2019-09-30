package uniandes.isis2304.epsandes.negocio;

public class ServicioSalud implements VOServicioSalud{

	private int capacidad;

	private String horarioAtencion;

	private long id;
	
	private String nombre;




	/**
	 * Constructor por defecto
	 */
	public ServicioSalud() {

		horarioAtencion = "";
		capacidad = 0;
		id = 0;
		nombre = "";
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El id del medico
	 * @param especialidad - La especialidad del medico
	 * @param numRegMedico - El numero de registro del medico
	 * @param tipo - El tipo de medico que es 
	 * @param nombre - El nombre del medico
	 */
    public ServicioSalud(long id, String horarioAtencion, int capacidad, String nombre) 
    {
    	this.id = id;
		this.nombre = nombre;
		this.horarioAtencion = horarioAtencion;
		this.capacidad = capacidad;
		
	}

	@Override
	public int getCapacidad() {
		// TODO Auto-generated method stub
		return capacidad;
	}

	@Override
	public String getHorarioAtencion() {
		// TODO Auto-generated method stub
		return horarioAtencion;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}


	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}
	
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del Servicio de salud
	 */
	public String toString() 
	{
		return "ServicioSalud [id= " + id + ", nombre= " + nombre + ", horarioAtencion= " + horarioAtencion + ", capacidad= " + capacidad + "]";
	}

}
