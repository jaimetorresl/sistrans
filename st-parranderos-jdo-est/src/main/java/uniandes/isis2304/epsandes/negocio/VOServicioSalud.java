package uniandes.isis2304.epsandes.negocio;

public interface VOServicioSalud {
	/**
	 * Interfaz para los m�todos get de ServicioSalud.
	 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
	 * 
	 * @author Nicolas Potes Garcia - Jaime Torres
	 * 
	 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
	 */

	/**
	 * @return la capacidad del servicio de salud
	 */
	public int getCapacidad();

	/**
	 * @return los horarios de atencion del servicio
	 */
	public String getHorarioAtencion();

	/**
	 * @return el id del servicio
	 */
	public long getId();
	
	/**
	 * @return el nombre del servicio
	 */
	public String getNombre();
	
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del servicio de salud
	 */
	public String toString();

}
