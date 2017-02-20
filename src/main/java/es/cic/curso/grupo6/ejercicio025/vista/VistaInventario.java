package es.cic.curso.grupo6.ejercicio025.vista;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import es.cic.curso.grupo6.ejercicio025.vista.MyUI;

public class VistaInventario extends VerticalLayout implements View {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3554081767202657813L;
	private ServicioGestorInventario servicioGestorInventario;
	
	private ServicioGestorProducto servicioGestorProducto;
	@SuppressWarnings("serial")
	public VistaInventario(Navigator navegador,ServicioGestorInventario  servicioInventario,
			ServicioGestorProducto servicioProducto) {
		
		this.servicioGestorInventario = servicioGestorInventario;
		this.servicioGestorProducto = servicioGestorProducto;
		
		// Navegación entre las vistas de la aplicación:
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		menuNavegacion.setHeight(100.0F, Unit.PERCENTAGE);
		MenuItem menuItemVistaCine = menuNavegacion.addItem("Cine", null);
		menuItemVistaCine.setEnabled(false);
		menuNavegacion.addItem("Tienda", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_TIENDA);
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
		// TODO Auto-generated method stub
		
	}

}
