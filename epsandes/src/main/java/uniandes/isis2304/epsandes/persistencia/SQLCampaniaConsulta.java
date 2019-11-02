package uniandes.isis2304.epsandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLCampaniaConsulta {
	
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
	public SQLCampaniaConsulta (PersistenciaEPSAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BAR a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idEPS - El identificador del bar
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del bar
	 * @return El número de tuplas insertadas
	 */
	public long adicionarCompaniaConsulta(PersistenceManager pm, long idCampania, 
			long idConsulta, String fechaInicio, String fechaFin, String disponible) 
	{
		
        Query q = pm.newQuery(SQL, "INSERT INTO " + "CAMPANIACONSULTA" + "(idcampania, idconsulta, fechainicio, fechafin, disponible) values (?, ?, ?, ?, ?)");
        q.setParameters(idCampania, idConsulta, fechaInicio, fechaFin, disponible);
        
        
        
        return (long) q.executeUnique();
	}

}
