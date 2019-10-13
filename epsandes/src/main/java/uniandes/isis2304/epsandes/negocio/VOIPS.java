package uniandes.isis2304.epsandes.negocio;


/**
 * Interfaz para los métodos get de IPS.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Nicolas Potes Garcia - Jaime Torres
 * 
 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
 */


public interface VOIPS {
	
	/**
	 * @return el id de la IPS
	 */
	public long getId();
	
	
	/**
	 * @return la localizacion de la IPS
	 */
	public String getLocalizacion();
	
	
	/**
	 * @return el nombre de la IPS
	 */
	public String getNombre();
	
	
	public String getTipo();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la IPS
	 */
	public String toString();
	

}