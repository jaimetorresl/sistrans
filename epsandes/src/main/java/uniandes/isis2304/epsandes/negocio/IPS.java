package uniandes.isis2304.epsandes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


public class IPS implements VOIPS{

	/**
	 * Clase para modelar el concepto IPS del negocio de la EPSAndes
	 *
	 * @author Nicolas Potes Garcia - Jaime Torres
	 */


	private long id;

	private String localizacion;

	private String nombre;
	
	private String tipo;
	
	private long idEPS;
	/**
	 * Las servicios de salud de la IPS 
	 * Cada objeto del servicio de salud contiene [id, horarioAtencion, capacidad] 
	 */
	private List<Object []> servicios;

	/**
	 * Las medicos de la IPS
	 * Cada objeto medico contiene [id, especialidad, numeRegMedico, tipo] 
	 */
	private List<Object []> medicos;
	
	/**
	 * Los recepcionistas de la IPS
	 * Cada objeto recepcionista contiene [id, nombre] 
	 */
	private List<Object []> recepcionistas;


	/**
	 * Constructor por defecto
	 */
	public IPS() {

		id = 0;
		localizacion = "";
		nombre = "";
		tipo = "";
		servicios = new LinkedList<Object []> ();

		recepcionistas = new LinkedList<Object[]>();

	}


	/**
	 * Constructor con valores
	 * @param id - El id de la IPS
	 * @param localizacion - La localizacion de la IPS
	 * @param nombre - El nombre de la IPS
	 * @param tipo - El tipo de IPS 
	 */
	public IPS(long id, String nombre, String tipo, String localizacion, long idEPS ) 
	{
		this.id = id;
		this.nombre = nombre;
		this.localizacion = localizacion;
		this.idEPS = idEPS;
		this.tipo = tipo;
		
		servicios = new LinkedList<Object []> ();
		medicos = new LinkedList<Object []> ();
		recepcionistas = new LinkedList<Object[]>();

	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getLocalizacion() {
		// TODO Auto-generated method stub
		return localizacion;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}
	
	

	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return tipo;
	}
	

	public List<Object []> getServicios() {

		return servicios;

	}

	public List<Object []> getMedicos() {

		return medicos;

	}
	
	
	public List<Object[]> getRecepcionistas() {
		
		return recepcionistas;
		
	}

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del IPS
	 */
	public String toString() 
	{
		return "IPS [id= " + id + ", nombre= " + nombre + ", localizacion= " + localizacion + "]";
	}

	/**
	 * @return Una cadena de caracteres con la información completa de la IPS.
	 * Ademas de la informacion basica de la IPS
	 */
	public String toStringCompleto () 
	{
		String resp =  this.toString();
		resp += "\n --- Servicios de salud de la IPS\n";
		int i = 1;
		for (Object [] servicio : servicios)
		{	
			String nombreS = (String) servicio[0];
			String horarioAtencion = (String) servicio[1];
			String capacidad = (String) servicio[2];

			resp += i++ + ". " + "[" +nombre.toString() + ", horarioAtencion= " + horarioAtencion.toString() + ", capacidad= " + capacidad.toString() + "]\n";
		}

		return resp;
	}

}