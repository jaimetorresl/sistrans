package uniandes.isis2304.epsandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLCampaniaPrevencion {
	
	
	private final static String SQL = PersistenciaEPSAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaEPSAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLCampaniaPrevencion (PersistenciaEPSAndes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarCampaniaPrevencion(PersistenceManager pm, long id, 
			String localizacion, String fechaInicio, String fechaFin, long idEPS ) 
	{
		
        Query q = pm.newQuery(SQL, "INSERT INTO " + "CAMPANIA_PREVENCION" + "(id, localizacion, fechainicio, fechafin, ideps) values (?, ?, ?, ?, ?)");
        q.setParameters(id, localizacion, fechaInicio, fechaFin, idEPS);
        
        return (long) q.executeUnique();
	}

}
