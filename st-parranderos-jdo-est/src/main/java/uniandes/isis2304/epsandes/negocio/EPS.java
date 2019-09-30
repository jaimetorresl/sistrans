package uniandes.isis2304.epsandes.negocio;

import java.util.LinkedList;
import java.util.List;

public class EPS implements VOEPS{
	
	private String nombre;
	
	
	private long id;
	
	/**
	 * Las IPS asociadas a cierta EPS 
	 * Cada objeto de IPS contiene [id, localizacion, nombre, tipo] 
	 */
	private List<Object []> listaIPS;
	
	/**
	 * Los usuarios asociadas a cierta EPS 
	 * Cada objeto de usuario contiene [estado, nombre, fechaNacimiento, numDocumento, tipoDocumento] 
	 */
	private List<Object []> usuarios;
	
	/**
	 * Las empleados de la EPS 
	 * Cada objeto de empleado contiene [id, nombre, rol] 
	 */
	private List<Object []> empleados;
	
	/**
	 * Constructor por defecto
	 */
	public EPS() {
		
		nombre = "";
		id = 0;
		
	}
	
	/**
	 * Constructor con valores
	 * @param id - El id de la EPS
	 * @param nombre - El nombre de la EPS
	 */
    public EPS(long id, String nombre) 
    {
    	this.id = id;
		this.nombre = nombre;
		listaIPS = new LinkedList<Object []> ();
	}
	

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	/**
	 * @return La lista de IPS de la EPS
	 */
	public List<Object []> getListaIPS() 
	{
		return listaIPS;
	}
	
	/**
	 *@param listaIPS - La nueva lista de IPS asociada a la EPS
	 */
	public void setListaIPS(List<Object[]> listaIPS) {
		
		this.listaIPS = listaIPS;
		
	}
	
	/**
	 * @return La lista de usuarios de la EPS
	 */
	public List<Object []> getUsuario() 
	{
		return usuarios;
	}
	
	/**
	 *@param usuarios - La nueva lista de usarios asociados a la EPS
	 */
	public void setUsuarios(List<Object[]> usuarios) {
		
		this.usuarios = usuarios;
		
	}
	
	/**
	 * @return La lista de empleados de la EPS
	 */
	public List<Object []> getEmpleados() 
	{
		return empleados;
	}
	
	/**
	 *@param empleados - La nueva lista de empleados asociados a la EPS
	 */
	public void setEmpleados(List<Object[]> empleados) {
		
		this.empleados = empleados;
		
	}
	
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la EPS
	 */
	public String toString() 
	{
		return "EPS [id= " + id + ", nombre= " + nombre + "]";
	}

}
