package uniandes.isis2304.epsandes.negocio;

public interface VOTipoSS {

	/**
	 * @return la especialidad del medico
	 */
	public String codigo();
	
	/**
	 * @return el id del medico
	 */
	public String nombre();
	
	
	public boolean requiereReserva();
}
