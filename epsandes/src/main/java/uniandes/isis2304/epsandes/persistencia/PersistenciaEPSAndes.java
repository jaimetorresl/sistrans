package uniandes.isis2304.epsandes.persistencia;

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

import uniandes.isis2304.epsandes.negocio.EPS;
import uniandes.isis2304.epsandes.negocio.EPSAndes;
import uniandes.isis2304.epsandes.negocio.IPS;
import uniandes.isis2304.epsandes.negocio.Medico;
import uniandes.isis2304.epsandes.negocio.MedicoSS;
import uniandes.isis2304.epsandes.negocio.PrestacionServicio;
import uniandes.isis2304.epsandes.negocio.ServicioSalud;
import uniandes.isis2304.epsandes.negocio.UsuarioEPS;

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
		
	
	/* ****************************************************************
	 * 			M�todos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patr�n SINGLETON
	 */
	private PersistenciaEPSAndes() {
	
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
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
		
		sqlUtil = new SQLUtil(this);
		sqlEPS = new SQLEPS(this);
		sqlIPS = new SQLIPS(this);
		sqlUsuarioEPS = new SQLUsuarioEPS(this);
		sqlMedico = new SQLMedico(this);
		sqlPaciente = new SQLPaciente(this);
		sqlEPS = new SQLEPS(this);
		sqlRecepcionistaIPS = new SQLRecepcionistaIPS(this);
		sqlGerenteEPS = new SQLGerenteEPS(this);
		sqlResultado = new SQLReceta(this);
		sqlPrestacion = new SQLPrestacionServicio(this);
		sqlMedicoSS = new SQLMedicoSS(this);
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

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaAdministradorEPS ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaServicioSalud ()
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
	public String darTablaMedicoConsulta ()
	{
		return tablas.get (7);
	}

	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaAfiliado ()
	{
		return tablas.get (9);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaPaciente ()
	{
		return tablas.get (10);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaRecepcionistaIPS ()
	{
		return tablas.get (11);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaGerenteEPS ()
	{
		return tablas.get (12);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaResultado ()
	{
		return tablas.get (13);
	}
	
	public String darTablaPrestacionServicio() {
		
		return tablas.get(14);
		
	}
	
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaMedicoSS()
	{
		return tablas.get (15);
	}
	
	
	public String darTablaIPSTipoSS() {
		
		return tablas.get(16);
		
	}


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
	
	
	public IPS registrarIPS(String nombre, String tipo, String localizacion, long idEPS)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();
		
		long id = nextval ();
		
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlIPS.adicionarIPS(pm, id, nombre, tipo, localizacion, idEPS);
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
	
	
	public UsuarioEPS registrarUsuarioEPS(String nombre, int rol, long idEPS, String correo)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();
		
		long id = nextval ();
		
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlUsuarioEPS.adicionarUsuarioEPS(pm, id, nombre, tipo, localizacion, idEPS);
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
	
	
	/* ****************************************************************
	 * 			M�todos para manejar los MEDICOS
	 *****************************************************************/
	
	
	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla Medico
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public Medico registrarMedico(PersistenceManager pm, long id, String especialidad,int numRegMedico, String tipo, String nombre)
	{
	    pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idMedico = nextval ();
            long tuplasInsertadas = sqlMedico.adicionarMedico(pm, idMedico, especialidad, numRegMedico, tipo, nombre);
            tx.commit();
            
            log.trace ("Inserci�n de medico: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Medico(idMedico, especialidad, nombre, tipo, numRegMedico);
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
	
	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla Servicio Salud
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public ServicioSalud registrarServicioSalud(PersistenceManager pm, long id, String horaInicio, String horaFinal,
			String codigoTipoSS, long idPaciente, long idIPS)
	{
		pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idServicio = nextval ();
            long tuplasInsertadas = sqlServicioSalud.adicionarServicioSalud(pm, id, horaInicio, horaFinal, codigoTipoSS, idPaciente, idIPS);
            
            tx.commit();
            
            log.trace ("Inserci�n de servicio de salud: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new ServicioSalud(id, horaInicio, horaFinal, codigoTipoSS, idPaciente, idIPS);
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
	
	
	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla MedicoSS
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public MedicoSS registrarOrdenServicioSalud(PersistenceManager pm, long idServicio, long idMedico)
	{
		pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlMedicoSS.adicionarMedicoSS(pm, idMedico, idServicio);
            tx.commit();
            
            log.trace ("Inserci�n de servicio de salud: " + idServicio + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new MedicoSS(idMedico, idServicio);
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
	
	
	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla PrestacionServicio
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public PrestacionServicio registrarPrestacionServicioSalud(PersistenceManager pm, long idServicio, long idResultado)
	{
		pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlPrestacion.adicionarPrestacion(pm, idServicio, idResultado);
            tx.commit();
            
            log.trace ("Inserci�n de prestacion de ss: " + idServicio + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new PrestacionServicio(idResultado, idServicio);
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
