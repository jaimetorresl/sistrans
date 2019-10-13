package uniandes.isis2304.epsandes.negocio;

public class Terapia implements VOTerapia{
	
	private long id;
	
	private String ordenPrevia;
	
	private String esAfiliado;
	
	private int numSesiones;
	
	private String tipoTerapia;
	
	private long idIPS;
	
	private int capacidad;
	
	private String horarioSemanal;
	
	
	public Terapia() {
		
		id = 0;
		ordenPrevia = "";
		esAfiliado = "";
		numSesiones = 0;
		tipoTerapia = "";
		idIPS = 0;
		capacidad = 0;
		horarioSemanal = "";
		
	}
	
	public Terapia(long id, String ordenPrevia, String esAfiliado, int numSesiones, String tipoTerapia, long idIPS, int capacidad, String horarioSemanal) {
		
		this.id = id;
		this.ordenPrevia = ordenPrevia;
		this.esAfiliado = esAfiliado;
		this.numSesiones = numSesiones;
		this.tipoTerapia = tipoTerapia;
		this.idIPS = idIPS;
		this.capacidad = capacidad;
		this.horarioSemanal = horarioSemanal;
		
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
	public String getHorarioSemanal() {
		// TODO Auto-generated method stub
		return horarioSemanal;
	}

	@Override
	public String toString() {
		return "Terapia [id=" + id + ", ordenPrevia=" + ordenPrevia + ", esAfiliado=" + esAfiliado + ", numSesiones="
				+ numSesiones + ", tipoTerapia=" + tipoTerapia + ", idIPS=" + idIPS + ", capacidad=" + capacidad
				+ ", horarioSemanal=" + horarioSemanal + "]";
	}

	
}
