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
import java.sql.Timestamp;
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

import uniandes.isis2304.epsandes.negocio.AdministradorEPS;
import uniandes.isis2304.epsandes.negocio.Consulta;
import uniandes.isis2304.epsandes.negocio.IPS;
import uniandes.isis2304.epsandes.negocio.Medico;
import uniandes.isis2304.epsandes.negocio.MedicoSS;
import uniandes.isis2304.epsandes.negocio.Paciente;
import uniandes.isis2304.epsandes.negocio.ServicioSalud;

/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaEPSAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaEPSAndes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaEPSAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaESPAndes
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla AdministradorEPS de la base de datos
	 */
	private SQLAdministradorEPS sqlAdministradorEPS;
	
	/**
	 * Atributo para el acceso a la tabla EPS de la base de datos
	 */
	private SQLEPS sqlEPS;
	
	/**
	 * Atributo para el acceso a la tabla GerenteEPS de la base de datos
	 */
	private SQLGerenteEPS sqlGerenteEPS;
	
	/**
	 * Atributo para el acceso a la tabla IPS de la base de datos
	 */
	private SQLIPS sqlIPS;
	
	/**
	 * Atributo para el acceso a la tabla IPSMedico de la base de datos
	 */
	private SQLIPSMedico sqlIPSMedico;
	
	/**
	 * Atributo para el acceso a la tabla IPSTipoSS de la base de datos
	 */
	private SQLIPSTipoSS sqlIPSTiposSS;
	
	/**
	 * Atributo para el acceso a la tabla Medico de la base de datos
	 */
	private SQLMedico sqlMedico;
	
	/**
	 * Atributo para el acceso a la tabla MedicoSS de la base de datos
	 */
	private SQLMedicoSS sqlMedicoSS;
	
	
	/**
	 * Atributo para el acceso a la tabla sqlPaciente de la base de datos
	 */
	private SQLPaciente sqlPaciente;
	
	/**
	 * Atributo para el acceso a la tabla PrestacionServicio de la base de datos
	 */
	private SQLPrestacionServicio sqlPrestacionServicio;
	
	/**
	 * Atributo para el acceso a la tabla RecepcionistaIPS de la base de datos
	 */
	private SQLRecepcionistaIPS sqlRecepcionistaIPS;
	
	/**
	 * Atributo para el acceso a la tabla Resultado de la base de datos
	 */
	private SQLResultado sqlResultado;
	
	/**
	 * Atributo para el acceso a la tabla ServicioSalud de la base de datos
	 */
	private SQLServicioSalud sqlServicioSalud;
	
	
	/**
	 * Atributo para el acceso a la tabla TipoSS de la base de datos
	 */
	private SQLTipoSS sqlTipoSS;
	
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaEPSAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add ("ADMINISTRADOR_EPS");
		tablas.add ("EPS");
		tablas.add ("GERENTE_EPS");
		tablas.add ("IPS");
		tablas.add ("IPS_MEDICO");
		tablas.add ("IPS_TIPO_SS");
		tablas.add ("MEDICO");
		tablas.add ("MEDICO_SS");
		tablas.add ("PACIENTE");
		tablas.add ("PRESTACION_SERVICIO");
		tablas.add ("RECEPCIONISTA_IPS");
		tablas.add ("RESULTADO");
		tablas.add ("SERVICIO_SALUD");
		tablas.add ("TIPO_SS");
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
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
	 * @return Retorna el único objeto PersistenciaESPAndes existente - Patrón SINGLETON
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
	 * @return Retorna el único objeto PersistenciaESPAndes existente - Patrón SINGLETON
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
	 * Cierra la conexión con la base de datos
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
		sqlAdministradorEPS = new SQLAdministradorEPS(this);	
		sqlEPS = new SQLEPS(this);
		sqlGerenteEPS = new SQLGerenteEPS(this);
		sqlIPS = new SQLIPS(this);
		sqlIPSMedico = new SQLIPSMedico(this);
		sqlIPSTiposSS = new SQLIPSTipoSS(this);
		sqlMedico = new SQLMedico(this);
		sqlMedicoSS = new SQLMedicoSS(this);
		sqlPaciente = new SQLPaciente(this);
		sqlPrestacionServicio = new SQLPrestacionServicio(this);
		sqlRecepcionistaIPS = new SQLRecepcionistaIPS(this);
		sqlResultado = new SQLResultado(this);	
		sqlServicioSalud = new SQLServicioSalud(this);
		sqlTipoSS = new SQLTipoSS(this);
		sqlUtil = new SQLUtil(this);
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
	public String darTablaAdministradorEPS ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaEPS ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaGerenteEPS ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaIPS ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaIPSMedico ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaIPSTipoSS ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaMedico ()
	{
		return tablas.get (7);
	}

	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaMedicoSS ()
	{
		return tablas.get (8);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaPaciente ()
	{
		return tablas.get (9);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaPrestacionServicio ()
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
	public String darTablaResultado()
	{
		return tablas.get (12);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaServicioSalud ()
	{
		return tablas.get (13);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaTipoSS ()
	{
		return tablas.get (14);
	}

	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
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
	 * 			Métodos para manejar las IPS 
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BAR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del bar en la ciudad (Mayor que 0)
	 * @return El objeto Bar adicionado. null si ocurre alguna Excepción
	 */
	public IPS adicionarIPS( String nombre, String  localizacion, long idEPS) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlIPS.adicionarIPS(pm, id, nombre, localizacion, idEPS);
            tx.commit();

            log.trace ("Inserción de IPS: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new IPS(id, nombre, localizacion, idEPS);
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
	 * 			Métodos para manejar los afiliados
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BAR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del bar en la ciudad (Mayor que 0)
	 * @return El objeto Bar adicionado. null si ocurre alguna Excepción
	 */
	public Paciente adicionarAfiliado(String nombre, String estado, Long numDocumento, Integer tipoDocumento,
			  String fechaNacimiento, long idEPS, long idConsulta, boolean esAfiliado) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            if(esAfiliado && (numDocumento == null || tipoDocumento == null || fechaNacimiento == null)){
            	return null;
            }
            long tuplasInsertadas = sqlPaciente.adicionarPaciente(pm, id, nombre, estado, numDocumento,
            		                                             tipoDocumento, fechaNacimiento, idEPS, esAfiliado);
            tx.commit();

            log.trace ("Inserción de Afiliado: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Paciente(numDocumento, estado, nombre, fechaNacimiento, tipoDocumento);
            		
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


	/* **********************
	 * 			Métodos para manejar los MEDICOS
	 ***********************/
	
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Medico
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
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
            
            log.trace ("Inserción de medico: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
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
	
	
	
	/* **********************
	 * 			Métodos para manejar los SERVICIOS DE SALUD
	 ***********************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Servicio Salud
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
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
            
            log.trace ("Inserción de servicio de salud: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
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
	 * Método que inserta, de manera transaccional, una tupla en la tabla MedicoSS
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
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
            
            log.trace ("Inserción de servicio de salud: " + idServicio + ": " + tuplasInsertadas + " tuplas insertadas");
            
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
	 * Método que inserta, de manera transaccional, una tupla en la tabla PrestacionServicio
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
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
            
            log.trace ("Inserción de prestacion de ss: " + idServicio + ": " + tuplasInsertadas + " tuplas insertadas");
            
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

	public long eliminarTipoBebidaPorId (long idTipoBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlTipoBebida.eliminarTipoBebidaPorId(pm, idTipoBebida);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
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
	public long [] limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarParranderos (pm);
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
