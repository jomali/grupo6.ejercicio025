package es.cic.curso.grupo6.ejercicio025.vista;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorTienda;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorVentas;

public class VistaTienda extends VerticalLayout implements View {
	private static final long serialVersionUID = -4756028504271319024L;

	ServicioGestorTienda servicioGestorTienda;
	ServicioGestorVentas servicioGestorVentas;

	/** <em>Grid</em> con los productos registrados en el sistema. */
	private Grid gridProductos;

	@SuppressWarnings("serial")
	public VistaTienda(Navigator navegador, ServicioGestorTienda servicioGestorTienda,
			ServicioGestorVentas servicioGestorVentas) {
		this.servicioGestorTienda = servicioGestorTienda;
		this.servicioGestorVentas = servicioGestorVentas;

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
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGrid();
	}

	private VerticalLayout creaLayoutVenta() {
		VerticalLayout resultado = new VerticalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);
		resultado.setSizeFull();

		gridProductos = new Grid();
		gridProductos.setColumns("id", "nombre", "precio");
		gridProductos.setSizeFull();
		gridProductos.setSelectionMode(SelectionMode.SINGLE);
		resultado.addComponent(gridProductos);

		return resultado;
	}

	public void cargaGrid() {
		List<Producto> productos = servicioGestorTienda.listaProductos();
		gridProductos.setContainerDataSource(new BeanItemContainer<>(Producto.class, productos));
	}

}
