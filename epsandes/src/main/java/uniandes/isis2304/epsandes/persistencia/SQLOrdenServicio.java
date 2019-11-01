package uniandes.isis2304.epsandes.persistencia;

import java.math.BigDecimal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.sun.org.apache.regexp.internal.RESyntaxException;

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
	public long adicionarOrdenServicio(PersistenceManager pm, long idOrden, long idReceta) throws Exception
	{

//		long xReturn = 0;
//
//
//		Query q1 = pm.newQuery(SQL, "SELECT idreceta FROM ORDEN_SERVICIO WHERE idReceta = ?");
//		q1.setParameters(idReceta);
//
//
//		BigDecimal resultQ1 = (BigDecimal) q1.executeUnique();


//		if(resultQ1 == null) {

			Query q = pm.newQuery(SQL, "INSERT INTO " + "ORDEN_SERVICIO" + "(id, idReceta) values (?, ?)");
			q.setParameters(idOrden, idReceta);
			return (long) q.executeUnique();


//		} else {
//
//
//			Query idOSS = pm.newQuery(SQL, "SELECT id FROM ORDEN_SERVICIO WHERE idReceta = ?");
//			idOSS.setParameters(idReceta);
//
//			BigDecimal resultIOSS = (BigDecimal) q1.executeUnique();
//			long longResult = resultIOSS.longValue();
//
//			xReturn = longResult;
//
//
//		}


		//return xReturn;

	}




	public long retornarOrdenServicio(PersistenceManager pm, long idReceta) {

		long xReturn = 0;


		Query q1 = pm.newQuery(SQL, "SELECT id FROM ORDEN_SERVICIO WHERE idReceta = ?");
		q1.setParameters(idReceta);


		BigDecimal resultQ1 = (BigDecimal) q1.executeUnique();
		
		
		if(resultQ1 != null) {
			
			long resultQ1Long = resultQ1.longValue();
			xReturn = resultQ1Long;
				
		}
		
		return xReturn;

	}


}