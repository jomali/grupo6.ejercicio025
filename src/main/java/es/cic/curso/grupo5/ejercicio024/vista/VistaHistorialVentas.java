package es.cic.curso.grupo5.ejercicio024.vista;

import java.util.List;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;
import es.cic.curso.grupo5.ejercicio024.modelo.Venta;
import es.cic.curso.grupo5.ejercicio024.servicio.ServicioGestorTaquilla;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class VistaHistorialVentas extends VerticalLayout implements View {
	private static final long serialVersionUID = 9046017234893235091L;
	
	private Grid maestro;
	
	private HistorialVentasForm detalle;
	
	private List<Venta> listaVentas;
	
	private ServicioGestorTaquilla servicioGestorTaquilla;
	
	private Button venta;
	
	private Venta v;
	
	@PropertyId("entradas")
	protected TextField entradas;
	@PropertyId("id_sesion")
	protected TextField sesion;
		
	@SuppressWarnings("serial")
	public VistaHistorialVentas(Navigator navegador, ServicioGestorTaquilla servicioGestorTaquilla) {
		this.servicioGestorTaquilla = servicioGestorTaquilla;

		
		// Navegación entre las vistas de la aplicación:
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		menuNavegacion.addItem("Cine",new Command() {
			@Override
	        public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo("");
	        }
		});
		menuNavegacion.addItem("Películas", new Command() {
			@Override
	        public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_PELICULAS);
	        }
		});
		MenuItem menuItemVistaHistorialVentas = menuNavegacion.addItem("Ventas", null);
		menuItemVistaHistorialVentas.setEnabled(false);
		addComponent(menuNavegacion);
		
		addComponent(creaTabVentas());
	}

	
	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Vista HISTORIAL");
	}
	
	private VerticalLayout creaTabVentas(){
		final VerticalLayout result = new VerticalLayout();
		result.setMargin(true);
		result.setSpacing(true);
		result.setSizeFull();
	     
        maestro = new Grid();
        maestro.setColumns("nEntradas","sesion", "importe");
        maestro.setSizeFull();
        maestro.setSelectionMode(SelectionMode.SINGLE);
        
        HorizontalLayout layoutVenta = new HorizontalLayout();
        layoutVenta.setMargin(false);
        layoutVenta.setSpacing(true);
        
        entradas = new TextField("Entradas: ");
		entradas.setInputPrompt("Número de entradas");
		sesion = new TextField("Sesión: ");
		sesion.setInputPrompt("Selecciona sesión");
        
        venta = new Button("Vender");
        venta.setIcon(FontAwesome.EURO);

		venta.setVisible(true);


        venta.addClickListener(e->{
        	int cantidad = Integer.parseInt(entradas.getValue());
        	Long idSesion = Long.parseLong(sesion.getValue());
        	servicioGestorTaquilla.vendeEntradas(idSesion, cantidad);
        	List<Venta> ventas = servicioGestorTaquilla.listaVentas();
        	maestro.setContainerDataSource(new BeanItemContainer<>(Venta.class, ventas));
        	
        	
        });
        layoutVenta.addComponents(sesion, entradas);
        
        result.addComponents(maestro, layoutVenta, venta);
        result.setMargin(true);
        result.setSpacing(true);
        result.setWidth("100%");
        
        
        
        return result;
	}
	
}
