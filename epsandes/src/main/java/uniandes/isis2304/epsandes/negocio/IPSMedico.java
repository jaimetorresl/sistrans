package uniandes.isis2304.epsandes.negocio;

public class IPSMedico implements VOIPSMedico{
	
	
	private long idMedico;
	
	private long idIPS;
	
	
	public IPSMedico() {
		
		
	}
	
	
	public IPSMedico(long idMedico, long idIPS) {
		
		this.idMedico = idMedico;
		this.idIPS = idIPS;
		
	}

	@Override
	public long getIdMedico() {
		// TODO Auto-generated method stub
		return idMedico;
	}

	@Override
	public long getIdIPS() {
		// TODO Auto-generated method stub
		return idIPS;
	}


	@Override
	public String toString() {
		return "IPSMedico [idMedico=" + idMedico + ", idIPS=" + idIPS + "]";
	}


}