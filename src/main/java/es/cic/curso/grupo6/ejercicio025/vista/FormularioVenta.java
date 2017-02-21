package es.cic.curso.grupo6.ejercicio025.vista;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

public class FormularioVenta extends FormLayout {

	private Producto producto;

	protected TextField textFieldCantidad;

	protected Button botonVender;

	private VerticalLayout padre;

	public FormularioVenta(VerticalLayout padre) {
		this.padre = padre;

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		textFieldCantidad = new TextField("Cantidad: ");
		textFieldCantidad.setInputPrompt("0");
		botonVender = new Button("Vender");
		layout.addComponents(textFieldCantidad, botonVender);

		addComponents(layout, botonVender);

		setProducto(null);
	}

	public void setProducto(Producto producto) {
		this.setVisible(producto != null);
		this.producto = producto;

		if (producto != null) {
			BeanFieldGroup.bindFieldsUnbuffered(producto, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Producto(), this);

		}
	}

}
