package uniandes.isis2304.epsandes.negocio;

public class CampaniaHospitalizacion implements VOCampaniaHospitalizacion{


	private long idCampania;

	private long idHospitalizacion;

	private String fechaInicio;

	private String fechaFin;

	private String disponible;


	public CampaniaHospitalizacion() {

		idCampania = 0;
		idHospitalizacion = 0;
		fechaInicio = "";
		fechaFin = "";
		disponible = "";

	}

	public CampaniaHospitalizacion(long idCampania, long idHospitalizacion, String fechaInicio, String fechaFin, String disponible) {

		this.idCampania = idCampania;
		this.idHospitalizacion = idHospitalizacion; 
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
	public long getIdHospitalizacion() {
		// TODO Auto-generated method stub
		return idHospitalizacion;
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
		return "CampaniaConsulta [idCampania=" + idCampania + ", idHospitalizacion=" + idHospitalizacion + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", disponible=" + disponible + "]";
	}

}
