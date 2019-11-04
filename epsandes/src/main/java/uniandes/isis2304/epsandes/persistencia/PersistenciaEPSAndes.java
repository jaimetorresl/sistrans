package uniandes.isis2304.epsandes.persistencia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.epsandes.negocio.CampaniaConsulta;
import uniandes.isis2304.epsandes.negocio.CampaniaHospitalizacion;
import uniandes.isis2304.epsandes.negocio.CampaniaPrevencion;
import uniandes.isis2304.epsandes.negocio.CampaniaProcedimientoEsp;
import uniandes.isis2304.epsandes.negocio.CampaniaTerapia;
import uniandes.isis2304.epsandes.negocio.Cita;
import uniandes.isis2304.epsandes.negocio.Consulta;
import uniandes.isis2304.epsandes.negocio.EPS;
import uniandes.isis2304.epsandes.negocio.EPSAndes;
import uniandes.isis2304.epsandes.negocio.Hospitalizacion;
import uniandes.isis2304.epsandes.negocio.IPS;
import uniandes.isis2304.epsandes.negocio.IPSMedico;
import uniandes.isis2304.epsandes.negocio.Medico;
import uniandes.isis2304.epsandes.negocio.OrdenConsulta;
import uniandes.isis2304.epsandes.negocio.OrdenHospitalizacion;
import uniandes.isis2304.epsandes.negocio.OrdenProcedimientoEsp;
import uniandes.isis2304.epsandes.negocio.OrdenServicio;
import uniandes.isis2304.epsandes.negocio.OrdenTerapia;
import uniandes.isis2304.epsandes.negocio.ProcedimientoEsp;
import uniandes.isis2304.epsandes.negocio.RecepcionistaIPS;
import uniandes.isis2304.epsandes.negocio.Receta;
import uniandes.isis2304.epsandes.negocio.Terapia;
import uniandes.isis2304.epsandes.negocio.UsuarioEPS;
import uniandes.isis2304.epsandes.negocio.UsuarioIPS;

public class PersistenciaEPSAndes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(EPSAndes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el �nico objeto de la clase - Patr�n SINGLETON
	 */
	private static PersistenciaEPSAndes instance;

	/**
	 * F�brica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla EPS de la base de datos
	 */
	private SQLEPS sqlEPS;

	/**
	 * Atributo para el acceso a la tabla IPS de la base de datos
	 */
	private SQLIPS sqlIPS;

	/**
	 * Atributo para el acceso a la tabla UsuarioEPS  de la base de datos
	 */
	private SQLUsuarioEPS sqlUsuarioEPS;

	/**
	 * Atributo para el acceso a la tabla UsuarioIPS de la base de datos
	 */
	private SQLUsuarioIPS sqlUsuarioIPS;

	private SQLRecepcionistaIPS sqlRecepcionistaIPS;

	/**
	 * Atributo para el acceso a la tabla Medico de la base de datos
	 */
	private SQLMedico sqlMedico;


	//	/**
	//	 * Atributo para el acceso a la tabla RecepcionistaIPS de la base de datos
	//	 */
	//	private SQLRecepcionistaIPS sqlRecepcionistaIPS;

	/**
	 * Atributo para el acceso a la tabla Receta de la base de datos
	 */
	private SQLReceta sqlReceta;


	private SQLCita sqlCita;

	private SQLIPSMedico sqlIpsMedico;

	private SQLConsulta sqlConsulta;

	private SQLTerapia sqlTerapia;

	private SQLProcedimientoEsp sqlProcedimientoEsp;

	private SQLHospitalizacion sqlHospitalizacion;

	private SQLOrdenServicio sqlOrdenServicio;

	private SQLOrdenConsulta sqlOrdenConsulta;

	private SQLOrdenTerapia sqlOrdenTerapia;

	private SQLOrdenProcedimientoEsp sqlOrdenProcedimiento;

	private SQLOrdenHospitalizacion sqlOrdenHospitalizacion;

	// ITERACION 2
	
	private SQLCampaniaPrevencion sqlCampaniaPrevencion;
	
	private SQLCampaniaConsulta sqlCampaniaConsulta;
	
	private SQLCampaniaTerapia sqlCampaniaTerapia;
	
	private SQLCampaniaProcedimiento sqlCampaniaProcedimiento;
	
	private SQLCampaniaHospitalizacion sqlCampaniaHospitalizacion;


	/* ****************************************************************
	 * 			M�todos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patr�n SINGLETON
	 */
	private PersistenciaEPSAndes() {

		pmf = JDOHelper.getPersistenceManagerFactory("EPSAndes");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("EPSAndes_sequence");
		tablas.add ("EPS");
		tablas.add ("IPS");
		tablas.add ("USUARIOIPS");
		tablas.add ("USUARIOEPS");
		tablas.add ("MEDICO");
		tablas.add ("CONSULTA");
		tablas.add ("TERAPIA");
		tablas.add ("PROCEDIMIENTOESP");
		tablas.add ("HOSPITALIZACION");
		tablas.add ("RECETA");
		tablas.add ("CITA");
		tablas.add ("IPSMEDICO");
		//tablas.add ("RECEPCIONISTAIPS");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patr�n SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaEPSAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		System.out.println("---------------------> "+unidadPersistencia+"  {"+tableConfig+" }");
		System.out.println("Working Directory = " +
				System.getProperty("user.dir"));		
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el �nico objeto PersistenciaParranderos existente - Patr�n SINGLETON
	 */
	public static PersistenciaEPSAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaEPSAndes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el �nico objeto PersistenciaParranderos existente - Patr�n SINGLETON
	 */
	public static PersistenciaEPSAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaEPSAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexi�n con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{


		sqlEPS = new SQLEPS(this);
		sqlIPS = new SQLIPS(this);
		sqlUsuarioEPS = new SQLUsuarioEPS(this);
		sqlUsuarioIPS = new SQLUsuarioIPS(this);
		sqlMedico = new SQLMedico(this);
		sqlReceta = new SQLReceta(this);
		sqlCita = new SQLCita(this);
		sqlConsulta = new SQLConsulta(this);
		sqlTerapia = new SQLTerapia(this);
		sqlProcedimientoEsp = new SQLProcedimientoEsp(this);
		sqlHospitalizacion = new SQLHospitalizacion(this);
		sqlIpsMedico = new SQLIPSMedico(this);
		sqlRecepcionistaIPS = new SQLRecepcionistaIPS(this);
		sqlUtil = new SQLUtil(this);
		sqlOrdenConsulta = new SQLOrdenConsulta(this);
		sqlOrdenTerapia = new SQLOrdenTerapia(this);
		sqlOrdenProcedimiento = new SQLOrdenProcedimientoEsp(this);
		sqlOrdenHospitalizacion = new SQLOrdenHospitalizacion(this);
		sqlOrdenServicio = new SQLOrdenServicio(this);
		sqlCampaniaPrevencion = new SQLCampaniaPrevencion(this);
		sqlCampaniaConsulta = new SQLCampaniaConsulta(this);
		sqlCampaniaTerapia = new SQLCampaniaTerapia(this);
		sqlCampaniaProcedimiento = new SQLCampaniaProcedimiento(this);
		sqlCampaniaHospitalizacion = new SQLCampaniaHospitalizacion(this);

	}



	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoBebida de parranderos
	 */
	public String darTablaEPS ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaIPS ()
	{
		return tablas.get (2);
	}

	public String darTablaUsuarioEPS()
	{
		return tablas.get (3);
	}

	public String darTablaUsuarioIPS()
	{
		return tablas.get (4);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaMedico ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaConsulta ()
	{
		return tablas.get (6);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaTerapia()
	{
		return tablas.get (7);
	}


	public String darTablaProcedimientoEsp()
	{
		return tablas.get (8);
	}


	public String darTablaHospitalizacion()
	{
		return tablas.get (9);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaReceta()
	{
		return tablas.get (10);
	}

	public String darTablaIPSMedico() {

		return tablas.get(11);

	}

	public String darTablaCita() {

		return tablas.get(12);

	}


	//	/**
	//	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	//	 */
	//	public String darTablaRecepcionistaIPS ()
	//	{
	//		return tablas.get (13);
	//	}

	/**
	 * Transacci�n para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicaci�n
	 * @return El siguiente n�mero del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}


	/* ****************************************************************
	 * 			M�todos para manejar las EPS
	 *****************************************************************/

	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla PrestacionServicio
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public EPS registrarEPS(String nombre)
	{

		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlEPS.adicionarEPS(pm, id, nombre);
			tx.commit();

			log.trace ("Inserci�n de EPS: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new EPS(id, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			M�todos para manejar los IPS
	 *****************************************************************/

	public IPS registrarIPS(String nombre, String tipo, String localizacion, long idEPS)
	{

		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlIPS.adicionarIPS(pm, id, nombre, localizacion, idEPS, tipo);
			tx.commit();

			log.trace ("Inserci�n de IPS: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new IPS(id, nombre, tipo, localizacion, idEPS);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	public RecepcionistaIPS registrarRecepcionistaIPS(String nombre, int rol, long idIPS, String correo)
	{

		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlRecepcionistaIPS.adicionarRecepcionistaIPS(pm, id, nombre, rol, idIPS, correo);


			tx.commit();

			log.trace ("Inserci�n de Recepcionista: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new RecepcionistaIPS(id, nombre, rol, idIPS, correo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public RecepcionistaIPS darRecepcionistaIPS(long id)
	{

		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			RecepcionistaIPS recepcionista = sqlRecepcionistaIPS.darRecepcionistaPorId(pm, id);

			tx.commit();

			log.trace ("Dar Recepcionista: " + id + ": tuplas insertadas");

			return recepcionista;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			M�todos para manejar los USUARIOS
	 *****************************************************************/

	public UsuarioEPS registrarUsuarioEPS(String nombre, int rol, long idEPS, String correo)
	{

		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlUsuarioEPS.adicionarUsuarioEPS(pm, id, nombre, rol, idEPS, correo);
			tx.commit();

			log.trace ("Inserci�n de UsuarioEPS: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new UsuarioEPS(id, nombre, rol, idEPS, correo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public UsuarioEPS darUsuarioEPS(long id)
	{

		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			UsuarioEPS usuario = sqlUsuarioEPS.darUsuarioEPSPorId(pm, id);

			tx.commit();

			log.trace ("Dar usuario: " + id + ": tuplas insertadas");

			return usuario;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public UsuarioIPS registrarUsuarioIPS(String nombre, String estado, long numDocumento, int tipoDocumento, String fechaNacimiento, long idEPS, String esAfiliado, String correo, String genero, int edad) {

		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlUsuarioIPS.adicionarUsuarioIPS(pm, nombre, estado, numDocumento, tipoDocumento, fechaNacimiento, idEPS, esAfiliado, correo, genero, edad);
			tx.commit();

			log.trace ("Inserci�n de UsuarioEPS: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new UsuarioIPS(nombre, estado, numDocumento, tipoDocumento, fechaNacimiento, idEPS, esAfiliado, correo, edad, genero);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			M�todos para manejar los MEDICOS
	 *****************************************************************/


	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla Medico
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public Medico registrarMedico(String nombre, String especialidad, long numRegMedico)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlMedico.adicionarMedico(pm, id, especialidad, numRegMedico, nombre);
			tx.commit();

			log.trace ("Inserci�n de medico: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Medico(id, nombre, especialidad, numRegMedico);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public IPSMedico registrarMedicoIPS(long idMedico, long idIPS)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlIpsMedico.adicionarIPSMedico(pm, idMedico, idIPS);
			tx.commit();

			log.trace ("Asociacion de medico: " + idMedico + ": " + tuplasInsertadas + " tuplas insertadas");

			return new IPSMedico(idMedico, idIPS);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/* ****************************************************************
	 * 			M�todos para manejar los SERVICIOS DE SALUD
	 *****************************************************************/

	public Consulta registarConsulta(String esAfiliado, String ordenPrevia, long idIPS, int capacidad, String horaInicio,
			String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlConsulta.adicionarConsulta(pm, id, ordenPrevia, esAfiliado, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
			tx.commit();

			log.trace ("Inserci�n de consulta afiliada a la ips: " + idIPS + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Consulta(id, esAfiliado, ordenPrevia, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista, "NO");
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	public Terapia registrarTerapia(String esAfiliado, String ordenPrevia, String numSesiones, String tipoTerapia, long idIPS, int capacidad,
			String horaInicio, String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlTerapia.adicionarTerapia(pm, id, ordenPrevia, esAfiliado, numSesiones, tipoTerapia, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
			tx.commit();

			log.trace ("Inserci�n de terapia afiliada a la ips: " + idIPS + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Terapia(id, ordenPrevia, esAfiliado, numSesiones, tipoTerapia, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public ProcedimientoEsp registrarProcedimientoEsp(String esAfiliado, String ordenPrevia, String conocimiento, String equipo, long idIPS, int capacidad, 
			String horaInicio, String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlProcedimientoEsp.adicionarProcedimientoEsp(pm, id, ordenPrevia, esAfiliado, conocimiento, equipo, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
			tx.commit();

			log.trace ("Inserci�n de procedimiento especial afiliada a la ips: " + idIPS + ": " + tuplasInsertadas + " tuplas insertadas");

			return new ProcedimientoEsp(id, esAfiliado, ordenPrevia, conocimiento, equipo, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	public Hospitalizacion registarHospitalizacion(String ordenPrevia, String esAfiliado, int numVisitas, long idIPS, int capacidad, 
			String horaInicio, String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlHospitalizacion.adicionarHospitalizacion(pm, id, ordenPrevia, esAfiliado, numVisitas, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
			tx.commit();

			log.trace ("Inserci�n de hospitalizacion afiliada a la ips: " + idIPS + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Hospitalizacion(id, ordenPrevia, esAfiliado, numVisitas, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	/* ****************************************************************
	 * 			M�todos para manejar las CITAS
	 *****************************************************************/

	public Cita registrarCita(String horaInicio, String horaFin, long idMedico, long idConsulta, long idTerapia, long idProcedimientoEsp, long idHospitalizacion, long idUsuarioIPS)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCita.adicionarCita(pm, id, horaInicio, horaFin, idMedico, idConsulta, idTerapia, idProcedimientoEsp, idHospitalizacion, idUsuarioIPS);
			tx.commit();

			log.trace ("Inserci�n de cita asociada al medico: " + idMedico + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cita(id, horaInicio, horaFin, idMedico, idConsulta, idTerapia, idProcedimientoEsp, idHospitalizacion, idUsuarioIPS);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 	 M�todos para manejar las RECETAS (cumplimientos de las citas)
	 *****************************************************************/


	public Receta registrarReceta(String diagnostico, String medicamentos, long idCita)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlReceta.adicionarReceta(pm, id, diagnostico, medicamentos, idCita);
			tx.commit();

			log.trace ("Inserci�n de receta asociada a la cita: " + idCita + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Receta(diagnostico, medicamentos, id, idCita);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 	 M�todos para manejar las Ordenes de los SS
	 *****************************************************************/


	public OrdenServicio registrarOrdenServicio(long idReceta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		long id = nextval ();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();


			long necServicio = sqlOrdenServicio.retornarOrdenServicio(pm, idReceta);

			if(necServicio == 0)  {

				long tuplasInsertadas = sqlOrdenServicio.adicionarOrdenServicio(pm, id, idReceta);
				tx.commit();
				log.trace ("Inserci�n de orden servicio asociado a la receta: " + idReceta + ": " + tuplasInsertadas + " tuplas insertadas");
				return new OrdenServicio(id, idReceta);

			} else {

				tx.commit();
				return new OrdenServicio(necServicio, idReceta);

			}

		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public OrdenConsulta registrarOrdenConsulta(long idOrdenSS, long idConsulta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlOrdenConsulta.adicionarOrdenConsulta(pm, idOrdenSS, idConsulta);
			tx.commit();

			log.trace ("Inserci�n de orden de ss asociado a la consulta: " + idConsulta + ": " + tuplasInsertadas + " tuplas insertadas");

			return new OrdenConsulta(idOrdenSS, idConsulta);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	public OrdenTerapia registrarOrdenTerapia(long idOrdenSS, long idTerapia)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlOrdenTerapia.adicionarOrdenTerapia(pm, idOrdenSS, idTerapia);
			tx.commit();

			log.trace ("Inserci�n de orden de ss asociado a la terapia: " + idTerapia + ": " + tuplasInsertadas + " tuplas insertadas");

			return new OrdenTerapia(idOrdenSS, idTerapia);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	public OrdenProcedimientoEsp registrarOrdenProcedimientoEsp(long idOrdenSS, long idProcedimiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlOrdenProcedimiento.adicionarOrdenProcedimientoEsp(pm, idOrdenSS, idProcedimiento);
			tx.commit();

			log.trace ("Inserci�n de orden de ss asociado al procedimiento especial: " + idProcedimiento + ": " + tuplasInsertadas + " tuplas insertadas");

			return new OrdenProcedimientoEsp(idOrdenSS, idProcedimiento);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public OrdenHospitalizacion registrarOrdenHospitalizacion(long idOrdenSS, long idHospitalizacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlOrdenHospitalizacion.adicionarOrdenHospitalizacion(pm, idOrdenSS, idHospitalizacion);
			tx.commit();

			log.trace ("Inserci�n de orden de ss asociado a la hospitalizacion: " + idHospitalizacion + ": " + tuplasInsertadas + " tuplas insertadas");

			return new OrdenHospitalizacion(idOrdenSS, idHospitalizacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	//-------------------------------------------------------------------------------
	//MOSTRAR LA CANTIDAD DE SERVICIOS PRESTADOS POR CADA IPS DURANTE UN PERIODO DE TIEMPO
	//Y EN EL AÑO CORRIDO
	//-------------------------------------------------------------------------------
	/**
	 * mostrar la cantidad de servicios prestados por cada IPS durante un periodo de tiempo 
	 * y en el año corrido
	 */
	public void serviciosPorIPS(String fechaInicio, String fechaFin) {
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			List<Object []> respuesta = new LinkedList <Object []> ();
			List<Object[]> tuplas = sqlIPS.darServicosPrestadosPorIPS(pm, fechaInicio, fechaFin);
			tx.begin();

			//metodo

			tx.commit();


		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	//-------------------------------------------------------------------------------
	//MOSTRAR LOS 20 SERVICIOS MÁS SOLICITADOS.
	//-------------------------------------------------------------------------------
	/**
	 *Mostrar los servicios que fueron más solicitados en un período de tiempo dado 
	 */
	public void serviciosMasSolicitados(String fechaInicio, String fechaFin) {
		PersistenceManager pm = pmf.getPersistenceManager();
		String[] masSolicitados = new String[20]; 
		Transaction tx=pm.currentTransaction();
		try
		{
			List<Object []> respuesta = new LinkedList <Object []> ();
			List<Object[]> tuplas = sqlCita.darCitasMasPedidas(pm, fechaInicio, fechaFin);
			tx.begin();
			//metodo

			tx.commit();


		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	//-------------------------------------------------------------------------------
	//MOSTRAR EL ÍNDICE DE USO DE CADA UNO DE LOS SERVICIOS PROVISTOS
	//-------------------------------------------------------------------------------

	/**
	 * Mostrar el indice de uso de cada uno de los servicios provistos
	 */
	public void indiceUso() {
		PersistenceManager pm = pmf.getPersistenceManager();
		ArrayList<Integer> masSolicitados = new ArrayList<>(); 
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			//metodo

			tx.commit();

			for (Integer integer : masSolicitados) {
				log.trace ("servicio" + integer);
			}


		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	//-------------------------------------------------------------------------------
	//MOSTRAR LOS SERVICIOS QUE CUMPLEN CON CIERTA CARACTERÍSTICA
	//-------------------------------------------------------------------------------

	/**
	 * Mostrar toda la información del servicio. Las características son, por ejemplo,
	 * fecha de prestación en un rango de tiempo, registrados por cierto recepcionista,
	 * son de cierto tipo, han sido solicitados más de X veces en un rango de fechas.
	 *  Consulte cualquier combinación de características en la consulta
	 */

	//-------------------------------------------------------------------------------
	//MOSTRAR LA UTILIZACIÓN DE SERVICIOS DE EPSANDES POR UN AFILIADO DADO, EN UN RANGO DE FECHAS
	//INDICADO.
	//-------------------------------------------------------------------------------

	/**
	 * Recuerde que un afiliado puede solicitar servicios de salud cuantas veces lo requiera. 
	 * Considere tanto la reserva como el uso efectivo de los servicios de salud.
	 */

	/**--------------------------------------------------------------------------------
	----------------------------------Iteracion 2 -------------------------------------
	----------------------------------------------------------------------------------*/


	public List<Consulta> darConsultas() {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {

			tx.begin();
			List<Consulta> lista =  sqlConsulta.darConsultas(pm);
			tx.commit();

			return lista;

		} catch (Exception e) {
			// TODO: handle exception
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
	}

	public List<Terapia> darTerapias() {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {

			tx.begin();
			List<Terapia> lista =  sqlTerapia.darTerapias(pm);
			tx.commit();

			return lista;

		} catch (Exception e) {
			// TODO: handle exception
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
	}

	public List<ProcedimientoEsp> darProcedimientosEsp() {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {

			tx.begin();
			List<ProcedimientoEsp> lista =  sqlProcedimientoEsp.darProcedimientoEsps(pm);
			tx.commit();

			return lista;

		} catch (Exception e) {
			// TODO: handle exception
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
	}

	public List<Hospitalizacion> darHospitalizacion() {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try {

			tx.begin();
			List<Hospitalizacion> lista =  sqlHospitalizacion.darHospitalizacions(pm);
			tx.commit();

			return lista;

		} catch (Exception e) {
			// TODO: handle exception
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
	}




	// ------------------------------------ITERACION 2 -------------------------------------------------
	
	public CampaniaPrevencion registrarCampaniaPrevencion(String localizacion, String fechaInicio, String fechaFin, long idEPS)
	{
		
		long id = nextval ();
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaPrevencion.adicionarCampaniaPrevencion(pm, id, localizacion, fechaInicio, fechaFin, idEPS);
			tx.commit();

			log.trace ("Inserci�n de campania de prevencion con localizacion en: " + localizacion + ": " + tuplasInsertadas + " tuplas insertadas");

			return new CampaniaPrevencion(id, localizacion, fechaInicio, fechaFin, idEPS);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	
	
	public CampaniaConsulta registrarCampaniaConsulta(long idCampania, long idConsulta, String fechaInicio,
			String fechaFin, String disponible)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaConsulta.adicionarCampaniaConsulta(pm, idCampania, idConsulta, fechaInicio, fechaFin, disponible);
			tx.commit();

			log.trace ("Inserci�n de consulta asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas insertadas");

			return new CampaniaConsulta(idCampania, idConsulta, fechaInicio, fechaFin, disponible);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	
	public CampaniaTerapia registrarCampaniaTerapia(long idCampania, long idTerapia, String fechaInicio,
			String fechaFin, String disponible)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaTerapia.adicionarCampaniaTerapia(pm, idCampania, idTerapia, fechaInicio, fechaFin, disponible);
			tx.commit();

			log.trace ("Inserci�n de terapia asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas insertadas");

			return new CampaniaTerapia(idCampania, idTerapia, fechaInicio, fechaFin, disponible);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	
	
	public CampaniaProcedimientoEsp registrarCampaniaProcedimiento(long idCampania, long idProcedimiento, String fechaInicio,
			String fechaFin, String disponible)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaProcedimiento.adicionarCampaniaProcedimientoEsp(pm, idCampania, idProcedimiento, fechaInicio, fechaFin, disponible);
			tx.commit();

			log.trace ("Inserci�n de procedimiento especial asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas insertadas");

			return new CampaniaProcedimientoEsp(idCampania, idProcedimiento, fechaInicio, fechaFin, disponible);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	public CampaniaHospitalizacion registrarCampaniaHospitalizacion(long idCampania, long idHospitalizacion, String fechaInicio,
			String fechaFin, String disponible)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaHospitalizacion.adicionarCampaniaHospitalizacion(pm, idCampania, idHospitalizacion, fechaInicio, fechaFin, disponible);
			tx.commit();

			log.trace ("Inserci�n de hospitalizacion asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas insertadas");

			return new CampaniaHospitalizacion(idCampania, idHospitalizacion, fechaInicio, fechaFin, disponible);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	
	
	public void eliminarCampaniaConsulta(long idCampania, long idConsulta, int eliminar)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaConsulta.eliminarCampaniaConsultaPorId(pm, idCampania, idConsulta, eliminar);
			tx.commit();

			log.trace ("Eliminacion de consulta asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas eliminadas");
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	
	public void eliminarCampaniaTerapia(long idCampania, long idTerapia, int eliminar)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaTerapia.eliminarCampaniaTerapiaPorId(pm, idCampania, idTerapia, eliminar);
			tx.commit();

			log.trace ("Eliminacion de terapia asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas eliminadas");
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	public void eliminarCampaniaProcedimiento(long idCampania, long idProcedimiento, int eliminar)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaProcedimiento.eliminarCampaniaProcedimientoPorId(pm, idCampania, idProcedimiento, eliminar);
			tx.commit();

			log.trace ("Eliminacion de terapia asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas eliminadas");
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	public void eliminarCampaniaHospitalizacion(long idCampania, long idHospitalizacion, int eliminar)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCampaniaHospitalizacion.eliminarCampaniaHospitalizacionPorId(pm, idCampania, idHospitalizacion, eliminar);
			tx.commit();

			log.trace ("Eliminacion de hospitalizacion asociada a la campania: " + idCampania + ": " + tuplasInsertadas + " tuplas eliminadas");
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	
	public void cambiarReservaConsulta(long idConsulta)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
		
			sqlConsulta.cambiarReservaConsulta(pm, idConsulta);
			
			tx.commit();
			
			log.trace ("Cambio de reservado en la consulta: " + idConsulta);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	
	public void cambiarReservaTerapia(long idTerapia)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
		
			sqlTerapia.cambiarReservaTerapia(pm, idTerapia);
			
			tx.commit();
			
			log.trace ("Cambio de reservado en la terapia: " + idTerapia);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	public void cambiarReservaProcedimiento(long idProcedimiento)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
		
			sqlProcedimientoEsp.cambiarReservaProcedimiento(pm, idProcedimiento);
			
			tx.commit();
			
			log.trace ("Cambio de reservado en la procedimiento: " + idProcedimiento);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	
	public void cambiarReservaHospitalizacion(long idHospitalizacion)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
		
			sqlHospitalizacion.cambiarReservaHospitalizacion(pm, idHospitalizacion);
			
			tx.commit();
			
			log.trace ("Cambio de reservado en la hospitalizacion: " + idHospitalizacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	
	public void reaperturaCampaniaConsulta(long idCampania, long idConsulta)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
		
			sqlCampaniaConsulta.reapertura(pm, idCampania, idConsulta);
			
			tx.commit();
			
			log.trace ("Cambio reapertura: " + idConsulta);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	public void reaperturaCampaniaTerapia(long idCampania, long idTerapia)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
		
			sqlCampaniaTerapia.reapertura(pm, idCampania, idTerapia);
			
			tx.commit();
			
			log.trace ("Cambio reapertura: " + idTerapia);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle espec�fico del problema encontrado
	 * @param e - La excepci�n que ocurrio
	 * @return El mensaje de la excepci�n JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}


	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarEPSAndes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarEPSAndes (pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}


}
