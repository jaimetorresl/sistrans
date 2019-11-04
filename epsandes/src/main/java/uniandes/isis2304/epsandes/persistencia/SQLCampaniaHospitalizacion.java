package uniandes.isis2304.epsandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLCampaniaHospitalizacion {
	
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
	public SQLCampaniaHospitalizacion (PersistenciaEPSAndes pp)
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
	public long adicionarCampaniaHospitalizacion(PersistenceManager pm, long idCampania, 
			long idHospitalizacion, String fechaInicio, String fechaFin, String disponible) 
	{
		
        Query q = pm.newQuery(SQL, "INSERT INTO " + "CAMPANIA_HOSPITALIZACIONP" + "(id, idhospitalizacion, fechainicio, fechafin, disponible) values (?, ?, ?, ?, ?)");
        q.setParameters(idCampania, idHospitalizacion, fechaInicio, fechaFin, disponible);
        
        
        
        return (long) q.executeUnique();
	}

	
	public long eliminarCampaniaHospitalizacionPorId (PersistenceManager pm, long idCampania, long idHospitalizacion, int eliminar)
	{
		Query q;


		q = pm.newQuery(SQL, "UPDATE CAMPANIA_HOSPITALIZACION SET DISPONIBLE = 'NO'  WHERE idhospitalizacion = ? AND id = ?");

		q.setParameters(idHospitalizacion, idCampania);
		
		q.executeUnique();


		if(eliminar == 0) {
			
			Query x = pm.newQuery(SQL, "DELETE FROM " + "CAMPANIA_HOSPITALIZACION" + " WHERE id = ? AND idhospitalizacion = ?");
			x.setParameters(idCampania, idHospitalizacion);
			x.executeUnique();

		}

		
		
		return 1;
	}

}
