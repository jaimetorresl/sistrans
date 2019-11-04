package uniandes.isis2304.epsandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLCampaniaTerapia {
	
	
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
	public SQLCampaniaTerapia (PersistenciaEPSAndes pp)
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
	public long adicionarCampaniaTerapia(PersistenceManager pm, long idCampania, 
			long idTerapia, String fechaInicio, String fechaFin, String disponible) 
	{
		
        Query q = pm.newQuery(SQL, "INSERT INTO " + "CAMPANIA_TERAPIA" + "(id, idterapia, fechainicio, fechafin, disponible) values (?, ?, ?, ?, ?)");
        q.setParameters(idCampania, idTerapia, fechaInicio, fechaFin, disponible);
        
        
        
        return (long) q.executeUnique();
	}
	
	
	public long eliminarCampaniaTerapiaPorId (PersistenceManager pm, long idCampania, long idTerapia, int eliminar)
	{
		Query q;


		q = pm.newQuery(SQL, "UPDATE CAMPANIA_TERAPIA SET DISPONIBLE = 'NO'  WHERE idterapia = ? AND id = ?");

		q.setParameters(idTerapia, idCampania);
		
		q.executeUnique();


		if(eliminar == 0) {
			
			Query x = pm.newQuery(SQL, "DELETE FROM " + "CAMPANIA_TERAPIA" + " WHERE id = ? AND idterapia = ?");
			x.setParameters(idCampania, idTerapia);
			x.executeUnique();

		}

		
		
		return 1;
	}
	
	
	public void reapertura(PersistenceManager pm, long idCampania, long idTerapia) {
		
		

		Query q;


		q = pm.newQuery(SQL, "UPDATE CAMPANIA_TERAPIA SET DISPONIBLE = 'SI'  WHERE idTerapia = ? AND id = ?");

		q.setParameters(idTerapia, idCampania);
		
		q.executeUnique();
		
		
	}


}
