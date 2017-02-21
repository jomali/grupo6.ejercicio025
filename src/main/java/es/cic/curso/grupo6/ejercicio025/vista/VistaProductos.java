package es.cic.curso.grupo6.ejercicio025.vista;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorTienda;

public class VistaProductos extends VerticalLayout implements View {
	private static final long serialVersionUID = -2185019071795535344L;

	/** Referencia a la lógica de gestión de productos en servidor. */
	private ServicioGestorTienda servicioGestorProductos;

	/** <em>Grid</em> con los productos registrados en el sistema. */
	private Grid gridProductos;

	@SuppressWarnings("serial")
	public VistaProductos(Navigator navegador, ServicioGestorTienda servicioGestorProductos) {
		this.servicioGestorProductos = servicioGestorProductos;

		// Navegación entre las vistas de la aplicación:
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		menuNavegacion.addItem("Tienda", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo("");
			}
		});
		menuNavegacion.addItem("Inventario", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_INVENTARIO);
			}
		});
		MenuItem menuItemVistaProductos = menuNavegacion.addItem("Productos", null);
		menuItemVistaProductos.setEnabled(false);
		addComponent(menuNavegacion);

		addComponent(creaLayoutProductos());
		addComponent(creaButtons());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGrid();
	}

	private VerticalLayout creaLayoutProductos() {
		VerticalLayout resultado = new VerticalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);
		resultado.setSizeFull();

		gridProductos = new Grid();
		gridProductos.setColumns("id", "nombre", "precio");
		gridProductos.setSizeFull();
		gridProductos.setSelectionMode(SelectionMode.SINGLE);
		resultado.addComponent(gridProductos);

		Button botonAnnadir = new Button();
		botonAnnadir.setCaption("Añadir producto");
		botonAnnadir.setIcon(FontAwesome.PLUS_CIRCLE);
		resultado.addComponent(botonAnnadir);

		return resultado;
	}
	
	private HorizontalLayout creaButtons() {
		HorizontalLayout resultado = new HorizontalLayout();
		resultado.setMargin(true);
		resultado.setSpacing(true);
		
		
		Button botonAnnadir = new Button();
		botonAnnadir.setCaption("Añadir producto");
		botonAnnadir.setIcon(FontAwesome.PLUS_CIRCLE);
		resultado.addComponent(botonAnnadir);
		
		Button botonQuitar = new Button();
		botonQuitar.setCaption("Quitar producto");
		botonQuitar.setIcon(FontAwesome.MINUS_CIRCLE);
		resultado.addComponent(botonQuitar);
		
		Button botonImprimir = new Button();
		botonImprimir.setCaption("Imprimir Productos");
		botonImprimir.setIcon(FontAwesome.PRINT);
		resultado.addComponent(botonImprimir);
		
		
		return resultado;
	}
	
	public void cargaGrid() {
		List<Producto> productos = servicioGestorProductos.listaProductos();
		gridProductos.setContainerDataSource(new BeanItemContainer<>(Producto.class, productos));
	}

}
