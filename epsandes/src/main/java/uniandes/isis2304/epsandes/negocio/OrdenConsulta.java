package uniandes.isis2304.epsandes.negocio;

public class OrdenConsulta implements VOOrdenConsulta{
	
	
	private long idOrden;
	
	private long idConsulta;
	
	public OrdenConsulta() {
		
		idOrden = 0;
		idConsulta = 0;
		
	}
	
	public OrdenConsulta (long idOrden, long idConsulta) {
		
		this.idOrden = idOrden;
		this.idConsulta = idConsulta;
		
	}

	@Override
	public long getIdOrden() {
		// TODO Auto-generated method stub
		return idOrden;
	}

	@Override
	public long getIdConsulta() {
		// TODO Auto-generated method stub
		return idConsulta;
	}

	@Override
	public String toString() {
		return "OrdenConsulta [idOrden=" + idOrden + ", idConsulta=" + idConsulta + "]";
	}
	

}
