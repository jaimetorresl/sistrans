package uniandes.isis2304.epsandes.negocio;

import java.util.LinkedList;
import java.util.List;

public class Medico implements VOMedico {
	
	private String especialidad;
	
	private long id;
	
	private long numRegMedico;

	private String nombre;
	
	/**
	 * Las IPS a las cual esta asociado
	 * Cada objeto IPS contiene [id, localizacion, nombre, tipo] 
	 */
	private List<Object []> listaIPS;
	
	/**
     * Constructor por defecto
     */
	public Medico() {
		
		especialidad = "";
		id = 0;
		numRegMedico = 0;
		nombre = "";
		listaIPS = new LinkedList<Object[]>();
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El id del medico
	 * @param especialidad - La especialidad del medico
	 * @param numRegMedico - El numero de registro del medico
	 * @param tipo - El tipo de medico que es 
	 * @param nombre - El nombre del medico
	 */
    public Medico(long id, String especialidad, String nombre, long numRegMedico) 
    {
    	this.id = id;
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.numRegMedico = numRegMedico;
		listaIPS = new LinkedList<Object[]>();
		
	}

	@Override
	public String especialidad() {
		// TODO Auto-generated method stub
		return especialidad;
	}

	@Override
	public long id() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public long numRegMedico() {
		// TODO Auto-generated method stub
		return numRegMedico;
	}


	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return nombre;
	}
	
	
	public List<Object []> getIPS() {

		return listaIPS;

	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del Medico
	 */
	public String toString() 
	{
		return "Medico [id= " + id + ", nombre= " + nombre + ", numRegMedico= " + numRegMedico +  "]";
	}

}