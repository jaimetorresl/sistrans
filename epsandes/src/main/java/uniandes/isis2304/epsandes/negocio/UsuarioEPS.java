package uniandes.isis2304.epsandes.negocio;

public class UsuarioEPS implements VOUsuarioEPS{
	
	
	public long id;
	
	public String nombre;
	
	public int rol;
	
	public long idEPS;
	
	public String correo;
	
	public UsuarioEPS() {
		
		id = 0;
		nombre = "";
		rol = 0;
		idEPS = 0;
		correo = "";
		
	}
	
	
	public UsuarioEPS(long id, String nombre, int rol, long idEPS, String correo) {
		
		this.id = id;
		this.nombre = nombre;
		this.rol = rol;
		this.idEPS = idEPS;
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
	public long getIdEPS() {
		// TODO Auto-generated method stub
		return idEPS;
	}

	@Override
	public String getCorreo() {
		// TODO Auto-generated method stub
		return correo;
	}


	@Override
	public String toString() {
		return "UsuarioEPS [id=" + id + ", nombre=" + nombre + ", rol=" + rol + ", idEPS=" + idEPS + ", correo="
				+ correo + "]";
	}
	
	
	

}
