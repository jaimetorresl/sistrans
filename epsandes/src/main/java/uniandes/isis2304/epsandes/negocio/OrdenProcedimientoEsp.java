package uniandes.isis2304.epsandes.negocio;

public class OrdenProcedimientoEsp implements VOOrdenProcedimientoEsp {
	
	
	private long idOrden;
	
	private long idProcedimientoEsp;
	
	public OrdenProcedimientoEsp() {
		
		idOrden = 0;
		idProcedimientoEsp = 0;
		
	}
	
	public OrdenProcedimientoEsp (long idOrden, long idProcedimientoEsp) {
		
		this.idOrden = idOrden;
		this.idProcedimientoEsp = idProcedimientoEsp;
		
	}

	@Override
	public long getIdOrden() {
		// TODO Auto-generated method stub
		return idOrden;
	}

	@Override
	public long getIdProcedimientoEsp() {
		// TODO Auto-generated method stub
		return idProcedimientoEsp;
	}

	@Override
	public String toString() {
		return "OrdenProcedimientoEsp [idOrden=" + idOrden + ", idProcedimientoEsp=" + idProcedimientoEsp + "]";
	}
	
	
	

}
