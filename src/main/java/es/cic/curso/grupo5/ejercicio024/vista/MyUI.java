package es.cic.curso.grupo5.ejercicio024.vista;

import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.ContextLoader;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import es.cic.curso.grupo5.ejercicio024.servicio.ServicioGestorCine;
import es.cic.curso.grupo5.ejercicio024.servicio.ServicioGestorTaquilla;
import es.cic.curso.grupo5.ejercicio024.servicio.ServicioPelicula;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	private static final long serialVersionUID = -1443407760685264021L;
	
	static final String VISTA_PELICULAS = "peliculas";
	static final String VISTA_HISTORIAL_VENTAS = "ventas";
	
	/** Gestiona una colección de implementaciones de <code>View</code>. */
	Navigator navegador;
	
	private ServicioGestorCine servicioGestorCine;
	private ServicioGestorTaquilla servicioGestorTaquilla;
	private ServicioPelicula servicioPelicula;
	
	@Override
	protected void init(VaadinRequest request) {
		servicioGestorCine = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorCine.class);
		servicioGestorTaquilla = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorTaquilla.class);
		servicioPelicula = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioPelicula.class);
		
		getPage().setTitle("Cine");
		
		// Crea el navegador para controlar las vistas:
		navegador = new Navigator(this, this);
				
		// Crea y registra las vistas:
		navegador.addView("", new VistaCine(navegador, servicioGestorCine, servicioGestorTaquilla));
		navegador.addView(VISTA_HISTORIAL_VENTAS, new VistaHistorialVentas(navegador, servicioGestorTaquilla));
		navegador.addView(VISTA_PELICULAS, new VistaPeliculas(navegador, servicioPelicula));
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -2502393197016663089L;
	}
	
}