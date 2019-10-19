package uniandes.isis2304.epsandes.negocio;
/**
 * Interfaz para los métodos get del Paciente.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Nicolas Potes Garcia - Jaime Torres
 * 
 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
 */
public interface VOUsuarioIPS {
	
	
	/**
	 * @return estado del paciente o afiliado (hospitalizado, consulta, etc)
	 */
	public String getEstado();
	
	/**
	 * @return nombre del paciente o afiliado
	 */
	public String getNombre();
	
	/**
	 * @return fecha de nacimiento del paciente o afiliado
	 */
	public String getFechaNacimiento();
	
	/**
	 * @return numero de identificacion del paciente o afiliado
	 */
	public long getNumDocumento();
	
	/**
	 * @return tipo de documento del paciente o afiliado. Se utilizan numeros para 
	 * describir el tipo de documento: (1) CC, (2) Pasaporte, etc.
	 */
	public int getTipoDocumento();
	
	
	/**
	 * @return true si el paciente es afiliado a la EPS, false de lo contrario
	 */
	public String getEsAfiliado();
	
	
	/**
	 * @return id eps a la cual se encuentra asociado
	 */
	public Long getIdEPS();
	
	
	public int getEdad();
	
	public String getGenero();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del paciente
	 */
	public String toString();

}