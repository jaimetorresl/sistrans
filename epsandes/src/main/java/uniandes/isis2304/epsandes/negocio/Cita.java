package uniandes.isis2304.epsandes.negocio;

import javafx.geometry.HorizontalDirection;

public class Cita implements VOCita{
	
	private long id;
	
	private String horaInicio;
	
	private String horaFin;
	
	private long idMedico;
	
	private long idConsulta;
	
	private long idTerapia;
	
	private long idProcedimientoEsp;
	
	private long idHospitalizacion;
	
	private long idUsuarioIPS;
	
	
	
	public Cita() {
		
		id = 0;
		horaInicio = "";
		horaFin = "";
		idMedico = 0;
		idConsulta = 0;
		idTerapia = 0;
		idProcedimientoEsp = 0;
		idHospitalizacion = 0;
		idUsuarioIPS = 0;
		
	}
	
	public Cita(long id, String horaInicio, String horaFin, long idMedico, long idConsulta, long idTerapia, long idProcedimientoEsp, long idHospitalizacion, long idUsuarioIPS) {
		
		
		this.id = id;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.idMedico = idMedico;
		this.idConsulta = idConsulta;
		this.idTerapia = idTerapia;
		this.idProcedimientoEsp = idProcedimientoEsp;
		this.idHospitalizacion = idHospitalizacion;
		this.idUsuarioIPS = idUsuarioIPS;
		
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getHoraInicio() {
		// TODO Auto-generated method stub
		return horaInicio;
	}

	@Override
	public String getHoraFin() {
		// TODO Auto-generated method stub
		return horaFin;
	}

	@Override
	public long getIdMedico() {
		// TODO Auto-generated method stub
		return idMedico;
	}

	@Override
	public long getIdConsulta() {
		// TODO Auto-generated method stub
		return idConsulta;
	}

	@Override
	public long getIdTerapia() {
		// TODO Auto-generated method stub
		return idTerapia;
	}

	@Override
	public long getIdProcedimientoEsp() {
		// TODO Auto-generated method stub
		return idProcedimientoEsp;
	}

	@Override
	public long getIdHospitalizacion() {
		// TODO Auto-generated method stub
		return idHospitalizacion;
	}

	@Override
	public long getIdUsuarioIPS() {
		// TODO Auto-generated method stub
		return idUsuarioIPS;
	}

	@Override
	public String toString() {
		return "Cita [id=" + id + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", idMedico=" + idMedico
				+ ", idConsulta=" + idConsulta + ", idTerapia=" + idTerapia + ", idProcedimientoEsp="
				+ idProcedimientoEsp + ", idHospitalizacion=" + idHospitalizacion + ", idUsuarioIPS=" + idUsuarioIPS
				+ "]";
	}

}
