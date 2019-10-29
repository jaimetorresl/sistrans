package uniandes.isis2304.epsandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLOrdenServicio {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
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
	public SQLOrdenServicio(PersistenciaEPSAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL
	 */
	public long adicionarOrdenTerapia(PersistenceManager pm, long idOrden, long idTerapia) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + "ORDEN_TERAPIA" + "(idOrden, idTerapia) values (?, ?)");
        q.setParameters(idOrden, idTerapia);
        return (long) q.executeUnique();
	}


}