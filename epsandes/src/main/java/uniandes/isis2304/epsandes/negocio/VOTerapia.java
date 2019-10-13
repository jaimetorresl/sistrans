package uniandes.isis2304.epsandes.negocio;

public interface VOTerapia {

	public long getId();

	public String getOrdenPrevia();

	public String getEsAfiliado();

	public int getNumSesiones();

	public String getTipoTerapia();

	public long getIdIPS();

	public int getCapacidad();

	public String getHorarioSemanal();

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos
	 */
	public String toString();


}
