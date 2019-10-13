package uniandes.isis2304.epsandes.negocio;

public class Hospitalizacion implements VOHospitalizacion{
	
	private long id;
	
	private String ordenPrevia;
	
	private String esAfiliado;
	
	private int numVisitas;
	
	private long idIPS;
	
	private int capacidad;
	
	private String horarioSemanal;
	
	public Hospitalizacion() {
		
		id = 0;
		ordenPrevia = "";
		esAfiliado = "";
		numVisitas = 0;
		idIPS = 0;
		capacidad = 0;
		horarioSemanal = "";
		
	}
	
	
	public Hospitalizacion(long id, String ordenPrevia, String esAfiliado, int numVisitas, long idIPS, int capacidad, String horarioSemanal) {
		
		this.id = id;
		this.ordenPrevia = ordenPrevia;
		this.esAfiliado = esAfiliado;
		this.numVisitas = numVisitas;
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
	public int getNumVisitas() {
		// TODO Auto-generated method stub
		return numVisitas;
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
		return "Hospitalizacion [id=" + id + ", ordenPrevia=" + ordenPrevia + ", esAfiliado=" + esAfiliado
				+ ", numVisitas=" + numVisitas + ", idIPS=" + idIPS + ", capacidad=" + capacidad + ", horarioSemanal="
				+ horarioSemanal + "]";
	}

}
