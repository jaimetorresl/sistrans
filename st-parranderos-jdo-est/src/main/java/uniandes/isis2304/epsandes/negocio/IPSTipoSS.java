package uniandes.isis2304.epsandes.negocio;

public class IPSTipoSS implements VOIPSTipoSS{
	
	private long id;
	
	private String tipoSS;
	
	private int capacidad;
	
	
	public IPSTipoSS()
	{
		
		
	}
	
	
	public IPSTipoSS(long id, String tipoSS, int capacidad) {
		
		this.id = id;
		this.tipoSS = tipoSS;
		this.capacidad = capacidad;
		
		
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getTipoSS() {
		// TODO Auto-generated method stub
		return tipoSS;
	}

	@Override
	public int getCapacidad() {
		// TODO Auto-generated method stub
		return capacidad;
	}
	

}