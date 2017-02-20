package es.cic.curso.grupo5.ejercicio024.vista;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;
import es.cic.curso.grupo5.ejercicio024.modelo.Venta;

public class HistorialVentasForm extends FormLayout{
	private static final long serialVersionUID = -2788131038611367600L;
	
	@PropertyId("entradas")
	protected TextField entradas;
	@PropertyId("id_sesion")
	protected TextField sesion;
	
	private VerticalLayout padre;
	private Button vender;
	private Button cancelar;
	
	private Venta venta;
	
	public HistorialVentasForm(VistaHistorialVentas padre){
		
		this.padre = padre;
		
		final VerticalLayout vertical = new VerticalLayout();
		final HorizontalLayout horizontal = new HorizontalLayout();
		
		vertical.setSpacing(true);
		horizontal.setSpacing(true);
		
		entradas = new TextField("Entradas: ");
		entradas.setInputPrompt("Número de entradas");
		sesion = new TextField("Sesión: ");
		sesion.setInputPrompt("Selecciona sesión");
		
		vender = new Button("Vender");
		cancelar = new Button("Cancelar");
		
		vertical.addComponents(entradas,sesion);
		horizontal.addComponents(vertical, vender, cancelar);
		

	}
	
	public void setVenta(Venta venta){
		this.setVisible(venta != null);
		this.venta = venta;

		if (venta != null) {
			BeanFieldGroup.bindFieldsUnbuffered(venta, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Venta(), this);

		}
	}


}
