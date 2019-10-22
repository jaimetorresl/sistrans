package uniandes.isis2304.epsandes.interfazApp;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.epsandes.negocio.EPSAndes;
import uniandes.isis2304.epsandes.negocio.IPS;
import uniandes.isis2304.epsandes.negocio.VOConsulta;
import uniandes.isis2304.epsandes.negocio.VOEPS;
import uniandes.isis2304.epsandes.negocio.VOHospitalizacion;
import uniandes.isis2304.epsandes.negocio.VOIPS;
import uniandes.isis2304.epsandes.negocio.VOIPSMedico;
import uniandes.isis2304.epsandes.negocio.VOMedico;
import uniandes.isis2304.epsandes.negocio.VOProcedimientoEsp;
import uniandes.isis2304.epsandes.negocio.VORecepcionistaIPS;
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

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		epsandes = new EPSAndes (tableConfig);

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
			ancho = 500;
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

