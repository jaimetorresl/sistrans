package uniandes.isis2304.epsandes.negocio;

public class CampaniaPrevencion implements VOCampaniaPrevencion{
	
	private long id;
	
	private String localizacion;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private long idEPS;
	
	public CampaniaPrevencion() {
		
		id = 0;
		localizacion = "";
		fechaInicio = "";
		fechaFin = "";
		idEPS = 0;
		
	}
	
	public CampaniaPrevencion(long id, String localizacion, String fechaInicio, String fechaFin, long idEPS)
	{
		
		this.id = id;
		this.localizacion = localizacion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.idEPS = idEPS;
	
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getLocalizacion() {
		// TODO Auto-generated method stub
		return localizacion;
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
	public long getIdEPS() {
		// TODO Auto-generated method stub
		return idEPS;
	}

	@Override
	public String toString() {
		return "CampaniaPrevencion [id=" + id + ", localizacion=" + localizacion + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", idEPS=" + idEPS + "]";
	}
	
}
