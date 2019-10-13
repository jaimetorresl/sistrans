package uniandes.isis2304.epsandes.negocio;

public class Cita implements VOCita{
	
	private long id;
	
	private String horaInicio;
	
	private String horaFin;
	
	private long idMedico;
	
	private long idConsulta;
	
	private long idTerapia;
	
	private long idProcedimientoEsp;
	
	private long idHospitalizacion;

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHoraInicio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHoraFin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getIdMedico() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getIdConsulta() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getIdTerapia() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getIdProcedimientoEsp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getIdHospitalizacion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "Cita [id=" + id + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", idMedico=" + idMedico
				+ ", idConsulta=" + idConsulta + ", idTerapia=" + idTerapia + ", idProcedimientoEsp="
				+ idProcedimientoEsp + ", idHospitalizacion=" + idHospitalizacion + "]";
	}

}
