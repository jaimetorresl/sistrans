package uniandes.isis2304.epsandes.interfazApp;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.epsandes.negocio.Consulta;
import uniandes.isis2304.epsandes.negocio.EPSAndes;
import uniandes.isis2304.epsandes.negocio.Hospitalizacion;
import uniandes.isis2304.epsandes.negocio.IPS;
import uniandes.isis2304.epsandes.negocio.ProcedimientoEsp;
import uniandes.isis2304.epsandes.negocio.RecepcionistaIPS;
import uniandes.isis2304.epsandes.negocio.Terapia;
import uniandes.isis2304.epsandes.negocio.UsuarioEPS;
import uniandes.isis2304.epsandes.negocio.VOCampaniaPrevencion;
import uniandes.isis2304.epsandes.negocio.VOCita;
import uniandes.isis2304.epsandes.negocio.VOConsulta;
import uniandes.isis2304.epsandes.negocio.VOEPS;
import uniandes.isis2304.epsandes.negocio.VOHospitalizacion;
import uniandes.isis2304.epsandes.negocio.VOIPS;
import uniandes.isis2304.epsandes.negocio.VOIPSMedico;
import uniandes.isis2304.epsandes.negocio.VOMedico;
import uniandes.isis2304.epsandes.negocio.VOOrdenConsulta;
import uniandes.isis2304.epsandes.negocio.VOOrdenHospitalizacion;
import uniandes.isis2304.epsandes.negocio.VOOrdenProcedimientoEsp;
import uniandes.isis2304.epsandes.negocio.VOOrdenServicio;
import uniandes.isis2304.epsandes.negocio.VOOrdenTerapia;
import uniandes.isis2304.epsandes.negocio.VOProcedimientoEsp;
import uniandes.isis2304.epsandes.negocio.VORecepcionistaIPS;
import uniandes.isis2304.epsandes.negocio.VOReceta;
import uniandes.isis2304.epsandes.negocio.VOTerapia;
import uniandes.isis2304.epsandes.negocio.VOUsuarioEPS;
import uniandes.isis2304.epsandes.negocio.VOUsuarioIPS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


/**
 * Clase principal de la interfaz
 * @author Nicolas Potes Garcia - Jaime Torres
 * 
 * Este codigo esta basado en la aplicacion de parranderos realizada por German Bravo
 */

public class InterfazEpsAndesApp extends JFrame implements ActionListener {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(InterfazEpsAndesApp.class.getName());

	/**
	 * Ruta al archivo de configuraci�n de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociaci�n a la clase principal del negocio.
	 */
	private EPSAndes epsandes;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuraci�n de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacci�n para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Men� de la aplicaci�n
	 */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicaci�n. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazEpsAndesApp( )
	{
		// Carga la configuraci�n de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gr�fica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		//tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		epsandes = new EPSAndes ();

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			M�todos de configuraci�n de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuraci�n para la aplicaci�, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuraci�n deseada
	 * @param archConfig - Archivo Json que contiene la configuraci�n
	 * @return Un objeto JSON con la configuraci�n del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontr� un archivo de configuraci�n v�lido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de interfaz v�lido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * M�todo para configurar el frame principal de la aplicaci�n
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuraci�n por defecto" );			
			titulo = "EPSAndes APP Default";
			alto = 300;
			ancho = 600;
		}
		else
		{
			log.info ( "Se aplica configuraci�n indicada en el archivo de configuraci�n" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * M�todo para crear el men� de la aplicaci�n con base em el objeto JSON le�do
	 * Genera una barra de men� y los men�s con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los men�s deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creaci�n de la barra de men�s
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creaci�n de cada uno de los men�s
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creaci�n de cada una de las opciones del men�
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	/* ****************************************************************
	 * 			CRUD de EPS
	 *****************************************************************/


	public void adicionarEPS()
	{	


		int acepto  = JOptionPane.showConfirmDialog(this, "Es un administrador de datos de la EPS?");


		if(acepto == 0) {


			try 
			{



				String idAdminDatos = JOptionPane.showInputDialog("Escriba su identificacion");
				long idAdminDatos2 = Long.parseLong(idAdminDatos);

				UsuarioEPS usuario = epsandes.darUsuarioEPS(idAdminDatos2);


				if(usuario != null) {


					String nombreEPS = JOptionPane.showInputDialog(this, "Nombre de EPS?", "Adicionar EPS", JOptionPane.QUESTION_MESSAGE);

					if (nombreEPS != null)
					{


						VOEPS eps = epsandes.registrarEPS(nombreEPS);

						if (eps == null)
						{
							throw new Exception ("No se pudo crear una EPS con nombre: " + nombreEPS);
						}
						String resultado = "En adicionarEPS\n\n";
						resultado += "EPS adicionado exitosamente: " + eps;
						resultado += "\n Operaci�n terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
					else
					{
						panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
					}  					


				} else {

					JOptionPane.showMessageDialog(this, "Identificacion invalida");

				}


			} 
			catch (Exception e) 
			{
				//			e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}


		} else {

			JOptionPane.showMessageDialog(this, "Debe ser un administrador de datos para acceder a esta seccion");


		}


	}




	public void adicionarIPS()
	{
		try 
		{


			String nombreIPS = JOptionPane.showInputDialog(this, "Nombre de IPS?", "Adicionar IPS", JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog(this, "Tipo de IPS?", "Adicionar IPS", JOptionPane.QUESTION_MESSAGE);
			String localizacion = JOptionPane.showInputDialog(this, "Localizacion de IPS?", "Adicionar IPS", JOptionPane.QUESTION_MESSAGE);
			String idEPS = JOptionPane.showInputDialog(this, "IdEPS de IPS?", "Adicionar IPS", JOptionPane.QUESTION_MESSAGE);
			long idEPS2 = Long.parseLong(idEPS);

			if (nombreIPS != null)
			{

				System.out.println(idEPS2);

				VOIPS ips = epsandes.registrarIPS(nombreIPS, tipo, localizacion, idEPS2);

				if (ips == null)
				{
					throw new Exception ("No se pudo crear una IPS con nombre: " + nombreIPS);
				}
				String resultado = "En adicionarIPS\n\n";
				resultado += "IPS adicionado exitosamente: " + ips;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void adicionarRecepcionistaIPS()
	{
		try 
		{


			String nombre = JOptionPane.showInputDialog(this, "Nombre del recepcionista?", "Adicionar Recepcionista de IPS", JOptionPane.QUESTION_MESSAGE);
			String rol = JOptionPane.showInputDialog(this, "Rol del recepcionista (3 si es recepcionista)?", "Adicionar Recepcionista de IPS", JOptionPane.QUESTION_MESSAGE);
			String idIPS = JOptionPane.showInputDialog(this, "Id de la IPS a la cual pertenece?", "Adicionar Recepcionista de IPS", JOptionPane.QUESTION_MESSAGE);
			String correo = JOptionPane.showInputDialog(this, "Correo electronico del recepcionista?", "Adicionar Recepcionista de IPS", JOptionPane.QUESTION_MESSAGE);

			int rol2 = Integer.parseInt(rol);
			long idIPS2 = Long.parseLong(idIPS);

			if (idIPS != null)
			{


				VORecepcionistaIPS recepcionista = epsandes.registrarRecepcionistaIPS(nombre, rol2, idIPS2, correo);

				if (recepcionista == null)
				{
					throw new Exception ("No se pudo registrar el recepcionista");
				}
				String resultado = "En adicionarRecepcionistaIPS\n\n";
				resultado += "Recepcionista agregado exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}



	public void adicionarUsuarioEPS()
	{
		try 
		{


			String nombreUsuario = JOptionPane.showInputDialog(this, "Nombre del Usuario de la EPS?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String rol = JOptionPane.showInputDialog(this, "Rol del usuario (1 para gerente, 2 administrador)?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String correo = JOptionPane.showInputDialog(this, "Correo de usuario?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String idEPS = JOptionPane.showInputDialog(this, "IdEPS del Usuario?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			long idEPS2 = Long.parseLong(idEPS);
			int rol2 = Integer.parseInt(rol);

			if (nombreUsuario != null)
			{

				System.out.println(idEPS2);

				VOUsuarioEPS usuario = epsandes.registrarUsuarioEPS(nombreUsuario, rol2, idEPS2, correo);

				if (usuario == null)
				{
					throw new Exception ("No se pudo crear un UsuarioEPS con nombre: " + nombreUsuario);
				}
				String resultado = "En adicionarUsuarioEPS\n\n";
				resultado += "UsuarioEPS adicionado exitosamente: " + usuario;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void adicionarUsuarioIPS()
	{
		try 
		{


			String nombreUsuario = JOptionPane.showInputDialog(this, "Nombre del Usuario de la IPS?", "Adicionar UsuarioIPS", JOptionPane.QUESTION_MESSAGE);
			String estado = JOptionPane.showInputDialog(this, "Estado del usuario?", "Adicionar UsuarioIPS", JOptionPane.QUESTION_MESSAGE);
			String numDocumento = JOptionPane.showInputDialog(this, "Numero de documento del usuario?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String tipoDocumento = JOptionPane.showInputDialog(this, "Tipo de documento del usuario (1 para cc, 2 TI, 3 pasaporte)?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String idEPS = JOptionPane.showInputDialog(this, "IdEPS del Usuario?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String fechaNacimiento = JOptionPane.showInputDialog(this, "Fecha nacimiento del usuario (formato: AA/MM/DD)?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String esAfiliado = JOptionPane.showInputDialog(this, "El usuario es afiliado (SI para si, NO para decir que no es afiliado)?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String correo = JOptionPane.showInputDialog(this, "Correo del usuario?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String genero = JOptionPane.showInputDialog(this, "Genero del usuario?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			String edad = JOptionPane.showInputDialog(this, "Edad del usuario?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			long idEPS2 = Long.parseLong(idEPS);
			int edad2 = Integer.parseInt(edad);
			long numDocumento2 = Long.parseLong(numDocumento);
			int tipoDocumento2 = Integer.parseInt(tipoDocumento);


			if (nombreUsuario != null)
			{

				VOUsuarioIPS usuario = epsandes.registrarUsuarioIPS(nombreUsuario, estado, numDocumento2, tipoDocumento2, fechaNacimiento, idEPS2, esAfiliado, correo, genero, edad2);

				if (usuario == null)
				{
					throw new Exception ("No se pudo crear un UsuarioIPS con nombre: " + nombreUsuario);
				}
				String resultado = "En adicionarUsuarioIPS\n\n";
				resultado += "UsuarioIPS adicionado exitosamente: " + usuario;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}



	public void adicionarMedico()
	{
		try 
		{


			String nombre = JOptionPane.showInputDialog(this, "Nombre del Medico?", "Adicionar Medico", JOptionPane.QUESTION_MESSAGE);
			String especialidad = JOptionPane.showInputDialog(this, "Especialidad del medico?", "Adicionar Medico", JOptionPane.QUESTION_MESSAGE);
			String numRegMedico = JOptionPane.showInputDialog(this, "Numero de registro medico?", "Adicionar UsuarioEPS", JOptionPane.QUESTION_MESSAGE);
			long numRegMedico2 = Long.parseLong(numRegMedico);

			if (nombre != null)
			{


				VOMedico medico = epsandes.registrarMedico(nombre, especialidad, numRegMedico2);

				if (medico == null)
				{
					throw new Exception ("No se pudo crear un Medico con nombre: " + nombre);
				}
				String resultado = "En adicionarMedico\n\n";
				resultado += "Medico adicionado exitosamente: " + medico;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}




	public void adicionarMedicoIPS()
	{
		try 
		{


			String idMedico = JOptionPane.showInputDialog(this, "Identificacion del Medico?", "Asociar Medico a IPS", JOptionPane.QUESTION_MESSAGE);
			String idIPS = JOptionPane.showInputDialog(this, "Identificacion de la IPS?", "Asociar Medico a IPS", JOptionPane.QUESTION_MESSAGE);
			long idMedico2 = Long.parseLong(idMedico);
			long idIPS2 = Long.parseLong(idIPS);

			if (idMedico != null)
			{


				VOIPSMedico ipsMedico = epsandes.registrarMedicoIPS(idMedico2, idIPS2);

				if (ipsMedico == null)
				{
					throw new Exception ("No se pudo asociar un medico con la IPS");
				}
				String resultado = "En adicionarMedicoIPS\n\n";
				resultado += "Medico asociado exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}



	//Adicionar cita 

	public void adicionarCita()
	{
		try 
		{


			String horaInicio = JOptionPane.showInputDialog("Dar hora inicio de la cita (DD-MM-AA HH24:MI:SS)");	
			String horaFin = JOptionPane.showInputDialog("Dar hora fin de la cita (DD-MM-AA HH24:MI:SS)");
			String idMedico = JOptionPane.showInputDialog("Dar id del medico responsable de la cita");

			long idMedico2 = Long.parseLong(idMedico);

			int opcionSS = Integer.parseInt(JOptionPane.showInputDialog("Que tipo de ss es? 1-consulta, 2-terapia, 3-procedimiento especial, 4-hospitalizacion"));	


			String ss = "";

			long idConsulta2 = 0;
			long idTerapia2 = 0;
			long idProcedimiento2 = 0;
			long idHospitalizacion2 = 0;

			if(opcionSS == 1) {

				String idConsulta = JOptionPane.showInputDialog("Id de la consulta?");
				idConsulta2 = Long.parseLong(idConsulta);


			} else if (opcionSS == 2) {

				String idTerapia = JOptionPane.showInputDialog("Id de la terapia?");
				idTerapia2 = Long.parseLong(idTerapia);

			} else if (opcionSS == 3) {

				String idProcedimiento = JOptionPane.showInputDialog("Id de la procedimiento?");
				idProcedimiento2 = Long.parseLong(idProcedimiento);

			} else if (opcionSS == 4) {

				String idHospitalizacion = JOptionPane.showInputDialog("Id de la hospitalizacion?");
				idHospitalizacion2 = Long.parseLong(idHospitalizacion);

			} 


			String idUsuarioIPS = JOptionPane.showInputDialog("Id del paciente?");
			long idUsuarioIPS2 = Long.parseLong(idUsuarioIPS);




			if (idUsuarioIPS != null && idMedico != null)
			{


				VOCita cita = epsandes.registrarCita(horaInicio, horaFin, idMedico2, idConsulta2, idTerapia2, idProcedimiento2, idHospitalizacion2, idUsuarioIPS2);

				if (cita == null)
				{
					throw new Exception ("No se pudo crear la cita");
				}
				String resultado = "En adicionarCita\n\n";
				resultado += "Cita creada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}



	//Adicionar receta

	public void adicionarReceta()
	{
		try 
		{

			String diagnostico = JOptionPane.showInputDialog("Dar diagnostico");	
			String medicamentos = JOptionPane.showInputDialog("Dar medicamentos para tratar al paciente");
			String idCita = JOptionPane.showInputDialog("Dar id de la cita correspondiente");

			long idCita2 = Long.parseLong(idCita);



			if (idCita != null)
			{


				VOReceta receta = epsandes.registrarReceta(diagnostico, medicamentos, idCita2);

				if (receta == null)
				{
					throw new Exception ("No se pudo crear la receta");
				}
				String resultado = "En adicionarReceta\n\n";
				resultado += "Receta creada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	//SERVICIOS DE SALUD


	public void adicionarConsulta()
	{
		try 
		{

			String idIPS = JOptionPane.showInputDialog(this, "Id de la IPS a la cual pertenece el ss?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);
			String capacidad = JOptionPane.showInputDialog(this, "Capacidad del ss?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);

			String horaInicio = JOptionPane.showInputDialog(this, "Hora de inicio (HH:mm:ss) (formato 24 hrs)?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);
			String horaFin = JOptionPane.showInputDialog(this, "Hora fin (HH:mm:ss) (formato 24 hrs)?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);

			String fechaInicio = JOptionPane.showInputDialog(this, "Fecha de inicio (AA/MM/DD) del servicio?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);
			String fechaFin = JOptionPane.showInputDialog(this, "Fecha de fin (AA/MM/DD) del servicio?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);

			String diaInicio = JOptionPane.showInputDialog(this, "Dia de la semana en la que inicia el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);
			String diaFin = JOptionPane.showInputDialog(this, "Dia de la semana en la que finaliza el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);

			String esAfiliado = JOptionPane.showInputDialog(this, "Es necesario ser afiliado para el ss (SI, NO)?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);
			String ordenPrevia = JOptionPane.showInputDialog(this, "Se necesita una ordenPrevia por parte de un medico (SI, NO)?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);

			String idRecepcionista = JOptionPane.showInputDialog(this, "Id del recepcionista que registro el ss?", "Adicionar Consulta", JOptionPane.QUESTION_MESSAGE);


			long idIPS2 = Long.parseLong(idIPS);
			int capacidad2 = Integer.parseInt(capacidad);

			long idRecepcionista2 = Long.parseLong(idRecepcionista);

			if (idIPS != null)
			{


				VOConsulta consulta = epsandes.registrarConsulta(esAfiliado, ordenPrevia, idIPS2, capacidad2, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista2);

				if (consulta == null)
				{
					throw new Exception ("No se pudo registrar el servicio de salud: consulta");
				}
				String resultado = "En adicionarConsulta\n\n";
				resultado += "Consulta registrada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}




	public void adicionarTerapia()
	{
		try 
		{

			String idIPS = JOptionPane.showInputDialog(this, "Id de la IPS a la cual pertenece el ss?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);
			String capacidad = JOptionPane.showInputDialog(this, "Capacidad del ss?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);

			String horaInicio = JOptionPane.showInputDialog(this, "Hora de inicio (HH:mm:ss) (formato 24 hrs)?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);
			String horaFin = JOptionPane.showInputDialog(this, "Hora fin (HH:mm:ss) (formato 24 hrs)?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);

			String fechaInicio = JOptionPane.showInputDialog(this, "Fecha de inicio (AA/MM/DD) del servicio?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);
			String fechaFin = JOptionPane.showInputDialog(this, "Fecha de fin (AA/MM/DD) del servicio?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);

			String diaInicio = JOptionPane.showInputDialog(this, "Dia de la semana en la que inicia el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);
			String diaFin = JOptionPane.showInputDialog(this, "Dia de la semana en la que finaliza el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);

			String esAfiliado = JOptionPane.showInputDialog(this, "Es necesario ser afiliado para el ss (SI, NO)?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);
			String ordenPrevia = JOptionPane.showInputDialog(this, "Se necesita una ordenPrevia por parte de un medico (SI, NO)?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);

			String idRecepcionista = JOptionPane.showInputDialog(this, "Id del recepcionista que registro el ss?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);

			String numSesiones = JOptionPane.showInputDialog(this, "Numero de series de cierto ejercicio ?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);
			String tipoTerapia = JOptionPane.showInputDialog(this, "Tipo de terapia a realizar?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);


			long idIPS2 = Long.parseLong(idIPS);
			int capacidad2 = Integer.parseInt(capacidad);

			long idRecepcionista2 = Long.parseLong(idRecepcionista);

			if (idIPS != null)
			{


				VOTerapia terapia = epsandes.registrarTerapia(ordenPrevia, esAfiliado, numSesiones, tipoTerapia, idIPS2, capacidad2, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista2);

				if (terapia == null)
				{
					throw new Exception ("No se pudo registrar el servicio de salud: terapia");
				}
				String resultado = "En adicionarTerapia\n\n";
				resultado += "Terapia registrada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void adicionarProcedimientoEsp()
	{
		try 
		{

			String idIPS = JOptionPane.showInputDialog(this, "Id de la IPS a la cual pertenece el ss?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			String capacidad = JOptionPane.showInputDialog(this, "Capacidad del ss?", "Adicionar Terapia", JOptionPane.QUESTION_MESSAGE);

			String horaInicio = JOptionPane.showInputDialog(this, "Hora de inicio (HH:mm:ss) (formato 24 hrs)?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			String horaFin = JOptionPane.showInputDialog(this, "Hora fin (HH:mm:ss) (formato 24 hrs)?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);

			String fechaInicio = JOptionPane.showInputDialog(this, "Fecha de inicio (AA/MM/DD) del servicio?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			String fechaFin = JOptionPane.showInputDialog(this, "Fecha de fin (AA/MM/DD) del servicio?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);

			String diaInicio = JOptionPane.showInputDialog(this, "Dia de la semana en la que inicia el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			String diaFin = JOptionPane.showInputDialog(this, "Dia de la semana en la que finaliza el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);

			String esAfiliado = JOptionPane.showInputDialog(this, "Es necesario ser afiliado para el ss (SI, NO)?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			String ordenPrevia = JOptionPane.showInputDialog(this, "Se necesita una ordenPrevia por parte de un medico (SI, NO)?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);

			String idRecepcionista = JOptionPane.showInputDialog(this, "Id del recepcionista que registro el ss?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);

			String conocimiento = JOptionPane.showInputDialog(this, "Conocimiento medico necesario para el procedimiento?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			String equipo = JOptionPane.showInputDialog(this, "Equipo necesario para el procedimiento?", "Adicionar Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);


			long idIPS2 = Long.parseLong(idIPS);
			int capacidad2 = Integer.parseInt(capacidad);

			long idRecepcionista2 = Long.parseLong(idRecepcionista);

			if (idIPS != null)
			{


				VOProcedimientoEsp procedimiento = epsandes.registraProcedimientoEsp(ordenPrevia, esAfiliado, conocimiento, equipo, idIPS2, capacidad2, horaInicio, horaFin, fechaInicio, fechaFin, diaInicio, diaFin, idRecepcionista2);

				if (procedimiento == null)
				{
					throw new Exception ("No se pudo registrar el servicio de salud: procedimiento especial");
				}
				String resultado = "En adicionarProcedimientoEsp\n\n";
				resultado += "Procedimiento especial registrado exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}




	public void adicionarHospitalizacion()
	{
		try 
		{

			String idIPS = JOptionPane.showInputDialog(this, "Id de la IPS a la cual pertenece el ss?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);
			String capacidad = JOptionPane.showInputDialog(this, "Capacidad del ss?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);

			String horaInicio = JOptionPane.showInputDialog(this, "Hora de inicio (HH:mm:ss) (formato 24 hrs)?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);
			String horaFin = JOptionPane.showInputDialog(this, "Hora fin (HH:mm:ss) (formato 24 hrs)?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);

			String fechaInicio = JOptionPane.showInputDialog(this, "Fecha de inicio (AA/MM/DD) del servicio?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);
			String fechaFin = JOptionPane.showInputDialog(this, "Fecha de fin (AA/MM/DD) del servicio?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);

			//String diaInicio = JOptionPane.showInputDialog(this, "Dia de la semana en la que inicia el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);
			//String diaFin = JOptionPane.showInputDialog(this, "Dia de la semana en la que finaliza el servicio (LUNES o MARTES o MIERCOLES, ...)?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);

			String esAfiliado = JOptionPane.showInputDialog(this, "Es necesario ser afiliado para el ss (SI, NO)?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);
			String ordenPrevia = JOptionPane.showInputDialog(this, "Se necesita una ordenPrevia por parte de un medico (SI, NO)?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);

			String idRecepcionista = JOptionPane.showInputDialog(this, "Id del recepcionista que registro el ss?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);

			String numVisitas = JOptionPane.showInputDialog(this, "Numero de visitas de la hospitalizacion?", "Adicionar Hospitalizacion", JOptionPane.QUESTION_MESSAGE);


			long idIPS2 = Long.parseLong(idIPS);
			int capacidad2 = Integer.parseInt(capacidad);

			long idRecepcionista2 = Long.parseLong(idRecepcionista);

			int numVisitas2 = Integer.parseInt(numVisitas);

			if (idIPS != null)
			{


				VOHospitalizacion hospitalizacion = epsandes.registrarHospitalizacion(ordenPrevia, esAfiliado, numVisitas2, idIPS2, capacidad2, horaInicio, horaFin, fechaInicio, fechaFin, "", "", idRecepcionista2);

				if (hospitalizacion == null)
				{
					throw new Exception ("No se pudo registrar el servicio de salud: hospitalizacion");
				}
				String resultado = "En adicionarHospitalizacion\n\n";
				resultado += "Hospitalizacion registrada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	public void adicionarOrdenConsulta()
	{
		try 
		{

			String idReceta = JOptionPane.showInputDialog(this, "Id de la receta asociada a la orden de ss?", "Adicionar Orden de SS: Consulta", JOptionPane.QUESTION_MESSAGE);
			long idReceta2 = Long.parseLong(idReceta);


			String idConsulta = JOptionPane.showInputDialog(this, "Id del ss: Consulta?", "Adicionar Orden de SS: Consulta", JOptionPane.QUESTION_MESSAGE);
			long idConsulta2 = Long.parseLong(idConsulta);


			if (idConsulta != null && idReceta != null)
			{

				VOOrdenServicio ordenServicio = epsandes.registrarOrdenServicio(idReceta2);

				VOOrdenConsulta ordenConsulta = epsandes.registrarOrdenConsulta(ordenServicio.getId(), idConsulta2);

				if (ordenConsulta == null)
				{
					throw new Exception ("No se pudo registrar la orden de ss asociado a una consulta");
				}
				String resultado = "En adicionarOrdenConsulta\n\n";
				resultado += "Orden consulta registrada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}




	public void adicionarOrdenTerapia()
	{
		try 
		{

			String idReceta = JOptionPane.showInputDialog(this, "Id de la receta asociada a la orden de ss?", "Adicionar Orden de SS: Terapia", JOptionPane.QUESTION_MESSAGE);
			long idReceta2 = Long.parseLong(idReceta);


			String idTerapia = JOptionPane.showInputDialog(this, "Id del ss: Terapia?", "Adicionar Orden de SS: Terapia", JOptionPane.QUESTION_MESSAGE);
			long idTerapia2 = Long.parseLong(idTerapia);


			if (idTerapia != null && idReceta != null)
			{

				VOOrdenServicio ordenServicio = epsandes.registrarOrdenServicio(idReceta2);

				VOOrdenTerapia ordenTerapia = epsandes.registrarOrdenTerapia(ordenServicio.getId(), idTerapia2);

				if (ordenTerapia == null)
				{
					throw new Exception ("No se pudo registrar la orden de ss asociado a una terapia");
				}
				String resultado = "En adicionarOrdenTerapia\n\n";
				resultado += "Orden terapia registrada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}



	public void adicionarOrdenProcedimientoEsp()
	{
		try 
		{

			String idReceta = JOptionPane.showInputDialog(this, "Id de la receta asociada a la orden de ss?", "Adicionar Orden de SS: Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			long idReceta2 = Long.parseLong(idReceta);


			String idProcedimiento = JOptionPane.showInputDialog(this, "Id del ss: Procedimiento Especial?", "Adicionar Orden de SS: Procedimiento Especial", JOptionPane.QUESTION_MESSAGE);
			long idProcedimiento2 = Long.parseLong(idProcedimiento);


			if (idProcedimiento != null && idReceta != null)
			{

				VOOrdenServicio ordenServicio = epsandes.registrarOrdenServicio(idReceta2);

				VOOrdenProcedimientoEsp ordenProcedimiento = epsandes.registrarOrdenProcedimientoEsp(ordenServicio.getId(), idProcedimiento2);

				if (ordenProcedimiento == null)
				{
					throw new Exception ("No se pudo registrar la orden de ss asociado a un procedimiento especial");
				}
				String resultado = "En adicionarOrdenProcedimientoEspecial\n\n";
				resultado += "Orden procedimiento especial registrada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}



	public void adicionarOrdenHospitalizacion()
	{
		try 
		{

			String idReceta = JOptionPane.showInputDialog(this, "Id de la receta asociada a la orden de ss?", "Adicionar Orden de SS: Hospitalizacion", JOptionPane.QUESTION_MESSAGE);
			long idReceta2 = Long.parseLong(idReceta);


			String idHospitalizacion = JOptionPane.showInputDialog(this, "Id del ss: Hospitalizacion?", "Adicionar Orden de SS: Hospitalizacion", JOptionPane.QUESTION_MESSAGE);
			long idHospitalizacion2 = Long.parseLong(idHospitalizacion);


			if (idHospitalizacion != null && idReceta != null)
			{

				VOOrdenServicio ordenServicio = epsandes.registrarOrdenServicio(idReceta2);

				VOOrdenHospitalizacion ordenHospitalizacion = epsandes.registrarOrdenHospitalizacion(ordenServicio.getId(), idHospitalizacion2);

				if (ordenHospitalizacion == null)
				{
					throw new Exception ("No se pudo registrar la orden de ss asociado a una hospitalizacion");
				}
				String resultado = "En adicionarOrdenHospitalizacion\n\n";
				resultado += "Orden hospitalizacion registrada exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}


		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}




	//Atributos para que el metodo adicionarCampania sirva
	private String fechaInicioSS;
	private String fechaFinSS;
	private ArrayList<String> listaIdConsulta = new ArrayList<String>();
	private ArrayList<String> listaIdTerapia = new ArrayList<String>();
	private ArrayList<String> listaIdProcedimiento = new ArrayList<String>();
	private ArrayList<String> listaIdHospitalizacion = new ArrayList<String>();

	private String opcionSS;



	public void adicionarCampaniaPrevencion() throws Exception
	{	

		//Id de la EPS a la que esta asociada la campania

		String idEPS = JOptionPane.showInputDialog(this, "Id de la EPS a la que esta asociada la campania?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
		long idEPS2 = Long.parseLong(idEPS);

		//Se elige las fechas de inicio y fin de la campania
		String fechaInicio = JOptionPane.showInputDialog(this, "Fecha y hora de inicio de la campania (DD-MM-AA HH:MM:SS) (formato 24hrs)?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
		String fechaFin = JOptionPane.showInputDialog(this, "Fecha y hora de fin de la campania (DD-MM-AA HH:MM:SS) (formato 24hrs)?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);


		//		Date dateInicio= (Date) new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(fechaInicio);
		//		Date dateFin= (Date) new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(fechaFin);

		//Se elige la localizacion de la campania de prevencion

		String localizacion = JOptionPane.showInputDialog(this, "Localizacion de la campania?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);

		int respuestaMas = 0;
		while(respuestaMas == 0) {


			//Se elige el tipo de ss que se quiere mostrar en lista (0-3)
			Object[] options1 = {"Consulta", "Terapia", "Procedimiento Especial", "Hospitalizacion"};
			int opcion = JOptionPane.showOptionDialog(null,
					"Elige el tipo de servicio",
					"Adicionar Campania Prevencion",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					options1,
					null);

			String resultado;

			//Fechas de inicio y fin del ss dentro del ss
			fechaInicioSS = JOptionPane.showInputDialog(this, "Fecha y hora de inicio del servicio consulta (DD-MM-AA HH:MM:SS) (formato 24hrs)?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
			fechaFinSS = JOptionPane.showInputDialog(this, "Fecha y hora de fin del servicio consulta (DD-MM-AA HH:MM:SS) (formato 24hrs)?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);

			//VERIFICAR Y CAMBIAR LO DE LAS FECHAS

			//			Date date1= (Date) new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(fechaInicioSS);  
			//			Date date2= (Date) new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(fechaFinSS); 


			//			if( !( (dateInicio.before(date1) && date1.before(dateFin)) && (dateInicio.before(date2)) && (date2.before(dateFin)) && (date1.before(date2))) ) {
			//				
			//				JOptionPane.showMessageDialog(this, "Las fechas del ss no estan en el rango de fechas de la campania");
			//				throw new Exception("Las fechas del ss no estan en el rango de fechas de la campania");
			//				
			//			}


			//Cantidad de dias que dura el servicio de salud dentro de la campania
			String numDias = JOptionPane.showInputDialog(this, "Cantidad de dias que dura la campania?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
			int numDias2 = Integer.parseInt(numDias);


			resultado = "";

			int contSS = 0;
			opcionSS = "";

			if(opcion == 0) {	
				//Se pide la lista de consultas que existen
				opcionSS = "CONSULTA";

				List<Consulta> listConsulta = epsandes.darConsultas();

				for (int i = 0; i < listConsulta.size(); i++) {

					contSS += listConsulta.get(i).getCapacidad();

					int count = i;
					count++;

					resultado += "\n " + (count) +") " +  listConsulta.get(i).toString();

				}
			}

			if(opcion == 1) {	
				//Se pide la lista de terapias que existen
				opcionSS = "TERAPIA";

				List<Terapia> listTerapia = epsandes.darTerapias();

				for (int i = 0; i < listTerapia.size(); i++) {

					contSS += listTerapia.get(i).getCapacidad();

					int count = i;
					count++;

					resultado += "\n " + (count) +") " +  listTerapia.get(i).toString();
				}

			}

			if(opcion == 2) {	
				//Se pide la lista de procedimientos que existen
				opcionSS = "PROCEDIMIENTO_ESP";

				List<ProcedimientoEsp> listProcedimientoEsp = epsandes.darProcedimientosEsp();

				for (int i = 0; i < listProcedimientoEsp.size(); i++) {

					contSS += listProcedimientoEsp.get(i).getCapacidad();

					int count = i;
					count++;

					resultado += "\n " + (count) +") " +  listProcedimientoEsp.get(i).toString();
				}

			}

			if(opcion == 3) {	
				//Se pide la lista de hospitalizacion que existen
				opcionSS = "HOSPITALIZACION";

				List<Hospitalizacion> listHospitalizacion = epsandes.darHospitalizaciones();

				for (int i = 0; i < listHospitalizacion.size(); i++) {

					contSS += listHospitalizacion.get(i).getCapacidad();

					int count = i;
					count++;

					resultado += "\n " + (count) +") " +  listHospitalizacion.get(i).toString();
				}

			}	

			panelDatos.actualizarInterfaz("Cantidad del "+ opcionSS +" es : " + contSS  + "\n" + resultado);



			JOptionPane.showMessageDialog(this, "Cantidad del "+ opcionSS +" es : " + contSS);

			String cantidadSS = JOptionPane.showInputDialog(this, "Cuantas "+ opcionSS +" requiere para la campania de prevencion (num de ss diario) ?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
			int cantidadSS2 = Integer.parseInt(cantidadSS);

			contSS *= numDias2;

			//Se verifica que la cantidad del ss solicitado no supere a la cantidad existente
			if( contSS <= cantidadSS2) {

				JOptionPane.showMessageDialog(this, "No se pueden separar mas o igual cantidad de "+ opcionSS +" para la campania de prevencion");
				throw new Exception("No se pueden separar mas o igual cantidad de "+ opcionSS +" para la campania de prevencion");

			}

			//Se elige cada consulta que se desea agregar

			while(cantidadSS2 > 0) {

				String idSS = JOptionPane.showInputDialog(this, "Id del ss que desea agregar a su campania?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);

				String capacidadSS = JOptionPane.showInputDialog(this, "Capacidad que desea obtener de este ss (recuerde que esta capacidad sera por dia) ?", "Adicionar Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
				int capacidadSS2 = Integer.parseInt(capacidadSS);

				if( !(cantidadSS2 - capacidadSS2 < 0) ) {

					System.out.println("faltan " + capacidadSS2 + " por agregar");

					cantidadSS2 -= capacidadSS2;

					if(opcionSS.equals("CONSULTA")) {

						listaIdConsulta.add(idSS);

					} else if(opcionSS.equals("TERAPIA")) {

						listaIdTerapia.add(idSS);

					} else if(opcionSS.equals("PROCEDIMIENTO_ESP")) {

						listaIdProcedimiento.add(idSS);

					} else if(opcionSS.equals("HOSPITALIZACION")) {

						listaIdHospitalizacion.add(idSS);

					}


				} else {

					JOptionPane.showConfirmDialog(this, "No puede exceder la cantidad que solicito del ss");

				}


			}

			respuestaMas = JOptionPane.showConfirmDialog(this, "Se va a agregar mas servicios de salud a la campania? ");

		}


		//Se agrega la campania de prevencion

		try {

			VOCampaniaPrevencion campania = epsandes.registrarCampaniaPrevencion(localizacion, fechaInicio, fechaFin, idEPS2);



			//Se agrega a la tabla campania ss los ss asociadas a la campania

			if(listaIdConsulta.size() != 0) {

				for(int i = 0; i < listaIdConsulta.size(); i++) {

					epsandes.registrarCampaniaConsulta(campania.getId(), Long.parseLong(listaIdConsulta.get(i)), fechaInicioSS, fechaFinSS, "SI");
					epsandes.cambiarReservaConsulta(Long.parseLong(listaIdConsulta.get(i)));

				}

			}  

			if(listaIdTerapia.size() != 0) {

				for(int i = 0; i < listaIdTerapia.size(); i++) {

					epsandes.registrarCampaniaTerapia(campania.getId(), Long.parseLong(listaIdTerapia.get(i)), fechaInicioSS, fechaFinSS, "SI");
					epsandes.cambiarReservaTerapia(Long.parseLong(listaIdTerapia.get(i)));
					
				}

			} 

			if(listaIdProcedimiento.size() != 0) {

				for(int i = 0; i < listaIdProcedimiento.size(); i++) {

					epsandes.registrarCampaniaProcedimiento(campania.getId(), Long.parseLong(listaIdProcedimiento.get(i)), fechaInicio, fechaFin, "SI");
					epsandes.cambiarReservaProcedimiento(Long.parseLong(listaIdProcedimiento.get(i)));
					
				}

			} 
			if(listaIdHospitalizacion.size() != 0) {

				for(int i = 0; i < listaIdHospitalizacion.size(); i++) {

					epsandes.registrarCampaniaHospitalizacion(campania.getId(), Long.parseLong(listaIdHospitalizacion.get(i)), fechaInicio, fechaFin, "SI");
					epsandes.cambiarReservaHospitalizacion(Long.parseLong(listaIdHospitalizacion.get(i)));
					
				}

			}

			//Se registra en consola
			String result = "En adicionarCampaniaPrevencion\n\n";
			result += "Campania prevencion registrada exitosamente";
			result += "\n Operaci�n terminada";
			panelDatos.actualizarInterfaz(result);



		} catch (Exception e) {
			// TODO: handle exception
			//				e.printStackTrace();
			String res = generarMensajeError(e);
			panelDatos.actualizarInterfaz(res);

		}


	}
	
	
	
	public void desreservarSSCampania()
	{
		try 
		{
			
			String idCampania = JOptionPane.showInputDialog(this, "Id de la campania?", "Desereservar ss de Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
			long idCampania2 = Long.parseLong(idCampania);
			
			Object[] options1 = {"Consulta", "Terapia", "Procedimiento Especial", "Hospitalizacion"};
			int opcion = JOptionPane.showOptionDialog(null,
					"Elige el tipo de servicio que desea desreservar",
					"Desereservar ss de Campania Prevencion",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					options1,
					null);
			
			
			if(opcion == 0) {
				
				String idConsulta = JOptionPane.showInputDialog(this, "Id de la consulta que desea desreservar?", "Desereservar ss de Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
				long idConsulta2 = Long.parseLong(idConsulta);
				
				int eliminar = JOptionPane.showConfirmDialog(this, "Desea eliminar el ss asociado a la campania?");
				
				epsandes.eliminarCampaniaConsultaPorId(idCampania2, idConsulta2, eliminar);
				epsandes.cambiarReservaConsulta(idConsulta2);
				
			} else if(opcion == 1) {
				
				String idTerapia = JOptionPane.showInputDialog(this, "Id de la terapia que desea desreservar?", "Desereservar ss de Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
				long idTerapia2 = Long.parseLong(idTerapia);
				
				int eliminar = JOptionPane.showConfirmDialog(this, "Desea eliminar el ss asociado a la campania?");
				
				epsandes.eliminarCampaniaTerapiaPorId(idCampania2, idTerapia2, eliminar);
				epsandes.cambiarReservaTerapia(idTerapia2);
				
			} else if(opcion == 2) {
				
				String idProcedimiento = JOptionPane.showInputDialog(this, "Id del procedimiento especial que desea desreservar?", "Desereservar ss de Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
				long idProcedimiento2 = Long.parseLong(idProcedimiento);
				
				int eliminar = JOptionPane.showConfirmDialog(this, "Desea eliminar el ss asociado a la campania?");
				
				epsandes.eliminarCampaniaProcedimientoPorId(idCampania2, idProcedimiento2, eliminar);
				epsandes.cambiarReservaProcedimiento(idProcedimiento2);
				
			} else if(opcion == 3) {
				
				String idHospitalizacion = JOptionPane.showInputDialog(this, "Id del hospitalizacion que desea desreservar?", "Desereservar ss de Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
				long idHospitalizacion2 = Long.parseLong(idHospitalizacion);
				
				int eliminar = JOptionPane.showConfirmDialog(this, "Desea eliminar el ss asociado a la campania?");
				
				epsandes.eliminarCampaniaHospitalizacionPorId(idCampania2, idHospitalizacion2, eliminar);
				epsandes.cambiarReservaHospitalizacion(idHospitalizacion2);
				
			}

			
				String resultado = "En desreservarSSCampania\n\n";
				resultado += "SS eliminado de la campania exitosamente";
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);

		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	
	
	public void reaperturaServicio() {
		
		
		try {
			
			String idCampania = JOptionPane.showInputDialog(this, "Id de la campania?", "Recuperar ss de Campania Prevencion", JOptionPane.QUESTION_MESSAGE);
			long idCampania2 = Long.parseLong(idCampania);
			
			Object[] options1 = {"Consulta", "Terapia", "Procedimiento Especial", "Hospitalizacion"};
			int opcion = JOptionPane.showOptionDialog(null,
					"Elige el tipo de servicio que desea realizar reapertura",
					"Reapertura ss de Campania Prevencion",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					options1,
					null);
			
			
			if(opcion == 0) {
				
				String idConsulta = JOptionPane.showInputDialog(this, "Id de la consulta reapertura?", "Reapertura ss", JOptionPane.QUESTION_MESSAGE);
				long idConsulta2 = Long.parseLong(idConsulta);
				
				epsandes.reaperturaCampaniaConsulta(idCampania2, idConsulta2);
				
				
			} 
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}


	/* ****************************************************************
	 * 			M�todos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecuci�n
	 */
	public void limpiarLogEpsAndes ()
	{
		// Ejecuci�n de la operaci�n y recolecci�n de los resultados
		boolean resp = limpiarArchivo ("epsandes.log");

		// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
		String resultado = "\n\n************ Limpiando el log de epsandes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecuci�n
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecuci�n de la operaci�n y recolecci�n de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el n�mero de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecuci�n de la demo y recolecci�n de los resultados
			long eliminados [] = epsandes.limpiarEPSAndes();

			// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";

			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Muestra la presentaci�n general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}

	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}

	/**
	 * Muestra el script de creaci�n de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}

	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}

	/**
	 * Muestra la documentaci�n Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}

	/**
	 * Muestra la informaci�n acerca del desarrollo de esta apicaci�n
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogot�	- Colombia)\n";
		resultado += " * Departamento	de	Ingenier�a	de	Sistemas	y	Computaci�n\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versi�n 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germ�n Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jim�nez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}


	/* ****************************************************************
	 * 			M�todos privados para la presentaci�n de resultados y otras operaciones
	 *****************************************************************/
	//    /**
	//     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una l�nea por cada tipo de bebida
	//     * @param lista - La lista con los tipos de bebida
	//     * @return La cadena con una l�ea para cada tipo de bebida recibido
	//     */
	//    private String listarTiposBebida(List<VOTipoBebida> lista) 
	//    {
	//    	String resp = "Los tipos de bebida existentes son:\n";
	//    	int i = 1;
	//        for (VOTipoBebida tb : lista)
	//        {
	//        	resp += i++ + ". " + tb.toString() + "\n";
	//        }
	//        return resp;
	//	}

	/**
	 * Genera una cadena de caracteres con la descripci�n de la excepcion e, haciendo �nfasis en las excepcionsde JDO
	 * @param e - La excepci�n recibida
	 * @return La descripci�n de la excepci�n, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicaci�n
	 * @param e - La excepci�n generada
	 * @return La cadena con la informaci�n de la excepci�n y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecuci�n\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para m�s detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como par�metro con la aplicaci�n por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			M�todos de la Interacci�n
	 *****************************************************************/
	/**
	 * M�todo para la ejecuci�n de los eventos que enlazan el men� con los m�todos de negocio
	 * Invoca al m�todo correspondiente seg�n el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
		try 
		{
			Method req = InterfazEpsAndesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este m�todo ejecuta la aplicaci�n, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por l�nea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazEpsAndesApp interfaz = new InterfazEpsAndesApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
}

