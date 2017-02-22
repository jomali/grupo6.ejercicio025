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
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorTienda;
import es.cic.curso.grupo6.ejercicio025.vista.MyUI;

public class VistaInventario extends VerticalLayout implements View {
	private static final long serialVersionUID = -3554081767202657813L;

	private ServicioGestorInventario servicioGestorInventario;

	private Almacen almacen;
	private Almacen tienda;

	private Inventario entradaSeleccionada;

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
				navegador.navigateTo("tienda");
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
			if (!e.getSelected().isEmpty()) {
				entradaSeleccionada = (Inventario) e.getSelected().iterator().next();
				moverTienda.setEnabled(true);
			} else {
				moverTienda.setEnabled(false);
			}
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
		moverTienda.setIcon(FontAwesome.ARROW_DOWN);
		moverTienda.setVisible(true);
		moverTienda.setEnabled(false);
		moverTienda.addClickListener(e -> {
			try {
				if (Integer.valueOf(movemos.getValue()) > 0) {
					String cantidad = movemos.getValue();
					int cantidadNumerica = Integer.parseInt(cantidad);

					Producto producto = entradaSeleccionada.getProducto();
					servicioGestorInventario.modificaCantidadProductos(producto.getId(), almacen.getId(),
							-cantidadNumerica);
					servicioGestorInventario.modificaCantidadProductos(producto.getId(), tienda.getId(),
							+cantidadNumerica);
					cargaGrid();
					movemos.setValue("");
					Notification.show("Se han pasado '" + cantidadNumerica + "' del almacén a la tienda");
				} else {
					Notification.show("Te has equivocado, inténtalo otra vez.");
				}

			} catch (Exception o) {
				Notification.show("De verdad? Inténtalo otra vez.");
				movemos.setValue("");
			}
		});

		movemos = new TextField();
		movemos.setInputPrompt("Cantidad");
		movemos.setVisible(true);

		resultado.addComponents(movemos, moverTienda);
		return resultado;
	}

	private VerticalLayout creaLayoutTienda() {
		VerticalLayout resultado = new VerticalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);
		resultado.setSizeFull();
		Label label = new Label("Inventario Tienda");
		gridTienda = new Grid();
		gridTienda.setColumns("id", "producto", "cantidad");
		gridTienda.setSizeFull();
		gridTienda.setSelectionMode(SelectionMode.NONE);

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
