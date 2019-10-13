package uniandes.isis2304.epsandes.negocio;

public interface VOUsuarioEPS {
	
	public long getId();
	
	public String getNombre();
	
	public int getRol();
	
	public long getIdEPS();
	
	public String getCorreo();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos
	 */
	public String toString();
	

}
