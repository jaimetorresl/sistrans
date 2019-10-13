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

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
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
	public SQLUtil (PersistenciaEPSAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqParranderos () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarEPSAndes (PersistenceManager pm)
	{
        Query qAdministradorEPS = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAdministradorEPS() );          
        Query qEPS = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEPS() );
        Query qIPS = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaIPS() );
        Query qMedico = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMedico() );
        Query qPaciente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPaciente() );
        Query qResultado = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResultado () );
        Query qServicioSalud = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicioSalud () );

        long administradoresEPSEliminados = (long) qAdministradorEPS.executeUnique ();
        long epsEliminadas = (long) qEPS.executeUnique ();
        long ipsEliminadas = (long) qIPS.executeUnique ();
        long medicoEliminados = (long) qMedico.executeUnique ();
        long pacienteEliminados = (long) qPaciente.executeUnique ();
        long resultadoEliminados = (long) qResultado.executeUnique ();
        long servicioSaludEliminados = (long) qServicioSalud.executeUnique ();
        return new long[] {administradoresEPSEliminados, epsEliminadas, ipsEliminadas, 
        		medicoEliminados, pacienteEliminados, resultadoEliminados, servicioSaludEliminados};
	}

}