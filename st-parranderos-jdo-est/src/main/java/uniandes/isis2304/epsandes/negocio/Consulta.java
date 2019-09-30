package uniandes.isis2304.epsandes.negocio;


public class Consulta implements VOConsulta{
	
	
	private long id;
	
	private boolean ordenPrevia;
	
	private int tipoConsulta;
	
	/**
     * Constructor por defecto
     */
	public Consulta() {
		
		ordenPrevia = false;
		id = 0;
		tipoConsulta = 0;
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El id de la consulta
	 * @param tipoConsulta - El tipo de la consulta 
	 * @param ordenPrevia - Si es necesario tener una orden previa para poder hacer uso de la consulta
	 */
    public Consulta(long id, int tipoConsulta, boolean ordenPrevia) 
    {
    	this.id = id;
		this.tipoConsulta = tipoConsulta;
		this.ordenPrevia = ordenPrevia;
		
	}
	
	
	

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean getOrdenPrevia() {
		// TODO Auto-generated method stub
		return ordenPrevia;
	}

	@Override
	public int getTipoConsulta() {
		// TODO Auto-generated method stub
		return tipoConsulta;
	}
	
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la consulta
	 */
	public String toString() 
	{
		return "Consulta [id= " + id + ", ordenPrevia= " + ordenPrevia + ", tipoConsulta= " + tipoConsulta + "]";
	}
	
	

}
