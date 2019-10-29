
package uniandes.isis2304.epsandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.epsandes.negocio.Medico;
import uniandes.isis2304.epsandes.negocio.RecepcionistaIPS;

public class SQLRecepcionistaIPS {
	
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
	public SQLRecepcionistaIPS (PersistenciaEPSAndes pp)
	{
		this.pp = pp;
	}
	

	public long adicionarRecepcionistaIPS(PersistenceManager pm, long id, String nombre, int rol, long idIPS, String correo) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + "RECEPCIONISTA_IPS" + "(id, nombre, rol, idIPS, correo) values (?, ?, ?, ?, ?)");
        q.setParameters(id, nombre, rol, idIPS, correo);
        return (long) q.executeUnique();
	}
	
	
	public RecepcionistaIPS darRecepcionistaPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "RECEPCIONISTA_IPS" + " WHERE id = ?");
		q.setResultClass(RecepcionistaIPS.class);
		q.setParameters(id);
		return (RecepcionistaIPS) q.executeUnique();
	}

}
