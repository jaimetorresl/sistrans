package uniandes.isis2304.epsandes.negocio;

public class PrestacionServicio implements VOPrestacionServicio{
	
	
	private long idResultado;
	
	private long idServicioSalud;
	
	
	
	
	public PrestacionServicio() {
		
		idResultado = 0;
		idServicioSalud = 0;
		
	}
	
	
	public PrestacionServicio(long idResultado, long idServicioSalud) {
		
		this.idResultado = idResultado;
		this.idServicioSalud = idServicioSalud;
		
	}
	

	@Override
	public long getIdResultado() {
		// TODO Auto-generated method stub
		return idResultado;
	}


	@Override
	public long getIdServicioSalud() {
		// TODO Auto-generated method stub
		return idServicioSalud;
	}

}
