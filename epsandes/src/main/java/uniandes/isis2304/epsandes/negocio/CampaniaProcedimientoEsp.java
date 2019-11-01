package uniandes.isis2304.epsandes.negocio;

public class CampaniaProcedimientoEsp implements VOCampaniaProcedimientoEsp{
	

	private long idCampania;
	
	private long idProcedimiento;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String disponible;
	
	
	public CampaniaProcedimientoEsp() {
		
		idCampania = 0;
		idProcedimiento = 0;
		fechaInicio = "";
		fechaFin = "";
		disponible = "";
		
	}
	
	public CampaniaProcedimientoEsp(long idCampania, long idProcedimiento, String fechaInicio, String fechaFin, String disponible) {
		
		this.idCampania = idCampania;
		this.idProcedimiento = idProcedimiento; 
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.disponible = disponible;
		
	}
	

	@Override
	public long getIdCampania() {
		// TODO Auto-generated method stub
		return idCampania;
	}

	@Override
	public long getIdProcedimiento() {
		// TODO Auto-generated method stub
		return idProcedimiento;
	}

	@Override
	public String getFechaInicio() {
		// TODO Auto-generated method stub
		return fechaInicio;
	}

	@Override
	public String getFechaFin() {
		// TODO Auto-generated method stub
		return fechaFin;
	}

	@Override
	public String getDisponible() {
		// TODO Auto-generated method stub
		return disponible;
	}

	@Override
	public String toString() {
		return "CampaniaConsulta [idCampania=" + idCampania + ", idProcedimiento=" + idProcedimiento + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", disponible=" + disponible + "]";
	}
	


}
