package uniandes.isis2304.epsandes.negocio;

public class Terapia implements VOTerapia{
	
	private long id;
	
	private String ordenPrevia;
	
	private String esAfiliado;
	
	private int numSesiones;
	
	private String tipoTerapia;
	
	private long idIPS;
	
	private int capacidad;
	
	private String horaInicio;
	
	private String horaFin;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String diaInicio;
	
	private String diaFin;
	
	private long idRecepcionista;
	
	public Terapia() {
		
		id = 0;
		ordenPrevia = "";
		esAfiliado = "";
		numSesiones = 0;
		tipoTerapia = "";
		idIPS = 0;
		capacidad = 0;
		horaInicio = "";
		horaFin = "";
		fechaInicio = "";
		fechaFin = "";
		diaInicio = "";
		diaFin = "";
		idRecepcionista = 0;
		
	}
	
	public Terapia(long id, String ordenPrevia, String esAfiliado, int numSesiones, String tipoTerapia, 
			long idIPS, int capacidad, String horarioSemanal, String horaInicio, String horaFin, 
			String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista) {
		
		this.id = id;
		this.ordenPrevia = ordenPrevia;
		this.esAfiliado = esAfiliado;
		this.numSesiones = numSesiones;
		this.tipoTerapia = tipoTerapia;
		this.idIPS = idIPS;
		this.capacidad = capacidad;
		this.horaInicio = horaInicio;
    	this.horaFin = horaFin;
    	this.fechaInicio = fechaInicio;
    	this.fechaFin = fechaFin;
    	this.diaInicio = diaInicio;
    	this.diaFin = diaFin;
    	this.idRecepcionista = idRecepcionista;
		
	}
	

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getOrdenPrevia() {
		// TODO Auto-generated method stub
		return ordenPrevia;
	}

	@Override
	public String getEsAfiliado() {
		// TODO Auto-generated method stub
		return esAfiliado;
	}

	@Override
	public int getNumSesiones() {
		// TODO Auto-generated method stub
		return numSesiones;
	}

	@Override
	public String getTipoTerapia() {
		// TODO Auto-generated method stub
		return tipoTerapia;
	}

	@Override
	public long getIdIPS() {
		// TODO Auto-generated method stub
		return idIPS;
	}

	@Override
	public int getCapacidad() {
		// TODO Auto-generated method stub
		return capacidad;
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
	public String getDiaInicio() {
		// TODO Auto-generated method stub
		return diaInicio;
	}


	@Override
	public String getDiaFin() {
		// TODO Auto-generated method stub
		return diaFin;
	}


	@Override
	public long getIdRecepcionistaIPS() {
		// TODO Auto-generated method stub
		return idRecepcionista;
	}

	@Override
	public String toString() {
		return "Terapia [id=" + id + ", ordenPrevia=" + ordenPrevia + ", esAfiliado=" + esAfiliado + ", numSesiones="
				+ numSesiones + ", tipoTerapia=" + tipoTerapia + ", idIPS=" + idIPS + ", capacidad=" + capacidad
				+ ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", fechaInicio=" + fechaInicio + ", fechaFin="
				+ fechaFin + ", diaInicio=" + diaInicio + ", diaFin=" + diaFin + ", idRecepcionista=" + idRecepcionista
				+ "]";
	}

	
}
