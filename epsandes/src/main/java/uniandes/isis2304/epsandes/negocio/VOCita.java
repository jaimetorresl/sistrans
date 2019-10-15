package uniandes.isis2304.epsandes.negocio;

public interface VOCita {
	
	
	public long getId();
	
	public String getHoraInicio();
	
	public String getHoraFin();
	
	public long getIdMedico();
	
	public long getIdConsulta();
	
	public long getIdTerapia();
	
	public long getIdProcedimientoEsp();
	
	public long getIdHospitalizacion();
	
	public long getIdUsuarioIPS();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos
	 */
	public String toString();

}
