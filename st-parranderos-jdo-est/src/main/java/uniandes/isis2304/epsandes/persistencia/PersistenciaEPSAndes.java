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
	/* ****************************************************************
	 * 			Métodos para manejar los TIPOS DE BEBIDA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla TipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	/**
	public TipoBebida adicionarTipoBebida(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idTipoBebida = nextval ();
            long tuplasInsertadas = sqlTipoBebida.adicionarTipoBebida(pm, idTipoBebida, nombre);
            tx.commit();
            
            log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new TipoBebida (idTipoBebida, nombre);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el nombre del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoBebidaPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlTipoBebida.eliminarTipoBebidaPorNombre(pm, nombre);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
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
	 * Método que consulta todas las tuplas en la tabla TipoBebida
	 * @return La lista de objetos TipoBebida, construidos con base en las tuplas de la tabla TIPOBEBIDA
	 */
	public List<TipoBebida> darTiposBebida ()
	{
		return sqlTipoBebida.darTiposBebida (pmf.getPersistenceManager());
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla TipoBebida que tienen el nombre dado
	 * @param nombre - El nombre del tipo de bebida
	 * @return La lista de objetos TipoBebida, construidos con base en las tuplas de la tabla TIPOBEBIDA
	 */
	public List<TipoBebida> darTipoBebidaPorNombre (String nombre)
	{
		return sqlTipoBebida.darTiposBebidaPorNombre (pmf.getPersistenceManager(), nombre);
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla TipoBebida con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public TipoBebida darTipoBebidaPorId (long idTipoBebida)
	{
		return sqlTipoBebida.darTipoBebidaPorId (pmf.getPersistenceManager(), idTipoBebida);
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar las BEBIDAS
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida (Debe existir en la tabla TipoBebida)
	 * @param gradoAlcohol - El grado de alcohol de la bebida (mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Bebida adicionarBebida(String nombre, long idTipoBebida, int gradoAlcohol) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();            
            long idBebida = nextval ();
            long tuplasInsertadas = sqlBebida.adicionarBebida(pm, idBebida, nombre, idTipoBebida, gradoAlcohol);
            tx.commit();
            
            log.trace ("Inserción bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            return new Bebida (idBebida,nombre, idTipoBebida, gradoAlcohol);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el nombre de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombreBebida - El nombre de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebidaPorNombre (String nombreBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBebida.eliminarBebidaPorNombre(pm, nombreBebida);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebidaPorId (long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBebida.eliminarBebidaPorId (pm, idBebida);
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
	 * Método que consulta todas las tuplas en la tabla Bebida que tienen el nombre dado
	 * @param nombreBebida - El nombre de la bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Bebida> darBebidasPorNombre (String nombreBebida)
	{
		return sqlBebida.darBebidasPorNombre (pmf.getPersistenceManager(), nombreBebida);
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Bebida> darBebidas ()
	{
		return sqlBebida.darBebidas (pmf.getPersistenceManager());
	}
 
	/**
	 * Método que elimina, de manera transaccional, las bebidas que no son referenciadas en la tabla SIRVEN de Parranderos
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebidasNoServidas ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBebida.eliminarBebidasNoServidas(pm);
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

	/* ****************************************************************
	 * 			Métodos para manejar los BEBEDORES
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Consulta adicionarBebedor(String nombre, String ciudad, String presupuesto) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idBebedor = nextval ();
            long tuplasInsertadas = sqlBebedor.adicionarBebedor(pmf.getPersistenceManager(), idBebedor, nombre, ciudad, presupuesto);
            tx.commit();

            log.trace ("Inserción de bebedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Consulta (idBebedor, nombre, ciudad, presupuesto);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el nombre del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebedorPorNombre(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBebedor.eliminarBebedorPorNombre (pm, nombre);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebedorPorId (long idBebedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBebedor.eliminarBebedorPorId (pm, idBebedor);
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
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el nombre dado
	 * @param nombreBebedor - El nombre del bebedor
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<Consulta> darBebedoresPorNombre (String nombreBebedor) 
	{
		return sqlBebedor.darBebedoresPorNombre (pmf.getPersistenceManager(), nombreBebedor);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public Consulta darBebedorPorId (long idBebedor) 
	{
		return (Consulta) sqlBebedor.darBebedorPorId (pmf.getPersistenceManager(), idBebedor);
	}

	/**
	 * Método que consulta TODA LA INFORMACIÓN DE UN BEBEDOR con el identificador dado. Incluye la información básica del bebedor,
	 * las visitas realizadas y las bebidas que le gustan.
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en las tuplas de la tablas BEBEDOR, VISITAN, BARES, GUSTAN, BEBIDAS y TIPOBEBIDA,
	 * relacionadas con el identificador de bebedor dado
	 */
	public Consulta darBebedorCompleto (long idBebedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Consulta consulta = (Consulta) sqlBebedor.darBebedorPorId (pm, idBebedor);
		consulta.setVisitasRealizadas(armarVisitasBebedor (sqlBebedor.darVisitasRealizadas (pm, idBebedor)));
		consulta.setBebidasQueLeGustan(armarGustanBebedor (sqlBebedor.darBebidasQueLeGustan (pm, idBebedor)));
		return consulta;
	}

	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<Consulta> darBebedores ()
	{
		return sqlBebedor.darBebedores (pmf.getPersistenceManager());
	}
 
	/**
	 * Método que consulta los bebedores y el número de visitas que ha realizado
	 * @return La lista de parejas de objetos, construidos con base en las tuplas de la tabla BEBEDOR y VISITAN. 
	 * El primer elemento de la pareja es un bebedor; 
	 * el segundo elemento es el número de visitas de ese bebedor (0 en el caso que no haya realizado visitas)
	 */
	public List<Object []> darBebedoresYNumVisitasRealizadas ()
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlBebedor.darBebedoresYNumVisitasRealizadas (pmf.getPersistenceManager());
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			long idBebedor = ((BigDecimal) datos [0]).longValue ();
			String nombreBebedor = (String) datos [1];
			String ciudadBebedor = (String) datos [2];
			String presupuesto = (String) datos [3];
			int numBares = ((BigDecimal) datos [4]).intValue ();

			Object [] resp = new Object [2];
			resp [0] = new Consulta (idBebedor, nombreBebedor, ciudadBebedor, presupuesto);
			resp [1] = numBares;	
			
			respuesta.add(resp);
        }

		return respuesta;
	}
 
	/**
	 * Método que consulta CUÁNTOS BEBEDORES DE UNA CIUDAD VISITAN BARES
	 * @param ciudad - La ciudad que se quiere consultar
	 * @return El número de bebedores de la ciudad dada que son referenciados en VISITAN
	 */
	public long darCantidadBebedoresCiudadVisitanBares (String ciudad)
	{
		return sqlBebedor.darCantidadBebedoresCiudadVisitanBares (pmf.getPersistenceManager(), ciudad);
	}
	
	/**
	 * Método que actualiza, de manera transaccional, la ciudad de un  BEBEDOR
	 * @param idBebedor - El identificador del bebedor
	 * @param ciudad - La nueva ciudad del bebedor
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long cambiarCiudadBebedor (long idBebedor, String ciudad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBebedor.cambiarCiudadBebedor (pm, idBebedor, ciudad);
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
	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
	 * Si el bebedor está referenciado en alguna otra relación, no se borra ni el bebedor NI las visitas
	 * @param idBebedor - El identificador del bebedor
	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
	 */
	public long []  eliminarBebedorYVisitas_v1 (long idBebedor)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlBebedor.eliminarBebedorYVisitas_v1 (pm, idBebedor);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1};
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
	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
	 * Si el bebedor está referenciado en alguna otra relación, no se puede borrar, SIN EMBARGO SÍ SE BORRAN TODAS SUS VISITAS
	 * @param idBebedor - El identificador del bebedor
	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
	 */
	public long [] eliminarBebedorYVisitas_v2 (long idBebedor)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long visitasEliminadas = eliminarVisitanPorIdBebedor(idBebedor);
            long bebedorEliminado = eliminarBebedorPorId (idBebedor);
            tx.commit();
            return new long [] {bebedorEliminado, visitasEliminadas};
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long [] {-1, -1};
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
	 * Método privado para generar las información completa de las visitas realizadas por un bebedor: 
	 * La información básica del bar visitado, la fecha y el horario, en el formato esperado por los objetos BEBEDOR
	 * @param tuplas - Una lista de arreglos de 7 objetos, con la información del bar y de la visita realizada, en el siguiente orden:
	 *   bar.id, bar.nombre, bar.ciudad, bar.presupuesto, bar.cantsedes, vis.fechavisita, vis.horario
	 * @return Una lista de arreglos de 3 objetos. El primero es un objeto BAR, el segundo corresponde a la fecha de la visita y
	 * el tercero corresponde al horaario de la visita
	 */
	private List<Object []> armarVisitasBebedor (List<Object []> tuplas)
	{
		List<Object []> visitas = new LinkedList <Object []> ();
		for (Object [] tupla : tuplas)
		{
			long idBar = ((BigDecimal) tupla [0]).longValue ();
			String nombreBar = (String) tupla [1];
			String ciudadBar = (String) tupla [2];
			String presupuestoBar = (String) tupla [3];
			int sedesBar = ((BigDecimal) tupla [4]).intValue ();
			Timestamp fechaVisita = (Timestamp) tupla [5];
			String horarioVisita = (String) tupla [6];
			
			Object [] visita = new Object [3];
			visita [0] = new AdministradorEPS (idBar, nombreBar, ciudadBar, presupuestoBar, sedesBar);
			visita [1] = fechaVisita;
			visita [2] = horarioVisita;

			visitas.add (visita);
		}
		return visitas;
	}
	
	/**
	 * Método privado para generar las información completa de las bebidas que le gustan a un bebedor: 
	 * La información básica de la bebida, especificando también el nombre de la bebida, en el formato esperado por los objetos BEBEDOR
	 * @param tuplas - Una lista de arreglos de 5 objetos, con la información de la bebida y del tipo de bebida, en el siguiente orden:
	 * 	 beb.id, beb.nombre, beb.idtipobebida, beb.gradoalcohol, tipobebida.nombre
	 * @return Una lista de arreglos de 2 objetos. El primero es un objeto BEBIDA, el segundo corresponde al nombre del tipo de bebida
	 */
	private List<Object []> armarGustanBebedor (List<Object []> tuplas)
	{
		List<Object []> gustan = new LinkedList <Object []> ();
		for (Object [] tupla : tuplas)
		{			
			long idBebida = ((BigDecimal) tupla [0]).longValue ();
			String nombreBebida = (String) tupla [1];
			long idTipoBebida = ((BigDecimal) tupla [2]).longValue ();
			int gradoAlcohol = ((BigDecimal) tupla [3]).intValue ();
			String nombreTipo = (String) tupla [4];

			Object [] gusta = new Object [2];
			gusta [0] = new Bebida (idBebida, nombreBebida, idTipoBebida, gradoAlcohol);
			gusta [1] = nombreTipo;	
			
			gustan.add(gusta);
		}
		return gustan;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los BARES
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
	public AdministradorEPS adicionarBar(String nombre, String ciudad, String presupuesto, int sedes) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idBar = nextval ();
            long tuplasInsertadas = sqlBar.adicionarBar(pm, idBar, nombre, ciudad, presupuesto, sedes);
            tx.commit();

            log.trace ("Inserción de Bar: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new AdministradorEPS (idBar, nombre, ciudad, presupuesto, sedes);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BAR, dado el nombre del bar
	 * Adiciona entradas al log de la aplicación
	 * @param nombreBar - El nombre del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBarPorNombre (String nombreBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBar.eliminarBaresPorNombre(pm, nombreBar);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BAR, dado el identificador del bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBarPorId (long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBar.eliminarBarPorId (pm, idBar);
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
	 * Método que consulta todas las tuplas en la tabla BAR
	 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
	 */
	public List<AdministradorEPS> darBares ()
	{
		return sqlBar.darBares (pmf.getPersistenceManager());
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla BAR que tienen el nombre dado
	 * @param nombreBar - El nombre del bar
	 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
	 */
	public List<AdministradorEPS> darBaresPorNombre (String nombreBar)
	{
		return sqlBar.darBaresPorNombre (pmf.getPersistenceManager(), nombreBar);
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla BAR que tienen el identificador dado
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR, construido con base en la tuplas de la tabla BAR, que tiene el identificador dado
	 */
	public AdministradorEPS darBarPorId (long idBar)
	{
		return sqlBar.darBarPorId (pmf.getPersistenceManager(), idBar);
	}
 
	/**
	 * Método que actualiza, de manera transaccional, aumentando en 1 el número de sedes de todos los bares de una ciudad
	 * @param ciudad - La ciudad que se quiere modificar
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long aumentarSedesBaresCiudad (String ciudad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlBar.aumentarSedesBaresCiudad(pm, ciudad);
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
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación GUSTAN
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla GUSTAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @return Un objeto GUSTAN con la información dada. Null si ocurre alguna Excepción
	 */
	public Gustan adicionarGustan(long idBebedor, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlGustan.adicionarGustan (pm, idBebedor, idBebida);
            tx.commit();

            log.trace ("Inserción de gustan: [" + idBebedor + ", " + idBebida + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Gustan (idBebedor, idBebida);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarGustan(long idBebedor, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlGustan.eliminarGustan(pm, idBebedor, idBebida);           
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
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<Gustan> darGustan ()
	{
		return sqlGustan.darGustan (pmf.getPersistenceManager());
	}
 
 
	/* ****************************************************************
	 * 			Métodos para manejar la relación SIRVEN
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla SIRVEN
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar - Debe haber un bar con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @param horario - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto SIRVEN con la información dada. Null si ocurre alguna Excepción
	 */
	public Sirven adicionarSirven (long idBar, long idBebida, String horario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlSirven.adicionarSirven (pmf.getPersistenceManager(), idBar, idBebida, horario);
    		tx.commit();

            log.trace ("Inserción de gustan: [" + idBar + ", " + idBebida + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Sirven (idBar, idBebida, horario);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla SIRVEN, dados los identificadores de bar y bebida
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSirven (long idBar, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
	        Transaction tx=pm.currentTransaction();
	        try
	        {
	            tx.begin();
	            long resp = sqlSirven.eliminarSirven (pm, idBar, idBebida);	            
	            tx.commit();

	            return resp;
	        }
	        catch (Exception e)
	        {
//	        	e.printStackTrace();
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
	 * Método que consulta todas las tuplas en la tabla SIRVEN
	 * @return La lista de objetos SIRVEN, construidos con base en las tuplas de la tabla SIRVEN
	 */
	public List<Sirven> darSirven ()
	{
		return sqlSirven.darSirven (pmf.getPersistenceManager());
	}
 
	/**
	 * Método que encuentra el identificador de los bares y cuántas bebidas sirve cada uno de ellos. Si una bebida se sirve en diferentes horarios,
	 * cuenta múltiples veces
	 * @return Una lista de arreglos de 2 números. El primero corresponde al identificador del bar, el segundo corresponde al nombre del tipo de bebida
	 */
	public List<long []> darBaresYCantidadBebidasSirven ()
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlSirven.darBaresYCantidadBebidasSirven (pmf.getPersistenceManager());
        for ( Object [] tupla : tuplas)
        {
			long [] datosResp = new long [2];
			
			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
        }
        return resp;
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar la relación VISITAN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla VISITAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBar - El identificador del bar - Debe haber un bar con ese identificador
	 * @param fecha - La fecha en que se realizó la visita
	 * @param horario - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto VISITAN con la información dada. Null si ocurre alguna Excepción
	 */	
	public Visitan adicionarVisitan (long idBebedor, long idBar, Timestamp fecha, String horario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlVisitan.adicionarVisitan(pm, idBebedor, idBar, fecha, horario);
            tx.commit();

            log.trace ("Inserción de gustan: [" + idBebedor + ", " + idBar + "]. " + tuplasInsertadas + " tuplas insertadas");

            return new Visitan (idBebedor, idBar, fecha, horario);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados los identificadores de bebedor y bar
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitan (long idBebedor, long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVisitan.eliminarVisitan(pm, idBebedor, idBar);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados el identificador del bebedor
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitanPorIdBebedor (long idBebedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long visitasEliminadas = sqlVisitan.eliminarVisitanPorIdBebedor (pm, idBebedor);
            tx.commit();

            return visitasEliminadas;
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados el identificador del bar
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitanPorIdBar (long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long visitasEliminadas = sqlVisitan.eliminarVisitanPorIdBar (pm, idBar);
            tx.commit();

            return visitasEliminadas;
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
	 * Método que consulta todas las tuplas en la tabla VISITAN
	 * @return La lista de objetos VISITAN, construidos con base en las tuplas de la tabla VISITAN
	 */
	public List<Visitan> darVisitan ()
	{
		return sqlVisitan.darVisitan (pmf.getPersistenceManager());
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
