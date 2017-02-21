package es.cic.curso.grupo6.ejercicio025.vista;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorInventario;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorTienda;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorVentas;

public class VistaTienda extends VerticalLayout implements View {
	private static final long serialVersionUID = -4756028504271319024L;

	private ServicioGestorTienda servicioGestorTienda;
	private ServicioGestorInventario servicioGestorInventario;
	private ServicioGestorVentas servicioGestorVentas;
	private Almacen almacen;
	private Almacen tienda;

	private FormularioVenta detalle;

	/** <em>Grid</em> con los productos registrados en el sistema. */
	private Grid gridProductos;

	private Grid gridInventario;

	@SuppressWarnings("serial")
	public VistaTienda(Navigator navegador, ServicioGestorTienda servicioGestorTienda,
			ServicioGestorInventario servicioGestorInventario, ServicioGestorVentas servicioGestorVentas,
			Almacen almacen, Almacen tienda) {
		this.servicioGestorTienda = servicioGestorTienda;
		this.servicioGestorInventario = servicioGestorInventario;
		this.servicioGestorVentas = servicioGestorVentas;
		this.almacen = almacen;
		this.tienda = tienda;

		// Navegación entre las vistas de la aplicación:
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		MenuItem menuItemTienda = menuNavegacion.addItem("Tienda", null);
		menuItemTienda.setEnabled(false);
		menuNavegacion.addItem("Inventario", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_INVENTARIO);
			}
		});
		menuNavegacion.addItem("Productos", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_PRODUCTO);
			}
		});
		addComponent(menuNavegacion);

		addComponent(creaLayoutVenta());
		addComponent(detalle);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGridProductos();
	}

	private VerticalLayout creaLayoutVenta() {
		VerticalLayout resultado = new VerticalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);
		resultado.setSizeFull();

		gridProductos = new Grid();
		gridProductos.setCaption("Lista de productos:");
		gridProductos.setColumns("id", "nombre", "precio");
		gridProductos.setWidth(100.0F, Unit.PERCENTAGE);
		gridProductos.setHeight(300.0F, Unit.PIXELS);
		gridProductos.setSelectionMode(SelectionMode.SINGLE);
		gridProductos.setVisible(true);

		gridInventario = new Grid();
		gridInventario.setCaption("Disponibilidad:");
		gridInventario.setColumns("id", "producto", "almacen", "cantidad");
		gridInventario.setWidth(100.0F, Unit.PERCENTAGE);
		gridInventario.setHeight(150.0F, Unit.PIXELS);
		gridInventario.setSelectionMode(SelectionMode.NONE);
		gridInventario.setVisible(false);

		detalle = new FormularioVenta(this);

		gridProductos.addSelectionListener(e -> {
			Producto producto = null;
			if (!e.getSelected().isEmpty()) {
				producto = (Producto) e.getSelected().iterator().next();
				gridInventario.setVisible(true);
				cargaGridInventario(producto);
			} else {
				gridInventario.setVisible(false);
			}
			detalle.setProducto(producto);
		});

		resultado.addComponents(gridProductos, gridInventario);
		return resultado;
	}

	public void cargaGridProductos() {
		List<Producto> productos = servicioGestorTienda.listaProductos();
		gridProductos.setContainerDataSource(new BeanItemContainer<>(Producto.class, productos));
	}

	public void vendeProducto(Producto producto, int cantidad) {
		Inventario entradaTienda = servicioGestorInventario.obtenEntradaInventario(producto.getId(), tienda.getId());
		Inventario entradaAlmacen = servicioGestorInventario.obtenEntradaInventario(producto.getId(), almacen.getId());
		
		if (entradaTienda.getCantidad() + entradaAlmacen.getCantidad() < cantidad) {
			Notification.show("No hay existencias suficientes");
			return;
		}
		
		if (entradaTienda.getCantidad() >= cantidad) {
			servicioGestorVentas.vende(producto.getId(), tienda.getId(), cantidad);
			Notification.show("Vendidas: " + cantidad + " uds.");
			return;
		}
		
		int restante = cantidad - entradaTienda.getCantidad();
		servicioGestorVentas.vende(producto.getId(), tienda.getId(), entradaTienda.getCantidad());
		servicioGestorVentas.vende(producto.getId(), almacen.getId(), restante);
		Notification.show("Vendidas: " + cantidad + " uds.");
	}

	private void cargaGridInventario(Producto producto) {
		List<Inventario> entradasInventario = servicioGestorInventario.listaEntradasPorProducto(producto.getId());
		gridInventario.setContainerDataSource(new BeanItemContainer<>(Inventario.class, entradasInventario));
	}

}
