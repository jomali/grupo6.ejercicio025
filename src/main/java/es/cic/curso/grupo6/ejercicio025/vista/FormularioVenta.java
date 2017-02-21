package es.cic.curso.grupo6.ejercicio025.vista;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.servicio.ServicioGestorInventario;

public class FormularioVenta extends FormLayout {
	private static final long serialVersionUID = -2476135359521998507L;

	private Producto producto;

	protected TextField textFieldCantidad;

	protected Button botonVender;

	private VistaTienda padre;

	public FormularioVenta(VistaTienda padre) {
		this.padre = padre;

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.setMargin(true);
		Label label = new Label("Cantidad");
		textFieldCantidad = new TextField();
		textFieldCantidad.setInputPrompt("0");
		botonVender = new Button("Vender");
		botonVender.setIcon(FontAwesome.EURO);
		
		botonVender.addClickListener(e -> {
			padre.vendeProducto(producto, (int)textFieldCantidad.getConvertedValue());
			padre.cargaGridProductos();
		});
		
		horizontalLayout.addComponents(label, textFieldCantidad, botonVender);

		addComponents(horizontalLayout);

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
