package uniandes.isis2304.epsandes.negocio;
/**
 * Interfaz para los m�todos get del Resultado.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Nicolas Potes Garcia - Jaime Torres
 * 
 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
 */
public interface VOReceta {

	/**
	 * @return diagnostico dado por el medico y entregado en el resultado
	 */
	public String getDiagnostico();

	/**
	 * @return receta de medicamentos para mejorar el estado de salud del paciente
	 */
	public String getMedicamentos();

	/**
	 * @return tratamiento que debe seguir por cierto tiempo deterinado y especificado
	 */

	public long getIdCita();

	/**
	 * @return id de la receta que fue entregada
	 */
	public long getId();


	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del resultado
	 */
	public String toString();

}
