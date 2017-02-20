package es.cic.curso.grupo6.ejercicio025.vista;

import java.util.List;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio025.modelo.Venta;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorVentas;

public class VistaTienda extends VerticalLayout implements View {
	private static final long serialVersionUID = -4756028504271319024L;

	private Grid maestro;

	/* private ProductoForm detalle; */

	private List<Venta> listaVentas;
	ServicioGestorVentas servicioGestorVentas;

	private Button addBtn;
	private Button cancelar;


	public VistaTienda(Navigator navegador, ServicioGestorVentas servicioGestorVentas) {
		this.servicioGestorVentas = servicioGestorVentas;

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
		
		HorizontalLayout contentLayout = new HorizontalLayout();
		contentLayout.setMargin(true);
		contentLayout.setSpacing(true);
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
		Label label = new Label("Hola mundo.");
		label.setContentMode(ContentMode.HTML);
		
		addComponent(label);
	}

}
