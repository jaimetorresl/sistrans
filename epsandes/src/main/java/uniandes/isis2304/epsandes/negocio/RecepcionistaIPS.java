package uniandes.isis2304.epsandes.negocio;

public class RecepcionistaIPS implements VORecepcionistaIPS{
	

	private long id;
	
	private String nombre;
	
	private int rol;
	
	private long idIPS;
	
	private String correo;
	
	public RecepcionistaIPS() {
		
		id = 0;
		nombre = "";
		rol = 0;
		idIPS = 0;
		correo = "";
		
	}
	
	
	public RecepcionistaIPS(long id, String nombre, int rol, long idIPS, String correo) {
		
		this.id = id;
		this.nombre = nombre;
		this.rol = rol;
		this.idIPS = idIPS;
		this.correo = correo;
		
	}
	

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public int getRol() {
		// TODO Auto-generated method stub
		return rol;
	}

	@Override
	public long getIdIPS() {
		// TODO Auto-generated method stub
		return idIPS;
	}

	@Override
	public String getCorreo() {
		// TODO Auto-generated method stub
		return correo;
	}


	@Override
	public String toString() {
		return "UsuarioEPS [id=" + id + ", nombre=" + nombre + ", rol=" + rol + ", idIPS=" + idIPS + ", correo="
				+ correo + "]";
	}
	
	

}
