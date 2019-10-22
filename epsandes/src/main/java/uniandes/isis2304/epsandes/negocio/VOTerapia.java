package uniandes.isis2304.epsandes.negocio;

public interface VOTerapia {

	public long getId();

	public String getOrdenPrevia();

	public String getEsAfiliado();

	public String getNumSesiones();

	public String getTipoTerapia();

	public long getIdIPS();

	public int getCapacidad();

	public String getHoraInicio();
	
	public String getHoraFin();
	
	public String getFechaInicio();
	
	public String getFechaFin();
	
	public String getDiaInicio();
	
	public String getDiaFin();
	
	public long getIdRecepcionistaIPS();

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos
	 */
	public String toString();


}
