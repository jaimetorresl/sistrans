package uniandes.isis2304.epsandes.negocio;

public class InscritoCampania implements VOInscritoCampania{
	
	private long idCampania;
	
	private long idUsuarioIPS;
	
	
	public InscritoCampania() {
		
		idCampania = 0;
		idUsuarioIPS = 0;
		
	}
	
	
	public InscritoCampania(long idCampania, long idUsuarioIPS) {
		
		this.idCampania = idCampania;
		this.idUsuarioIPS = idUsuarioIPS;
		
	}
	
	

	@Override
	public long getIdCampania() {
		// TODO Auto-generated method stub
		return idCampania;
	}

	@Override
	public long getIdUsuarioIPS() {
		// TODO Auto-generated method stub
		return idUsuarioIPS;
	}


	@Override
	public String toString() {
		return "InscritoCampania [idCampania=" + idCampania + ", idUsuarioIPS=" + idUsuarioIPS + "]";
	}
	

}
