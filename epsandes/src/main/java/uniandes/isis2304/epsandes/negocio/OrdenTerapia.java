package uniandes.isis2304.epsandes.negocio;

public class OrdenTerapia implements VOOrdenTerapia{
	

	private long idOrden;
	
	private long idTerapia;
	
	public OrdenTerapia() {
		
		idOrden = 0;
		idTerapia = 0;
		
	}
	
	public OrdenTerapia(long idOrden, long idTerapia) {
		
		this.idOrden = idOrden;
		this.idTerapia = idTerapia;
		
	}

	@Override
	public long getIdOrden() {
		// TODO Auto-generated method stub
		return idOrden;
	}

	@Override
	public long getIdTerapia() {
		// TODO Auto-generated method stub
		return idTerapia;
	}

	@Override
	public String toString() {
		return "OrdenTerapia [idOrden=" + idOrden + ", idTerapia=" + idTerapia + "]";
	}

}
