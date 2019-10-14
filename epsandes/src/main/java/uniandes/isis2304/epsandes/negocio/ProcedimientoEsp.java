package uniandes.isis2304.epsandes.negocio;

public class ProcedimientoEsp implements VOProcedimientoEsp{
	
	private long id;
	
	private String esAfiliado;
	
	private String ordenPrevia;
	
	private String conocimiento;
	
	private String equipo;
	
	private long idIPS;
	
	private int capacidad;
	
	private String horarioSemanal;
	
	
	public ProcedimientoEsp() {
		
		id = 0;
		esAfiliado = "";
		ordenPrevia = "";
		conocimiento = "";
		equipo = "";
		idIPS = 0;
		capacidad = 0;
		horarioSemanal = "";
		
	}
	
	
	public ProcedimientoEsp(long id, String esAfiliado, String ordenPrevia, String conocimiento, String equipo, long idIPS, int capacidad, String horarioSemanal) {
		
		this.id = id;
		this.esAfiliado = esAfiliado;
		this.ordenPrevia = ordenPrevia;
		this.conocimiento = conocimiento;
		this.equipo = equipo;
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
	public String getEsAfiliado() {
		// TODO Auto-generated method stub
		return esAfiliado;
	}

	@Override
	public String getOrdenPrevia() {
		// TODO Auto-generated method stub
		return ordenPrevia;
	}

	@Override
	public String getConocimiento() {
		// TODO Auto-generated method stub
		return conocimiento;
	}

	@Override
	public String getEquipo() {
		// TODO Auto-generated method stub
		return equipo;
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
		return "ProcedimientoEsp [id=" + id + ", esAfiliado=" + esAfiliado + ", ordenPrevia=" + ordenPrevia
				+ ", conocimiento=" + conocimiento + ", equipo=" + equipo + ", idIPS=" + idIPS + ", capacidad="
				+ capacidad + ", horarioSemanal=" + horarioSemanal + "]";
	}

}
