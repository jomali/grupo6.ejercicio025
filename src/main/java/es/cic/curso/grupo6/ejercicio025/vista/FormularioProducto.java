package es.cic.curso.grupo6.ejercicio025.vista;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

public class FormularioProducto extends FormLayout {
	private static final long serialVersionUID = 7300330890055337740L;

	@PropertyId("nombre")
	protected TextField nombre;

	@PropertyId("precio")
	protected TextField precio;
	
	private VerticalLayout padre;

	public FormularioProducto(VistaProductos padre) {
		this.padre = padre;
		
		
		// final HorizontalLayout horizontal= new HorizontalLayout();
		// horizontal.setSpacing(true);
		//
		// final VerticalLayout vertical1 = new VerticalLayout();
		//
		// final VerticalLayout horizontal3 = new VerticalLayout();
		//
		// vertical1.setSpacing(true);
		//
		// horizontal3.setSpacing(true);
		//
		// nombre = new TextField("Nombre: ");
		// nombre.setInputPrompt("Nombre");
		// precio = new TextField("Precio: ");
		// precio.setInputPrompt("Precio");
		//
		//
		// accion = new Button("Añadir");
		// accion.addClickListener(e -> {
		// //padre.cargaGrid(producto);
		// //padre.mostrarBotones();
		// });
		// borrar = new Button("Borrar");
		// //borrar.addClickListener(e->padre.borrarGrid(producto));
		// borrar.setIcon(FontAwesome.CLOSE);
		//
		// horizontal.addComponents(vertical1, horizontal3);
		// vertical1.addComponents(nombre,precio);
		//
		// horizontal3.addComponents(accion,borrar);
		//
		// addComponents(horizontal);
		//
		//
		// setProducto(null);
	}

	// public void setProducto(Producto producto) {
	// this.setVisible(producto != null);
	// this.producto = producto;
	//
	// if (producto != null) {
	// BeanFieldGroup.bindFieldsUnbuffered(producto, this);
	// } else {
	// BeanFieldGroup.bindFieldsUnbuffered(new Producto(), this);
	//
	// }
	// }
	//
	//
	// public void ocultarBotones(){
	// accion.setCaption("Nuevo Producto");
	// accion.setIcon(FontAwesome.PLUS);
	// borrar.setVisible(false);
	// }
	// public void mostrarBotones(){
	// accion.setVisible(true);
	// accion.setCaption("Actualizar Estado");
	// accion.setIcon(FontAwesome.REFRESH);
	// borrar.setVisible(true);
	// }

}
