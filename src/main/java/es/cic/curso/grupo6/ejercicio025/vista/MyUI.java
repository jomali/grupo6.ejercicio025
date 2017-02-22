package es.cic.curso.grupo6.ejercicio025.vista;

import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.ContextLoader;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import es.cic.curso.grupo6.ejercicio025.dto.ProductoConverter;
import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
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
	private ProductoConverter productoConverter;
	
	private Almacen almacen;
	private Almacen tienda;

	@Override
	protected void init(VaadinRequest request) {
		servicioGestorInventario = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorInventario.class);
		servicioGestorTienda = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorTienda.class);
		servicioGestorVentas = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorVentas.class);
		productoConverter = ContextLoader.getCurrentWebApplicationContext().getBean(ProductoConverter.class);
		
		almacen = new Almacen();
		almacen.setNombre("Almacén principal");
		almacen.setCapacidad(1000);
		tienda = new Almacen();
		tienda.setNombre("Tienda");
		tienda.setCapacidad(100);
		servicioGestorTienda.agregaAlmacen(almacen);
		servicioGestorTienda.agregaAlmacen(tienda);
		cargaBD();

		getPage().setTitle("Badulaque");

		// Crea el navegador para controlar las vistas:
		navegador = new Navigator(this, this);

		// Crea y registra las vistas:
		navegador.addView("", new VistaTienda(navegador, servicioGestorTienda, servicioGestorInventario,
				servicioGestorVentas, productoConverter, almacen, tienda));
		navegador.addView(VISTA_INVENTARIO,
				new VistaInventario(navegador, almacen, tienda, servicioGestorInventario));
		navegador.addView(VISTA_PRODUCTO, new VistaProductos(navegador, servicioGestorTienda));
	}

	private void cargaBD() {
		if (!servicioGestorTienda.listaProductos().isEmpty())
			return;
		Producto p1 = new Producto();
		p1.setNombre("Galletas Príncipe");
		p1.setPrecio(2.5F);
		servicioGestorTienda.agregaProducto(p1);

		Producto p2 = new Producto();
		p2.setNombre("Café Fortaleza");
		p2.setPrecio(2.10F);
		servicioGestorTienda.agregaProducto(p2);

		Producto p3 = new Producto();
		p3.setNombre("Espárragos Cojonudos");
		p3.setPrecio(8.90F);
		servicioGestorTienda.agregaProducto(p3);

		Producto p4 = new Producto();
		p4.setNombre("Dulce de Leche Havanna");
		p4.setPrecio(4.90F);
		servicioGestorTienda.agregaProducto(p4);

		Producto p5 = new Producto();
		p5.setNombre("Donuts Chocolate");
		p5.setPrecio(1.99F);
		servicioGestorTienda.agregaProducto(p5);

		servicioGestorInventario.estableceCantidadProductos(p1.getId(), almacen.getId(), 100);
		servicioGestorInventario.estableceCantidadProductos(p1.getId(), tienda.getId(), 8);
		servicioGestorInventario.estableceCantidadProductos(p2.getId(), almacen.getId(), 80);
		servicioGestorInventario.estableceCantidadProductos(p2.getId(), tienda.getId(), 18);
		servicioGestorInventario.estableceCantidadProductos(p3.getId(), almacen.getId(), 50);
		servicioGestorInventario.estableceCantidadProductos(p3.getId(), tienda.getId(), 20);
		servicioGestorInventario.estableceCantidadProductos(p4.getId(), almacen.getId(), 0);
		servicioGestorInventario.estableceCantidadProductos(p4.getId(), tienda.getId(), 11);
		servicioGestorInventario.estableceCantidadProductos(p5.getId(), almacen.getId(), 60);
		servicioGestorInventario.estableceCantidadProductos(p5.getId(), tienda.getId(), 30);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -2502393197016663089L;
	}

}
