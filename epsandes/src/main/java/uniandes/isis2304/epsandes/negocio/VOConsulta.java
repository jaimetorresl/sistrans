package uniandes.isis2304.epsandes.negocio;


/**
 * Interfaz para los m�todos get de Consulta.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Nicolas Potes Garcia - Jaime Torres
 * 
 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
 */

public interface VOConsulta {
	
	
	/**
	 * @return el id de la consulta
	 */
	public long getId();
	
	/**
	 * @return la orden de la consulta
	 */
	public String getOrdenPrevia();
	
	/**
	 * @return si tiene que ser afiliado
	 */
	public String getEsAfiliado();
	
	
	public long getIdIPS();
	
	public int getCapacidad();
	
	public String getHoraInicio();
	
	public String getHoraFin();
	
	public String getFechaInicio();
	
	public String getFechaFin();
	
	public String getDiaInicio();
	
	public String getDiaFin();
	
	public long getIdRecepcionistaIPS();
	
	public String getReservado();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la consulta
	 */
	public String toString();
	
}
