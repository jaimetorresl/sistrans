package uniandes.isis2304.epsandes.negocio;

public class OrdenHospitalizacion implements VOOrdenHospitalizacion{
	
	private long idOrden;
	
	private long idHospitalizacion;
	
	public OrdenHospitalizacion() {
		
		idOrden = 0;
		idHospitalizacion = 0;
		
	}
	
	public OrdenHospitalizacion (long idOrden, long idHospitalizacion) {
		
		this.idOrden = idOrden;
		this.idHospitalizacion = idHospitalizacion;
		
	}

	@Override
	public long getIdOrden() {
		// TODO Auto-generated method stub
		return idOrden;
	}

	@Override
	public long getIdHospitalizacion() {
		// TODO Auto-generated method stub
		return idHospitalizacion;
	}

	@Override
	public String toString() {
		return "OrdenHospitalizacion [idOrden=" + idOrden + ", idHospitalizacion=" + idHospitalizacion + "]";
	}
	
	

}
