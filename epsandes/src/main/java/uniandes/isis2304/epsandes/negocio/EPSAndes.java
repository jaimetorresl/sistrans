package uniandes.isis2304.epsandes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import uniandes.isis2304.epsandes.persistencia.PersistenciaEPSAndes;


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

		System.out.println(eps.getNombre());
		return eps;

	}


	/* ****************************************************************
	 * 			M�todos para manejar las IPS
	 *****************************************************************/

	public IPS registrarIPS(String nombre, String tipo, String localizacion, long idEPS) {



		IPS ips;
		log.info("Registrando IPS: " + nombre);

		ips = pp.registrarIPS(nombre, tipo, localizacion, idEPS);

		log.info ("IPS registrada: " + ips);

		return ips;

	}
	
	
	public RecepcionistaIPS registrarRecepcionistaIPS(String nombre, int rol, long idIPS, String correo) {



		RecepcionistaIPS recepcionista;
		log.info("Registrando recepcionista: " + nombre);

		recepcionista = pp.registrarRecepcionistaIPS(nombre, rol, idIPS, correo);

		log.info ("Recepcionista registrado: " + nombre);

		return recepcionista;

	}


	/* ****************************************************************
	 * 			M�todos para manejar los USUARIOS
	 *****************************************************************/
	public UsuarioIPS registrarUsuarioIPS (String nombre, String estado, long numDocumento, int tipoDocumento, String fechaNacimiento, long idEPS, String esAfiliado, String correo, String genero, int edad)
	{	
		UsuarioIPS usuarioIPS;

		log.info ("Adicionando usuarioIPS: " + nombre);
		usuarioIPS = pp.registrarUsuarioIPS (nombre, estado, numDocumento, tipoDocumento, fechaNacimiento, idEPS, esAfiliado, correo, genero, edad);
		log.info ("Adicionando usuarioIPS: " + usuarioIPS);
		return usuarioIPS;

	}


	public UsuarioEPS registrarUsuarioEPS (String nombre, int rol, long idEPS, String correo)
	{	
		UsuarioEPS usuarioEPS;

		log.info ("Adicionando usuarioEPS: " + nombre);
		usuarioEPS = pp.registrarUsuarioEPS(nombre, rol, idEPS, correo);
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
	public Medico registrarMedico (String nombre, String especialidad, long numRegMedico)
	{	
		Medico medico;

		log.info ("Adicionando medico: " + nombre);
		medico = pp.registrarMedico(nombre, especialidad, numRegMedico);
		log.info ("Adicionando medico: " + medico);
		return medico;

	}
	
	
	
	public IPSMedico registrarMedicoIPS(long idMedico, long idIPS)
	{	
		IPSMedico ipsMedico;

		log.info ("Asociando medico: " + idMedico);
		ipsMedico = pp.registrarMedicoIPS(idMedico, idIPS);
		log.info ("Asociando medico: " + idMedico);
		return ipsMedico;

	}


//	/**
//	 * Se busca un medico dentro de la lista de medicos existentes (por su id).
//	 * @param id del medico a buscar
//	 * @return medico o null por si no se encontro
//	 */
//	public Medico buscarMedicoPorId(long id) {
//
//		Medico medicoId;
//		log.info("Buscando medico");
//
//		medicoId = pp.buscarMedicoPorId(id);
//		log.info ("Medico encontrado: " + medicoId);
//
//		return medicoId;
//
//	}


	/* ****************************************************************
	 * 			M�todos para manejar los Servicios de salud
	 *****************************************************************/

	public Consulta registrarConsulta(String esAfiliado, String ordenPrevia, long idIPS, int capacidad, String horaInicio,
			String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{	
		Consulta consulta;

		log.info ("Adicionando consulta de la IPS: " + idIPS);
		consulta = pp.registarConsulta(esAfiliado, ordenPrevia, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
		log.info ("Adicionando consulta: " + consulta);
		return consulta;

	}
	
	
	public Terapia registrarTerapia(String ordenPrevia, String esAfiliado, String numSesiones, String tipoTerapia, long idIPS, int capacidad,
			String horaInicio, String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{	
		Terapia terapia;

		log.info ("Adicionando terapia tipo: " + tipoTerapia);
		terapia = pp.registrarTerapia(esAfiliado, ordenPrevia, numSesiones, tipoTerapia, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
		log.info ("Adicionando terapia: " + terapia);
		return terapia;

	}
	
	public ProcedimientoEsp registraProcedimientoEsp(String ordenPrevia, String esAfiliado, String conocimiento, String equipo, long idIPS, int capacidad,
			String horaInicio, String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{	
		ProcedimientoEsp procedimiento;

		log.info ("Adicionando procedimiento cono conocimiento en: " + conocimiento);
		procedimiento = pp.registrarProcedimientoEsp(esAfiliado, ordenPrevia, conocimiento, equipo, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
				
		log.info ("Adicionando procedimiento: " + procedimiento);
		return procedimiento;

	}
	
	public Hospitalizacion registrarHospitalizacion(String ordenPrevia, String esAfiliado, int numVisitas, long idIPS, int capacidad,
			String horaInicio, String horaFin, String fechaInicio, String fechaFin, String diaInicio, String diaFin, long idRecepcionista)
	{	
		Hospitalizacion hospitalizacion;

		log.info ("Adicionando una hospitalizacion a la IPS: " + idIPS);
		hospitalizacion = pp.registarHospitalizacion(ordenPrevia, esAfiliado, numVisitas, idIPS, capacidad, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista);
		log.info ("Adicionando hospitalizacion: " + hospitalizacion);
		return hospitalizacion;

	}
	
	
	
	/* ****************************************************************
	 * 			M�todos para manejar los Servicios de cita
	 *****************************************************************/
	
	public Cita registrarCita(String horaInicio, String horaFin, long idMedico, long idConsulta, long idTerapia, long idProcedimientoEsp, long idHospitalizacion, long idUsuarioIPS) {
		
		Cita cita;

		log.info ("Adicionando cita que empieza a las: " + horaInicio + " y termina a las: " + horaFin);
		cita = pp.registrarCita(horaInicio, horaFin, idMedico, idConsulta, idTerapia, idProcedimientoEsp, idHospitalizacion, idUsuarioIPS);
		log.info ("Adicionando cita: " + cita);
		return cita;
		
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
