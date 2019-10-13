package uniandes.isis2304.epsandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLPrestacionServicio {

	/* **********************
	 * 			Constantes
	 ***********************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acÃ¡ para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaEPSAndes.SQL;

	/* **********************
	 * 			Atributos
	 ***********************/
	/**
	 * El manejador de persistencia general de la aplicaciÃ³n
	 */
	private PersistenciaEPSAndes pp;

	/* **********************
	 * 			MÃ©todos
	 ***********************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaciÃ³n
	 */
	public SQLPrestacionServicio (PersistenciaEPSAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BAR a la base de datos de Parranderos
	 */
	public long adicionarPrestacion (PersistenceManager pm, long idServicio, long idResultado) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPrestacionServicio() + "(idServicio, idResultado) values (?, ?)");
        q.setParameters(idServicio, idResultado);
        return (long) q.executeUnique();
	}
	
	

}