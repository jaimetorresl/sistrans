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
import uniandes.isis2304.epsandes.negocio.Hospitalizacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLHospitalizacion 
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
	public SQLHospitalizacion (PersistenciaEPSAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BAR a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del bar
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHospitalizacion (PersistenceManager pm, long id, String ordenPrevia, String esAfiliado, int numVisitas, long idIPS, int capacidad, 
			String horaInicio, String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + "HOSPITALIZACION" + "(id, ordenPrevia, esAfiliado, numVisitas, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, idRecepcionista, reservado) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, ordenPrevia, esAfiliado, numVisitas, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, idRecepcionista, "NO");
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BAR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHospitalizacionPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHospitalizacion () + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
	
	public void cambiarReservaHospitalizacion (PersistenceManager pm, long idHospitalizacion)
	{
		
		
		Query select = pm.newQuery(SQL, "SELECT RESERVADO FROM HOSPITALIZACION WHERE id = ?");
		select.setParameters(idHospitalizacion);

		String reservado = (String) select.executeUnique();
		

		Query q;

		if(reservado.equals("SI")) {
			
			q = pm.newQuery(SQL, "UPDATE HOSPITALIZACION SET RESERVADO = 'NO'  WHERE id = ?");

		} else {
			
			
			q = pm.newQuery(SQL, "UPDATE HOSPITALIZACION SET RESERVADO = 'SI'  WHERE id = ?");

		}

		q.setParameters(idHospitalizacion);
		
		q.executeUnique();

		//return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Hospitalizacion darHospitalizacionPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHospitalizacion () + " WHERE id = ?");
		q.setResultClass(Hospitalizacion.class);
		q.setParameters(id);
		return (Hospitalizacion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Hospitalizacion> darHospitalizacions (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHospitalizacion ());
		q.setResultClass(Hospitalizacion.class);
		return (List<Hospitalizacion>) q.executeList();
	}
	
}
