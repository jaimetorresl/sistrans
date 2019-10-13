package uniandes.isis2304.epsandes.negocio;

/**
 * Interfaz para los métodos get de Medico.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Nicolas Potes Garcia - Jaime Torres
 * 
 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
 */

public interface VOMedico {
	
	/**
	 * @return la especialidad del medico
	 */
	public String especialidad();
	
	/**
	 * @return el id del medico
	 */
	public long id();
	
	/**
	 * @return el numero de registro del medico
	 */
	public int numRegMedico();
	

	/**
	 * @return el nombre de medico que es
	 */
	public String nombre();
	
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del medico
	 */
	public String toString();
	

}