package uniandes.isis2304.epsandes.negocio;


/**
 * Interfaz para los m�todos get de EPS.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Nicolas Potes Garcia - Jaime Torres
 * 
 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
 */
public interface VOEPS {
	
	
	/**
	 * @return nombre de la EPS (debe ser unico)
	 */
	public String getNombre();
	
	/**
	 * @return id de la EPS
	 */
	public long getId();

	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la EPS
	 */
	public String toString();
	

}
