package es.cic.curso.grupo5.ejercicio024.vista;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;
import es.cic.curso.grupo5.ejercicio024.servicio.ServicioPelicula;

import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class VistaPeliculas extends VerticalLayout implements View {
	private static final long serialVersionUID = -5390150077727891270L;

	private Grid maestro;

	private PeliculaForm detalle;

	private List<Pelicula> listaPeliculas;

	private Button addBtn;
	private Button cancelar;

	private ServicioPelicula servicioPelicula;

	@SuppressWarnings("serial")
	public VistaPeliculas(Navigator navegador, ServicioPelicula servicioPelicula) {
		this.servicioPelicula = servicioPelicula;

		// Navegación entre las vistas de la aplicación:
		MenuBar menuNavegacion = new MenuBar();
		menuNavegacion.setWidth(100.0F, Unit.PERCENTAGE);
		menuNavegacion.addItem("Cine", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo("");
			}
		});
		MenuItem menuItemVistaPeliculas = menuNavegacion.addItem("Películas", null);
		menuItemVistaPeliculas.setEnabled(false);
		menuNavegacion.addItem("Ventas", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_HISTORIAL_VENTAS);
			}
		});
		addComponent(menuNavegacion);

		addComponent(creaTabPeliculas());
		cargaGrid(null);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Vista PELÍCULAS");
	}

	private VerticalLayout creaTabPeliculas() {
		final VerticalLayout result = new VerticalLayout();
		result.setMargin(true);
		result.setSpacing(true);
		result.setSizeFull();

		maestro = new Grid();
		maestro.setColumns("titulo", "director", "interprete", "productora", "estreno", "duracion", "genero");
		maestro.setSizeFull();

		maestro.setFrozenColumnCount(1);
		maestro.setSelectionMode(SelectionMode.SINGLE);

		addBtn = new Button("Añadir película");
		cancelar = new Button("Cancelar");
		cancelar.setVisible(false);

		maestro.addSelectionListener(e -> {
			Pelicula p = null;
			if (!e.getSelected().isEmpty()) {
				p = (Pelicula) e.getSelected().iterator().next();
				addBtn.setVisible(false);
			} else {
				addBtn.setVisible(true);
			}
			detalle.mostrarBotones();
			detalle.setPelicula(p);
		});
		detalle = new PeliculaForm(this);

		cancelar.addClickListener(e -> {
			Pelicula p = null;
			detalle.setPelicula(p);
			addBtn.setVisible(true);
			cancelar.setVisible(false);
		});

		addBtn.addClickListener(e -> {
			addBtn.setVisible(false);
			detalle.ocultarBotones();
			cancelar.setVisible(true);
			aniadirGrid();
		});

		addBtn.setIcon(FontAwesome.FILM);

		result.addComponents(maestro, detalle, addBtn, cancelar);
		result.setMargin(true);
		result.setSpacing(true);
		result.setWidth("100%");

		return result;
	}

	public void cargaGrid(Pelicula peli) {
		if (peli != null) {
			servicioPelicula.actualizarPelicula(peli);
		}

		listaPeliculas = servicioPelicula.obtenerPeliculas();

		maestro.setContainerDataSource(new BeanItemContainer<>(Pelicula.class, listaPeliculas));
		detalle.setPelicula(null);
	}

	public void aniadirGrid() {
		Pelicula p = new Pelicula("", "", "", "", 0, 0, "");
		detalle.setPelicula(p);
		maestro.setContainerDataSource(new BeanItemContainer<>(Pelicula.class, listaPeliculas));
		servicioPelicula.aniadirPelicula(p);
	}

	public void mostrarBotones() {
		addBtn.setVisible(true);
		cancelar.setVisible(false);
	}

	public void borrarGrid(Pelicula peli) {
		listaPeliculas.remove(peli);
		maestro.setContainerDataSource(new BeanItemContainer<>(Pelicula.class, listaPeliculas));
		Long peliId = peli.getId();
		servicioPelicula.borrarPelicula(peliId);
	}

}