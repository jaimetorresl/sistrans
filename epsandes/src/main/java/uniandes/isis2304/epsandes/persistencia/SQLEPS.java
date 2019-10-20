/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.epsandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


import uniandes.isis2304.epsandes.negocio.EPS;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLEPS 
{
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
	public SQLEPS (PersistenciaEPSAndes pp)
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
	public long adicionarEPS(PersistenceManager pm, long idEPS, String nombre) 
	{
		//System.out.println("persistence "+ idEPS + nombre);
		//System.out.println("eps tabla " + pp.darTablaEPS());
		
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEPS () + "(id, nombre) values (?, ?)");
        
       System.out.println(q.executeUnique().toString());
        
        q.setParameters(idEPS, nombre);
        
        System.out.println();
        
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreEPS - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEPSPorNombre (PersistenceManager pm, String nombreEPS)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEPS () + " WHERE nombre = ?");
        q.setParameters(nombreEPS);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BAR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idEPS - El identificador del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEPSPorId (PersistenceManager pm, long idEPS)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEPS () + " WHERE id = ?");
        q.setParameters(idEPS);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idEPS - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public EPS darEPSPorId (PersistenceManager pm, long idEPS) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEPS () + " WHERE id = ?");
		q.setResultClass(EPS.class);
		q.setParameters(idEPS);
		return (EPS) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre de bar buscado
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<EPS> darEPSPorNombre (PersistenceManager pm, String nombreBar) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEPS () + " WHERE nombre = ?");
		q.setResultClass(EPS.class);
		q.setParameters(nombreBar);
		return (List<EPS>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<EPS> darEPSs (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEPS ());
		q.setResultClass(EPS.class);
		return (List<EPS>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para aumentar en uno el número de sedes de los bares de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param ciudad - La ciudad a la cual se le quiere realizar el proceso
	 * @return El número de tuplas modificadas
	 */
	/**
	public long aumentarSedesBaresCiudad (PersistenceManager pm, String ciudad)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaBar () + " SET cantsedes = cantsedes + 1 WHERE ciudad = ?");
        q.setParameters(ciudad);
        return (long) q.executeUnique();
	}
	*/
	
}
