package uniandes.isis2304.epsandes.negocio;

public interface VOProcedimientoEsp {


	public long getId();

	public String getEsAfiliado();

	public String getOrdenPrevia();

	public String getConocimiento();

	public String getEquipo();

	public long getIdIPS();

	public int getCapacidad();

	public String getHorarioSemanal();


	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos
	 */
	public String toString();

}
