package uniandes.isis2304.epsandes.negocio;

public class AdministradorEPS implements VOAdministradorEPS{
	
	
	
	private long id;
	
	private String nombre;
	
	private boolean gerenteEPS;
	
	
	/**
     * Constructor por defecto
     */
	public AdministradorEPS() {
		
		nombre = "";
		id = 0;
		gerenteEPS = false;
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El id del medico
	 * @param nombre - El nombre del medico
	 */
    public AdministradorEPS(long id, String nombre, boolean gerenteEPS) 
    {
    	this.id = id;
		this.nombre = nombre;
		this.gerenteEPS = gerenteEPS;
		
	}
	

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}
	
	
	@Override
	public boolean getGerenteEPS() {
		// TODO Auto-generated method stub
		return gerenteEPS;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del administrador de la EPS
	 */
	public String toString() 
	{
		return "AdministradorEPS [id= " + id + ", nombre= " + nombre + "gerenteEPS= " + gerenteEPS + "]";
	}
	

}
