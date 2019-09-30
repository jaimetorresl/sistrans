package uniandes.isis2304.epsandes.negocio;

public class Paciente implements VOPaciente
{
	
	
	private String estado;
	
	private String nombre;
	
	private String fechaNacimiento;
	
	private long numDocumento;
	
	private int tipoDocumento;
	
	private boolean afiliado;
	
	/**
     * Constructor por defecto
     */
	public Paciente() {
		
		estado = "";
		nombre = "";
		fechaNacimiento = "";
		numDocumento = 0;
		tipoDocumento = 0;
		
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param numDocumento - El numero de identificacion del documento del paciente
	 * @param tipoDocumento - El tipo de documento del que dispone el paciente
	 * @param estado - El estado en el que esta el paciente (hospitalizado, etc)
	 * @param nombre - El nombre del paciente
	 * @param fechaNacimiento - La fecha de nacimiento del paciente
	 */
    public Paciente(long numDocumento, String estado, String nombre, String fechaNacimiento, int tipoDocumento) 
    {
    	this.numDocumento = numDocumento;
		this.nombre = nombre;
		this.estado = estado;
		this.tipoDocumento = tipoDocumento;
		this.fechaNacimiento = fechaNacimiento;
		
	}


	@Override
	public String getEstado() {
		// TODO Auto-generated method stub
		return estado;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public String getFechaNacimiento() {
		// TODO Auto-generated method stub
		return fechaNacimiento;
	}

	@Override
	public long getNumDocumento() {
		// TODO Auto-generated method stub
		return numDocumento;
	}

	@Override
	public int getTipoDocumento() {
		// TODO Auto-generated method stub
		return tipoDocumento;
	}

	@Override
	public boolean getAfiliado() {
		// TODO Auto-generated method stub
		return afiliado;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del Paciente
	 */
	public String toString() 
	{
		return "Paciente [numDocumento= " + numDocumento + ", tipoDocumento= " + tipoDocumento + ", afiliado= " + afiliado + ", fechaNacimiento= " + fechaNacimiento + "nombre" + nombre + "estado" + estado + "]";
	}
	
	
	

}