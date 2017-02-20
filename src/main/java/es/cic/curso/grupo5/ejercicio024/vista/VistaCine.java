package es.cic.curso.grupo5.ejercicio024.vista;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;
import es.cic.curso.grupo5.ejercicio024.modelo.Sala;
import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;
import es.cic.curso.grupo5.ejercicio024.servicio.ServicioGestorCine;
import es.cic.curso.grupo5.ejercicio024.servicio.ServicioGestorTaquilla;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class VistaCine extends VerticalLayout implements View {
	private static final long serialVersionUID = -5390150077727891270L;

	public static final int CAPACIDAD_SALA = 250;

	private ServicioGestorCine servicioGestorCine;

	private ServicioGestorTaquilla servicioGestorTaquilla;

	private TabSheet tabsSalas;

	@SuppressWarnings("serial")
	public VistaCine(Navigator navegador, ServicioGestorCine servicioGestorCine,
			ServicioGestorTaquilla servicioGestorTaquilla) {
		this.servicioGestorCine = servicioGestorCine;
		this.servicioGestorTaquilla = servicioGestorTaquilla;

		// Navegación entre las vistas de la aplicación:
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		menuNavegacion.setHeight(100.0F, Unit.PERCENTAGE);
		MenuItem menuItemVistaCine = menuNavegacion.addItem("Cine", null);
		menuItemVistaCine.setEnabled(false);
		menuNavegacion.addItem("Películas", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_PELICULAS);
			}
		});
		menuNavegacion.addItem("Ventas", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_HISTORIAL_VENTAS);
			}
		});
		addComponent(menuNavegacion);

		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setMargin(true);
		contentLayout.setSpacing(true);

		// Agregar Sala:
		Button botonAgregaSala = new Button("Añadir sala");
		botonAgregaSala.setIcon(FontAwesome.STAR);
		botonAgregaSala.addClickListener(e -> {
			Sala sala = new Sala();
			sala.setCapacidad(CAPACIDAD_SALA);
			servicioGestorCine.agregaSala(sala);
			Notification.show("Añadida una nueva sala con aforo: " + CAPACIDAD_SALA);
			refresh();
		});
		contentLayout.addComponent(botonAgregaSala);
		contentLayout.setComponentAlignment(botonAgregaSala, Alignment.MIDDLE_RIGHT);

		// Lista de Salas:
		tabsSalas = new TabSheet();
		tabsSalas.setSizeFull();
		tabsSalas.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabsSalas.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		contentLayout.addComponent(tabsSalas);

		addComponent(contentLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		refresh();
	}

	private void refresh() {
		tabsSalas.removeAllComponents();
		List<Sala> salas = servicioGestorCine.listaSalas();
		for (int i = 0; i < salas.size(); i++) {
			tabsSalas.addTab(creaTabSala(salas.get(i)), "Sala " + (i + 1));
		}
	}

	private VerticalLayout creaTabSala(Sala sala) {
		Grid gridSesiones = new Grid();
		Button botonAgregarSesion = new Button("Añadir sesión");
		Button botonEliminarSesion = new Button("Eliminar sesión");
		
		final VerticalLayout result = new VerticalLayout();
		result.setMargin(true);
		result.setSpacing(true);
		result.setSizeFull();

		// GRID DE SESIONES:
		gridSesiones.setColumns("id", "asientosOcupados", "abierta");
		gridSesiones.setSizeFull();
		gridSesiones.setSelectionMode(SelectionMode.SINGLE);

		gridSesiones.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				Sesion sesionSeleccionada = null;
				sesionSeleccionada = (Sesion) e.getSelected().iterator().next();
				botonEliminarSesion.setVisible(true);
			} else {
				botonEliminarSesion.setVisible(false);
			}
//			detalle.setSesion(sesionSeleccionada);
		});
		List<Sesion> sesiones = servicioGestorCine.listaSesiones(sala.getId());
		gridSesiones.setContainerDataSource(new BeanItemContainer<>(Sesion.class, sesiones));
		result.addComponent(gridSesiones);

		// Botón AÑADIR SESIÓN:
		botonAgregarSesion.addClickListener(e -> {
			Sesion sesion = new Sesion();
			sesion.setAbierta(true);
			sesion.setAsientosOcupados(0);
			servicioGestorCine.agregaSesion(sala.getId(), sesion);
			List<Sesion> tempSesiones = servicioGestorCine.listaSesiones(sala.getId());
			gridSesiones.setContainerDataSource(new BeanItemContainer<>(Sesion.class, tempSesiones));
		});
		botonAgregarSesion.setIcon(FontAwesome.PLUS);
		result.addComponent(botonAgregarSesion);

		return result;
	}

}