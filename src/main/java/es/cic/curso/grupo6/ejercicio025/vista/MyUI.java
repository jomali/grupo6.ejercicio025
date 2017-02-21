package es.cic.curso.grupo6.ejercicio025.vista;

import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.ContextLoader;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorInventario;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorTienda;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorVentas;

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
	private static final long serialVersionUID = -9188348155047637473L;

	static final String VISTA_INVENTARIO = "inventario";
	static final String VISTA_PRODUCTO = "productos";

	/** Gestiona una colección de implementaciones de <code>View</code>. */
	Navigator navegador;

	private ServicioGestorInventario servicioGestorInventario;
	private ServicioGestorTienda servicioGestorTienda;
	private ServicioGestorVentas servicioGestorVentas;
	
	private Almacen almacen;
	private Almacen tienda;

	@Override
	protected void init(VaadinRequest request) {
		servicioGestorInventario = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorInventario.class);
		servicioGestorTienda = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorTienda.class);
		servicioGestorVentas = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorVentas.class);
		almacen = new Almacen();
		almacen.setCapacidad(1000);
		tienda = new Almacen();
		tienda.setCapacidad(100);
		servicioGestorTienda.agregaAlmacen(almacen);
		servicioGestorTienda.agregaAlmacen(tienda);
		cargaBD();

		getPage().setTitle("Badulaque");

		// Crea el navegador para controlar las vistas:
		navegador = new Navigator(this, this);

		// Crea y registra las vistas:
		navegador.addView("", new VistaTienda(navegador, servicioGestorTienda, servicioGestorVentas));
		navegador.addView(VISTA_INVENTARIO, new VistaInventario(navegador, almacen, tienda, servicioGestorInventario));
		navegador.addView(VISTA_PRODUCTO, new VistaProductos(navegador, servicioGestorTienda));
	}
	
	private void cargaBD() {
		servicioGestorTienda.agregaProducto("El guardián entre el centeno", 12.5F);
		servicioGestorTienda.agregaProducto("Jerusalem", 25.0F);
		servicioGestorTienda.agregaProducto("Nords", 20.0F);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -2502393197016663089L;
	}

}
