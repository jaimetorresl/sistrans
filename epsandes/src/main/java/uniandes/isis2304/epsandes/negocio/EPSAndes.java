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

	//--------------------------------------------------------------------------------------------------------------
	//						METODOS DE CONSULTA
	//--------------------------------------------------------------------------------------------------------------


	/**
	 * MOSTRAR LA CANTIDAD DE SERVICIOS PRESTADOS POR CADA IPS DURANTE UN PERIODO DE TIEMPO Y EN
	 *EL AÑO CORRIDO
	 */
	public List<Object []> rfc1(String fechaInicio, String fechaFin) {
		log.info ("Listado servicios prestados por una IPS en un tiempo dado");
		List<Object []> tuplas = pp.rfc1(fechaInicio, fechaFin);
		log.info ("Listado servicios prestados por una IPS en un tiempo dado: Listo!");
		return tuplas;

	}

	/**
	 * Los servicios que fueron más solicitados en un período de tiempo dado.
	 */
	public List<Object []> rfc2(String fechaInicio, String fechaFin) {

		log.info ("Listando servicios que fueron más solicitados");
		List<Object []> tuplas = pp.rfc2(fechaInicio, fechaFin);
		log.info ("Listando servicios que fueron más solicitados: Listo!");
		return tuplas;
	}

	/**
	 * Dada una unidad de tiempo (por ejemplo, semana o mes) y un servicio de salud10, considerando todo el tiempo
	 *de operación de EPSAndes, indicar cuáles fueron las fechas de mayor demanda (mayor cantidad de servicios
	 *solicitados), las de mayor actividad (mayor cantidad de servicios efectivamente prestados) y también las fechas
	 *de menor demanda.
	 */
	public  List<Object []> rfc6(String unidadTiempo, String servicio) {
		log.info ("Listando fechas de mayor y menor demanda");
		List<Object []> tuplas = pp.rfc6(unidadTiempo, servicio);
		log.info ("Listando Bebedores y cuántas visitas ha realizado: Listo!");
		return tuplas;
	}

	/**
	 * Encontrar la información de los afiliados exigente. Se considera exigente a un afiliado que, durante el último
	 *año de operación de EPSAndes, ha solicitado y recibido más de doce (12) de servicios de salud, de por lo
	 *menos tres (3) tipos de servicio diferentes11. La información en el resultado debe evidenciar el hecho de ser
	 *afiliado exigente.
	 */
	public  List<Object []> rfc7(String fechaInicio, String fechaFin) {
		log.info ("Listando afiliados exigentes");
		List<Object []> tuplas = pp.rfc6(fechaInicio, fechaFin);
		log.info ("Listando afiliados exigentes: Listo!");
		return tuplas;
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	//	public EPSAndes (JsonObject tableConfig)
	//	{
	//		pp = PersistenciaEPSAndes.getInstance (tableConfig);
	//	}

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


	public RecepcionistaIPS darRecepcionistaIPS(long id) {



		RecepcionistaIPS recepcionista;
		log.info("Encontrando recepcionista: " + id);

		recepcionista = pp.darRecepcionistaIPS(id);

		log.info ("Recepcionista encontrado: " + id);

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



	public UsuarioEPS darUsuarioEPS(long id) {



		UsuarioEPS usuario;
		log.info("Encontrando usuario: " + id);

		usuario = pp.darUsuarioEPS(id);

		log.info ("Usuario encontrado: " + id);

		return usuario;

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
	 * 			M�todos para manejar las Recetas
	 *****************************************************************/

	public Receta registrarReceta(String diagnostico, String medicamentos, long idCita) {

		Receta receta;

		log.info ("Adicionando receta que tiene la cita asignada: " + idCita);
		receta = pp.registrarReceta(diagnostico, medicamentos, idCita);
		log.info ("Adicionando receta: " + receta);
		return receta;

	}


	/* ****************************************************************
	 * 			M�todos para manejar las Ordenes de SS
	 *****************************************************************/


	public OrdenServicio registrarOrdenServicio(long idReceta) {

		OrdenServicio ordenServicio;

		log.info ("Adicionando orden de servicio que tiene asociada la receta: " + idReceta);
		ordenServicio = pp.registrarOrdenServicio(idReceta);
		log.info ("Adicionando orden de servicio: " + ordenServicio);
		return ordenServicio;

	}


	public OrdenConsulta registrarOrdenConsulta(long idOrdenSS, long idConsulta) {

		OrdenConsulta ordenConsulta;

		log.info ("Adicionando orden de ss: consulta con la consulta asociada: " + idConsulta);
		ordenConsulta = pp.registrarOrdenConsulta(idOrdenSS, idConsulta);
		log.info ("Adicionando orden consulta: " + ordenConsulta);
		return ordenConsulta;

	}


	public OrdenTerapia registrarOrdenTerapia(long idOrdenSS, long idTerapia) {

		OrdenTerapia ordenTerapia;

		log.info ("Adicionando orden de ss: terapia con la terapia asociada: " + idTerapia);
		ordenTerapia = pp.registrarOrdenTerapia(idOrdenSS, idTerapia);
		log.info ("Adicionando orden terapia: " + ordenTerapia);
		return ordenTerapia;

	}

	public OrdenProcedimientoEsp registrarOrdenProcedimientoEsp(long idOrdenSS, long idProcedimiento) {

		OrdenProcedimientoEsp ordenProcedimiento;

		log.info ("Adicionando orden de ss: procedimiento especial con el procedimiento asociado: " + idProcedimiento);
		ordenProcedimiento = pp.registrarOrdenProcedimientoEsp(idOrdenSS, idProcedimiento);
		log.info ("Adicionando orden procedimiento: " + ordenProcedimiento);
		return ordenProcedimiento;

	}


	public OrdenHospitalizacion registrarOrdenHospitalizacion(long idOrdenSS, long idHospitalizacion) {

		OrdenHospitalizacion ordenHospitalizacion;

		log.info ("Adicionando orden de ss: hospitalizacion con la hospitalizacion asociado: " + idHospitalizacion);
		ordenHospitalizacion = pp.registrarOrdenHospitalizacion(idOrdenSS, idHospitalizacion);
		log.info ("Adicionando orden hospitalizacion: " + ordenHospitalizacion);
		return ordenHospitalizacion;

	}


	/**--------------------------------------------------------------------------------
	----------------------------------Iteracion 2 -------------------------------------
	----------------------------------------------------------------------------------*/



	public List<Consulta> darConsultas() {

		return pp.darConsultas();

	}

	public List<Terapia> darTerapias() {

		return pp.darTerapias();

	}

	public List<ProcedimientoEsp> darProcedimientosEsp() {

		return pp.darProcedimientosEsp();

	}

	public List<Hospitalizacion> darHospitalizaciones() {

		return pp.darHospitalizacion();

	}




	public CampaniaPrevencion registrarCampaniaPrevencion(String localizacion, String fechaInicio, String fechaFin, long idEPS) {

		CampaniaPrevencion campaniaPrevencion;

		log.info ("Adicionando campania de prevencion en la localizacion de: " + localizacion);
		campaniaPrevencion = pp.registrarCampaniaPrevencion(localizacion, fechaInicio, fechaFin, idEPS);
		log.info ("Adicionando campania de prevencion: " + campaniaPrevencion);
		return campaniaPrevencion;

	}


	public CampaniaConsulta registrarCampaniaConsulta(long idCampania, long idConsulta,
			String fechaInicio, String fechaFin, String disponible) {

		CampaniaConsulta campaniaConsulta;

		log.info ("Adicionando una consulta asociada a la campania de prevencion: " + idCampania);
		campaniaConsulta = pp.registrarCampaniaConsulta(idCampania, idConsulta, fechaInicio, fechaFin, disponible);
		log.info ("Adicionando consulta a la campania de prevencion: " + campaniaConsulta);
		return campaniaConsulta;

	}


	public CampaniaTerapia registrarCampaniaTerapia(long idCampania, long idTerapia,
			String fechaInicio, String fechaFin, String disponible) {

		CampaniaTerapia campaniaTerapia;

		log.info ("Adicionando una terapia asociada a la campania de prevencion: " + idCampania);
		campaniaTerapia = pp.registrarCampaniaTerapia(idCampania, idTerapia, fechaInicio, fechaFin, disponible);
		log.info ("Adicionando terapia a la campania de prevencion: " + campaniaTerapia);
		return campaniaTerapia;

	}


	public CampaniaProcedimientoEsp registrarCampaniaProcedimiento(long idCampania, long idProcedimiento,
			String fechaInicio, String fechaFin, String disponible) {

		CampaniaProcedimientoEsp campaniaProcedimiento;

		log.info ("Adicionando un procedimiento especial asociada a la campania de prevencion: " + idCampania);
		campaniaProcedimiento = pp.registrarCampaniaProcedimiento(idCampania, idProcedimiento, fechaInicio, fechaFin, disponible);
		log.info ("Adicionando procedimiento especial a la campania de prevencion: " + campaniaProcedimiento);
		return campaniaProcedimiento;

	}


	public CampaniaHospitalizacion registrarCampaniaHospitalizacion(long idCampania, long idHospitalizacion,
			String fechaInicio, String fechaFin, String disponible) {

		CampaniaHospitalizacion campaniaHospitalizacion;

		log.info ("Adicionando una hospitalizacion asociada a la campania de prevencion: " + idCampania);
		campaniaHospitalizacion = pp.registrarCampaniaHospitalizacion(idCampania, idHospitalizacion, fechaInicio, fechaFin, disponible);
		log.info ("Adicionando hospitalizacion a la campania de prevencion: " + campaniaHospitalizacion);
		return campaniaHospitalizacion;

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
