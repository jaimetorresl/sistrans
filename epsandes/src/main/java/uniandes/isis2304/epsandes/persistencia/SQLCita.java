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

import java.math.BigDecimal;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.epsandes.negocio.Cita;
import uniandes.isis2304.epsandes.negocio.EPS;
import uniandes.isis2304.epsandes.negocio.Cita;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLCita 
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
	public SQLCita (PersistenciaEPSAndes pp)
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
	public long adicionarCita (PersistenceManager pm, long id, String horaInicio, String horaFin, long idMedico, long idConsulta, long idTerapia, long idProcedimientoEsp, long idHospitalizacion, long idUsuarioIPS) 
			throws Exception{


		String tabla = "";
		
		long idSS = 0;

		if(idConsulta != 0) {

			tabla = "CONSULTA";
			idSS = idConsulta;

		} else if(idTerapia != 0) {
			
			tabla = "TERAPIA";
			idSS = idTerapia;

		} else if(idProcedimientoEsp != 0) {

			tabla = "PROCEDIMIENTO_ESP";
			idSS = idProcedimientoEsp;

		} else if(idHospitalizacion != 0) {

			tabla = "HOSPITALIZACION";
			idSS = idHospitalizacion;

		} 





		//Verifica que el medico trabaja en la IPS que presta ese servicio de salud        
		Query darIPSSS = pm.newQuery(SQL, "SELECT idips FROM " + tabla + " WHERE id = ?");
		darIPSSS.setParameters(idSS);

		Query darIPSMedico = pm.newQuery(SQL, "SELECT idips FROM " + "IPS_MEDICO" + " WHERE idmedico = ?");
		darIPSMedico.setParameters(idMedico);


		BigDecimal result = (BigDecimal) darIPSSS.executeUnique();
		BigDecimal result2 = (BigDecimal) darIPSMedico.executeUnique();


		long resultIPSSS = result.longValue();
		long resultIPSMedico = result2.longValue();


		if(resultIPSSS != resultIPSMedico) {

			throw new Exception("El medico no pertenece a la ips que ofrece ese servicio de salud");

		}


		//Verifica que el usuario pertenezca a una EPS que tenga esa IPS que presta ese servicio de salud

		Query darEPSUsuario = pm.newQuery(SQL, "SELECT ideps FROM " + "USUARIO_IPS" + " WHERE numdocumento = ?");
		darEPSUsuario.setParameters(idUsuarioIPS);

		BigDecimal idEPSUsuario = (BigDecimal) darEPSUsuario.executeUnique();
		long idEPSUsuario2 = idEPSUsuario.longValue();

		Query darEPSCita = pm.newQuery(SQL, "SELECT ideps FROM " + "IPS" + " WHERE id = ?");
		darEPSCita.setParameters(resultIPSSS);

		BigDecimal idEPSCita = (BigDecimal) darEPSCita.executeUnique();
		long idEPSCita2 = idEPSCita.longValue();



		if(idEPSUsuario2 != idEPSCita2) {

			throw new Exception("El usuario no pertenece a la IPS que contiene este servicio de salud");

		}


		//Verificar que la hora de la cita este en los rangos de horas del ss

		Query darHora = pm.newQuery(SQL, "SELECT id FROM " + tabla + " WHERE id = ? AND (TO_DATE(?,'DD-MM-YY HH24:MI:SS') BETWEEN TO_DATE(horainicio,'DD-MM-YY HH24:MI:SS') AND TO_DATE(horafin,'DD-MM-YY HH24:MI:SS')) "
				+ "AND (TO_DATE(?,'DD-MM-YY HH24:MI:SS') BETWEEN TO_DATE(horainicio,'DD-MM-YY HH24:MI:SS') AND TO_DATE(horafin,'DD-MM-YY HH24:MI:SS'))");

		darHora.setParameters(idSS, horaInicio, horaFin);

		BigDecimal idHoraSS = (BigDecimal)darHora.executeUnique(); 
		long idHoraSS2 = idHoraSS.longValue();
		

		if(idHoraSS2 != idSS) {

			throw new Exception("El rango horario de la cita no coincide con el del servicio de salud");

		}


		//Verifica que el Usuario de la IPS puede acceder a este servicio de salud (afiliado o no)

		Query darAfiliadoUsuario = pm.newQuery(SQL, "SELECT esafiliado FROM USUARIO_IPS WHERE numdocumento = ?");
		darAfiliadoUsuario.setParameters(idUsuarioIPS);
		
		String resultAfiliado = (String) darAfiliadoUsuario.executeUnique();
		
		
		Query darAfiliadoSS = pm.newQuery(SQL, "SELECT esafiliado FROM " + tabla +  " WHERE id = ?");
		darAfiliadoSS.setParameters(idSS);
		
		
		String resultAfiliadoSS = (String) darAfiliadoSS.executeUnique();
		
		if(resultAfiliadoSS.equals("SI") && resultAfiliado.equals("NO")) {
			
			throw new Exception("El usuario no puede hacer uso del ss ya que no es afiliado");
			
		}
		


		Query q = pm.newQuery(SQL, "INSERT INTO " + "CITA" + "(id, horainicio, horafin, idmedico, idconsulta, idterapia, idprocedimientoesp, idhospitalizacion, idusuarioips) values (?,?,?,?,?,?,?,?,?)");
		q.setParameters(id, horaInicio, horaFin, idMedico, null, idTerapia, null, null, idUsuarioIPS);
		
		
		
		if(idConsulta != 0) {

			q.setParameters(id, horaInicio, horaFin, idMedico, idConsulta, null, null, null, idUsuarioIPS);

		} else if(idTerapia != 0) {
			
			q.setParameters(id, horaInicio, horaFin, idMedico, null, idTerapia, null, null, idUsuarioIPS);
			
		} else if(idProcedimientoEsp != 0) {

			q.setParameters(id, horaInicio, horaFin, idMedico, null, null, idProcedimientoEsp, null, idUsuarioIPS);

		} else if(idHospitalizacion != 0) {

			q.setParameters(id, horaInicio, horaFin, idMedico, null, null, null, idHospitalizacion, idUsuarioIPS);

		} 

		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BARES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCitaPorNombre (PersistenceManager pm, String nombre)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita () + " WHERE nombre = ?");
		q.setParameters(nombre);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BAR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCitaPorId (PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita () + " WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BAR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del bar
	 * @return El objeto BAR que tiene el identificador dado
	 */
	public Cita darCitaPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita () + " WHERE id = ?");
		q.setResultClass(Cita.class);
		q.setParameters(id);
		return (Cita) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre de bar buscado
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<Cita> darCitaPorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita () + " WHERE nombre = ?");
		q.setResultClass(Cita.class);
		q.setParameters(nombre);
		return (List<Cita>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BARES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Cita> darCitas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita ());
		q.setResultClass(Cita.class);
		return (List<Cita>) q.executeList();
	}
	
	public List<Object []> darCitasMasPedidas (PersistenceManager pm,String fechaInicio, String fechaFin)
	{
		String sql = "SELECT* FROM";
		sql +="(SELECT cita.idConsulta, cita.idTerapia, cita.idProcedimientoEsp, cita.idHospitalizacion";
		sql += "COUNT(cita.idConsulta, cita.idTerapia, cita.idProcedimientoEsp, cita.idHospitalizacion) AS CUANTOS";
		sql	+= "FROM cita";
		sql	+= "INNER JOIN consulta ON cita.idConsulta = consulta.id";
		sql	+= "INNER JOIN terapia ON cita.idTerapia = terapia.id";
		sql	+= "INNER JOIN procedimiento_esp ON cita.procedimientoEsp = procedimiento_esp.id";
		sql	+= "INNER JOIN hospitalizacion ON cita.idHospitalizacion = hospitalizacion.id";
		sql	+= "WHERE TO_DATE('cita.fechaInicio', 'YYYY-MM-DD')>= TO_DATE('"+fechaInicio+",'YYYY-MM-DD')";
		sql	+= "AND TO_DATE('cita.fechaFin', 'YYYY-MM-DD')<= TO_DATE('"+fechaFin+",'YYYY-MM-DD')";
	  	sql	+= "GROUP BY (cita.idConsulta, cita.idTerapia, cita.idProcedimientoEsp, cita.idHospitalizacion)";
	  	sql	+= "ORDER BY CUANTOS DESC)t";
	  	sql	+="WHERE ROWNUM BETWEN 1 AND 20";
		
	  	Query q = pm.newQuery(SQL, sql);
		
		return q.executeList();
	}


}
