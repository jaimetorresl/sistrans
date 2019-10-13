package uniandes.isis2304.epsandes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import uniandes.isis2304.epsandes.persistencia.PersistenciaEPSAndes;
//import uniandes.isis2304.epsandes.persistencia.PersistenciaEPSAndes;
import uniandes.isis2304.parranderos.negocio.Bebedor;


/**
 * Clase principal del negocio
 * Satisface todos los requerimientos funcionales del negocio
 *
 * @author Nicolas Potes Garcia - Jaime Andres Torres
 * 
 * Este codigo esta basado en el proyecto de Parranderos realizado por German Bravo
 */

public class EPSAndes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(EPSAndes.class.getName());

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaEPSAndes pp;


	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public EPSAndes ()
	{
		pp = PersistenciaEPSAndes.getInstance ();
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public EPSAndes (JsonObject tableConfig)
	{
		pp = PersistenciaEPSAndes.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexi�n con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}

	/* ****************************************************************
	 * 			M�todos para manejar la EPS
	 *****************************************************************/

	public EPS registrarEPS (String nombre)
	{	
		EPS eps;
		log.info ("Registrando eps: " + nombre);


			eps = pp.registrarEPS(nombre);
			log.info ("EPS registrada: " + nombre);


		return eps;

	}
	

	/* ****************************************************************
	 * 			M�todos para manejar las IPS
	 *****************************************************************/

	public IPS registrarIPS(long id, String nombre, String tipo, String localizacion, long idEPS) {



		IPS ips;
		log.info("Registrando IPS: " + nombre);

		ips = pp.registrarIPS(nombre, tipo, localizacion, idEPS);

		log.info ("IPS registrada: " + ips);

		return ips;

	}
	
	
	/* ****************************************************************
	 * 			M�todos para manejar los USUARIOS
	 *****************************************************************/
	public UsuarioEPS registrarUsuarioEPS (String nombre, String estado, long numDocumento, int tipoDocumento, String fechaNacimiento, long idEPS, String esAfiliado, String correo)
	{	
		UsuarioEPS usuarioEPS;
		
		log.info ("Adicionando usuarioEPS: " + nombre);
        UsuarioEPS usuarioEPS = pp.adicionarUsuarioEPS (nombre, presupuesto, ciudad);
        log.info ("Adicionando usuarioEPS: " + usuarioEPS);
        return usuarioEPS;

	}
	
	
	
	
	/* ****************************************************************
	 * 			M�todos para manejar los MEDICOS
	 *****************************************************************/
	
	/**
	 * Registra a la EPS de manera persistente un medico asociado a una o varias IPS
	 * Adiciona entradas al log de la aplicaci�n
	 */
	public Medico registrarMedico (String nombre, long id, String especialidad, int numRegMedico, String tipo, long idIPS)
	{	
		Medico medico;
		log.info ("Registrando medico: " + nombre + " con id: " + id);

		if(buscarMedicoPorId(id) == null) {

			medico = pp.registrarMedico (nombre, id, especialidad, numRegMedico, tipo, idIPS);		
			log.info ("Registrando medico: " + medico);


		} else {

			log.info("El medico no cumple con los requisitos para ser ingresado");

		}

		return medico;

	}


	/**
	 * Se busca un medico dentro de la lista de medicos existentes (por su id).
	 * @param id del medico a buscar
	 * @return medico o null por si no se encontro
	 */
	public Medico buscarMedicoPorId(long id) {

		Medico medicoId;
		log.info("Buscando medico");

		medicoId = pp.buscarMedicoPorId(id);
		log.info ("Medico encontrado: " + medicoId);

		return medicoId;

	}



	/* ****************************************************************
	 * 			M�todos para manejar los Afiliados (paciente)
	 *****************************************************************/

	public UsuarioIPS registrarAfiliado(long id, String nombre, String estado, long numDocumento, long tipoDocumento,
			String fechaNacimiento, long idEPS) {

		UsuarioIPS paciente;
		log.info("Registrando Paciente: " + nombre);

		paciente = pp.registrarPaciente(id, nombre, estado, numDocumento, tipoDocumento, fechaNacimiento, idEPS);

		log.info ("Paciente registrado: " + paciente);

		return paciente;


	}



	/* ****************************************************************
	 * 			M�todos para manejar los Servicios de salud
	 *****************************************************************/

	public ServicioSalud registrarServicioSalud(long id, int capacidad, String horarioAtencion,
			long idIPS, String nombre) {

			ServicioSalud servicio;
			log.info("Registrando Servicio de salud: " + nombre);

			servicio = pp.registrarServicioSalud(id, capacidad, horarioAtencion, idIPS, nombre);

			log.info ("Servicio de salud registrado: " + servicio);

			return servicio;

		}
	
	public void registrarOrdenServicioSalud(long idServicioSalud, long idMedico, long idAfiliado) {
		
		
		log.info("Registrando orden de servicio de salud");
		pp.registrarOrdenServicioSalud(idServicioSalud, idMedico, idAfiliado);
		log.info("Orden de servicio de salud registrado");
		
		
	}
	
	
	public void reservarServicioSalud(long idAfiliado, long idServicio) {
		
		log.info("Realizando reserva de servicio de salud");
		pp.reservarServicioSalud(idAfiliado, idServicio);
		log.info("Reserva de servicio de salud registrado");

	}
	
	
	public void registrarPrestServicio(long idServicio, long idAfiliado) {
		
		log.info("Registrando prestacion de servicio de salud");
		pp.registrarPrestServicio(idAfiliado, idServicio);
		log.info("Prestacion de servicio de salud registrado");
		
		
	}
	
	
	
	
	
	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de EPSAndes
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarEPSAndes ()
	{
        log.info ("Limpiando la BD de EPSAndes");
        long [] borrrados = pp.limpiarEPSAndes();	
        log.info ("Limpiando la BD de EPSAndes: Listo!");
        return borrrados;
	}
	

}
