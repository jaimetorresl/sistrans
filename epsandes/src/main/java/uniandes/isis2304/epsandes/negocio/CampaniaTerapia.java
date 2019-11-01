package uniandes.isis2304.epsandes.negocio;

public class CampaniaTerapia implements VOCampaniaTerapia{

	private long idCampania;
	
	private long idTerapia;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String disponible;
	
	
	public CampaniaTerapia() {
		
		idCampania = 0;
		idTerapia = 0;
		fechaInicio = "";
		fechaFin = "";
		disponible = "";
		
	}
	
	public CampaniaTerapia(long idCampania, long idTerapia, String fechaInicio, String fechaFin, String disponible) {
		
		this.idCampania = idCampania;
		this.idTerapia = idTerapia; 
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
	public long getIdTerapia() {
		// TODO Auto-generated method stub
		return idTerapia;
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
		return "CampaniaConsulta [idCampania=" + idCampania + ", idTerapia=" + idTerapia + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", disponible=" + disponible + "]";
	}
	



}
