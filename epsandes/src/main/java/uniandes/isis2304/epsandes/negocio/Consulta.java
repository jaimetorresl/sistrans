package uniandes.isis2304.epsandes.negocio;


public class Consulta implements VOConsulta{
	
	
	private long id;
	
	private String esAfiliado;
	
	private String ordenPrevia;
	
	private long idIPS;
	
	/**
     * Constructor por defecto
     */
	public Consulta() {
		
		ordenPrevia = "N";
		id = 0;
		esAfiliado = "N";
		idIPS = 0;
		
	}
	
	
	/**
	 * Constructor con valores
	 * @param id - El id de la consulta
	 * @param tipoConsulta - El tipo de la consulta 
	 * @param ordenPrevia - Si es necesario tener una orden previa para poder hacer uso de la consulta
	 */
    public Consulta(long id, String esAfiliado, String ordenPrevia, long idIPS) 
    {
    	this.id = id;
    	this.esAfiliado = esAfiliado;
    	this.ordenPrevia = ordenPrevia;
    	this.idIPS = idIPS;
		
	}
	
	
	

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getOrdenPrevia() {
		// TODO Auto-generated method stub
		return ordenPrevia;
	}
	

	@Override
	public String getEsAfiliado() {
		// TODO Auto-generated method stub
		return esAfiliado;
	}


	@Override
	public long getIdIPS() {
		// TODO Auto-generated method stub
		return idIPS;
	}


	@Override
	public String toString() {
		return "Consulta [id=" + id + ", esAfiliado=" + esAfiliado + ", ordenPrevia=" + ordenPrevia + ", idIPS=" + idIPS
				+ "]";
	}
	

}
