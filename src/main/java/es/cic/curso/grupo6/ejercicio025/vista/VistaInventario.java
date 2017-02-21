package es.cic.curso.grupo6.ejercicio025.vista;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorInventario;
import es.cic.curso.grupo6.ejercicio025.vista.MyUI;

public class VistaInventario extends VerticalLayout implements View {
	private static final long serialVersionUID = -3554081767202657813L;

	private ServicioGestorInventario servicioGestorInventario;

	private Almacen almacen;
	private Almacen tienda;

	private Inventario inventario;

	private Grid gridAlmacen;
	private Grid gridTienda;

	private Button moverTienda;

	private TextField movemos;

	@SuppressWarnings("serial")
	public VistaInventario(Navigator navegador, Almacen almacen, Almacen tienda,
			ServicioGestorInventario servicioGestorInventario) {
		this.almacen = almacen;
		this.tienda = tienda;
		this.servicioGestorInventario = servicioGestorInventario;

		// Navegación entre las vistas de la aplicación:
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		menuNavegacion.setHeight(100.0F, Unit.PERCENTAGE);
		menuNavegacion.addItem("Tienda", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo("");
			}
		});
		MenuItem menuItemVistaInventario = menuNavegacion.addItem("Inventario", null);
		menuItemVistaInventario.setEnabled(false);

		menuNavegacion.addItem("Productos", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_PRODUCTO);
			}
		});
		addComponent(menuNavegacion);
		addComponent(creaLayoutAlmacen());
		addComponent(creaButtons());
		addComponent(creaLayoutTienda());
		// addComponent(detalle);

		// Creación de Grids
		// HorizontalLayout contentLayout = new HorizontalLayout();
		// contentLayout.setMargin(true);
		// contentLayout.setSpacing(true);
		// contentLayout.addComponent(gridAlmacen);
		// addComponent(contentLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGrid();
	}

	private VerticalLayout creaLayoutAlmacen() {
		VerticalLayout resultado = new VerticalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);
		resultado.setSizeFull();
		Label label = new Label("Inventario Almacén");
		gridAlmacen = new Grid();
		gridAlmacen.setColumns("id", "producto", "cantidad");
		gridAlmacen.setSizeFull();
		gridAlmacen.setSelectionMode(SelectionMode.SINGLE);

		gridAlmacen.addSelectionListener(e -> {
			Producto producto = null;
			if (!e.getSelected().isEmpty()) {
				producto = (Producto) e.getSelected().iterator().next();
				moverTienda.setEnabled(true);
				// servicioGestorInventario.modificaCantidadProductos(idProducto,
				// idAlmacen, cantidadNumerica);
			} else {
				moverTienda.setEnabled(false);
			}
			
			// detalle.setProducto(producto);
		});
		resultado.addComponent(label);
		resultado.addComponent(gridAlmacen);

		return resultado;
	}

	private HorizontalLayout creaButtons() {
		HorizontalLayout resultado = new HorizontalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);

		moverTienda = new Button();
		moverTienda.setCaption("Añadir producto a Tienda");
		moverTienda.setIcon(FontAwesome.PLUS_CIRCLE);
		resultado.addComponent(moverTienda);
		moverTienda.setVisible(true);
		moverTienda.setEnabled(false);
		moverTienda.addClickListener(e -> {
			try {
				if (Integer.valueOf(movemos.getValue()) > 0) {
					String cantidad = movemos.getValue();
					int cantidadNumerica = Integer.parseInt(cantidad);

					// servicioGestorInventario.modificaCantidadProductos(idProducto,cantidadNumerica
					// );
					// servicioGestorInventario.modificaCantidadProductos(idAlmacen,cantidadNumerica
					// );
				} else {
					Notification.show("Te has equivocado, inténtalo otra vez.");
				}

			} catch (Exception o) {
				Notification.show("De verdad? Inténtalo otra vez.");
			}
		});

		movemos = new TextField();
		movemos.setVisible(true);
		resultado.addComponent(movemos);

		return resultado;
	}

	private VerticalLayout creaLayoutTienda() {
		VerticalLayout resultado = new VerticalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);
		resultado.setSizeFull();
		Label label = new Label("Inventario Tienda");
		gridTienda = new Grid();
		gridTienda.setColumns("id", "almacen", "cantidad");
		gridTienda.setSizeFull();
		gridTienda.setSelectionMode(SelectionMode.SINGLE);

		gridAlmacen.addSelectionListener(e -> {
			Producto producto = null;
			if (!e.getSelected().isEmpty()) {
				producto = (Producto) e.getSelected().iterator().next();
				// servicioGestorInventario.modificaCantidadProductos(idProducto,
				// idAlmacen, cantidadNumerica);
			}
			// detalle.setProducto(producto);
		});

		resultado.addComponent(label);
		resultado.addComponent(gridTienda);

		return resultado;
	}

	public void cargaGrid() {
		List<Inventario> productoAlmacen = servicioGestorInventario.listaEntradasPorAlmacen(almacen.getId());
		gridAlmacen.setContainerDataSource(new BeanItemContainer<>(Inventario.class, productoAlmacen));

		List<Inventario> productoTienda = servicioGestorInventario.listaEntradasPorAlmacen(tienda.getId());
		gridTienda.setContainerDataSource(new BeanItemContainer<>(Inventario.class, productoTienda));
	}
}
