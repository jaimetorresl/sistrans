package uniandes.isis2304.epsandes.negocio;

public class OrdenServicio implements VOOrdenServicio{
	
	private long id;
	
	private long idReceta;
	
	public OrdenServicio() {
		
		id = 0;
		idReceta = 0;
		
	}
	
	
	public OrdenServicio(long id, long idReceta) {
		
		this.id = id;
		this.idReceta = idReceta;
		
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public long getIdReceta() {
		// TODO Auto-generated method stub
		return idReceta;
	}


	@Override
	public String toString() {
		return "OrdenServicio [id=" + id + ", idReceta=" + idReceta + "]";
	}

}
