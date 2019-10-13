package uniandes.isis2304.epsandes.negocio;

public interface VOHospitalizacion {
	
	public long getId();
	
	public String getOrdenPrevia();
	
	public String getEsAfiliado();
	
	public int getNumVisitas();
	
	public long getIdIPS();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos
	 */
	public String toString();

}
