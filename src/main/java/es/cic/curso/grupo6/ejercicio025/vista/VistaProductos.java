package es.cic.curso.grupo6.ejercicio025.vista;

import java.util.List;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorProductos;

public class VistaProductos extends VerticalLayout implements View {
	private static final long serialVersionUID = -2185019071795535344L;

	private Grid maestro;

	private ProductoForm detalle;

	private List<Producto> listaProductos;

	private Button addBtn;
	private Button cancelar;

	private ServicioGestorProductos servicioGestorProducto;

	@SuppressWarnings("serial")
	public VistaProductos(Navigator navegador, ServicioGestorProductos servicioGestorProducto) {
		this.servicioGestorProducto = servicioGestorProducto;

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

	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Vista PRODUCTOS");
	}

	
//	 private VerticalLayout creaTabProductos() { final VerticalLayout result =
//	 new VerticalLayout(); result.setMargin(true); result.setSpacing(true);
//	 result.setSizeFull();
//	 
//	 maestro = new Grid(); maestro.setColumns("nombre", "precio", "cantidad");
//	 maestro.setSizeFull();
//	 
//	 maestro.setFrozenColumnCount(1);
//	 maestro.setSelectionMode(SelectionMode.SINGLE);
//	 
//	 addBtn = new Button("Añadir producto"); cancelar = new
//	 Button("Cancelar"); cancelar.setVisible(false);
//	 
//	 maestro.addSelectionListener(e -> { Producto p = null; if
//	 (!e.getSelected().isEmpty()) { p = (Producto)
//	 e.getSelected().iterator().next(); addBtn.setVisible(false); } else {
//	 addBtn.setVisible(true); } detalle.mostrarBotones();
//	 detalle.setProducto(p); }); detalle = new ProductoForm(this);
//	 
//	 cancelar.addClickListener(e -> { Producto p = null;
//	 detalle.setProducto(p); addBtn.setVisible(true);
//	 cancelar.setVisible(false); });
//	 
//	 addBtn.addClickListener(e -> { addBtn.setVisible(false);
//	 detalle.ocultarBotones(); cancelar.setVisible(true); aniadirGrid(); });
//	 
//	 addBtn.setIcon(FontAwesome.FILM);
//	 
//	 result.addComponents(maestro, detalle, addBtn, cancelar);
//	 result.setMargin(true); result.setSpacing(true); result.setWidth("100%");
//	 
//	 return result; }
	

	
//	 public void cargaGrid(Producto item) { if (item != null) {
//	 servicioGestorProducto.actualizarProducto(item); }
//	 
//	 listaProductos = servicioGestorProducto.obtenerProducto();
//	 
//	 maestro.setContainerDataSource(new BeanItemContainer<>(Producto.class,
//	 listaProductos)); detalle.setProducto(null); }
	 
//	 public void aniadirGrid() { Producto p = new Producto();
//	 detalle.setProducto(p); maestro.setContainerDataSource(new
//	 BeanItemContainer<>(Producto.class, listaProductos));
//	 servicioGestorProducto.aniadirProducto(p); }
	 
//	 public void mostrarBotones() { addBtn.setVisible(true);
//	 cancelar.setVisible(false); }
//	 
//	public void borrarGrid(Producto item) {
//		listaProductos.remove(item);
//		maestro.setContainerDataSource(new BeanItemContainer<>(Producto.class, listaProductos));
//		Long itemId = item.getId();
//		servicioGestorProducto.borrarProducto(itemId);
//	}
	
}
