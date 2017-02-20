package es.cic.curso.grupo6.ejercicio025.vista;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class VistaTienda extends VerticalLayout implements View {
	private static final long serialVersionUID = -4756028504271319024L;

	public VistaTienda(Navigator navegador) {
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Label label = new Label("Hola mundo.");
		label.setContentMode(ContentMode.HTML);
		
		addComponent(label);
	}

}
