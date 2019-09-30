package uniandes.isis2304.epsandes.negocio;

public interface VOAdministradorEPS {
	
	/**
	 * Interfaz para los m�todos get del administrador de la EPS.
	 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
	 * 
	 * @author Nicolas Potes Garcia - Jaime Torres
	 * 
	 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
	 */
	
	
	/**
	 * @return id del administrador de datos de la EPS
	 */
	public long getId();
	
	/**
	 * @return nombre del administrador de datos de la EPS
	 */
	public String getNombre();
	
	/**
	 * @return si es gerente de EPS o administrador de la EPS
	 */
	public boolean getGerenteEPS();
	
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del medico
	 */
	public String toString();
	

}
