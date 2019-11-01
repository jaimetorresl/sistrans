package uniandes.isis2304.epsandes.negocio;

public class CampaniaConsulta implements VOCampaniaConsulta {
	
	
	private long idCampania;
	
	private long idConsulta;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String disponible;
	
	
	public CampaniaConsulta() {
		
		idCampania = 0;
		idConsulta = 0;
		fechaInicio = "";
		fechaFin = "";
		disponible = "";
		
	}
	
	public CampaniaConsulta(long idCampania, long idConsulta, String fechaInicio, String fechaFin, String disponible) {
		
		this.idCampania = idCampania;
		this.idConsulta = idConsulta; 
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
	public long getIdConsulta() {
		// TODO Auto-generated method stub
		return idConsulta;
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
		return "CampaniaConsulta [idCampania=" + idCampania + ", idConsulta=" + idConsulta + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", disponible=" + disponible + "]";
	}
	
}
