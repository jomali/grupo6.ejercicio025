package es.cic.curso.grupo5.ejercicio024.vista;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;

public class PeliculaForm extends FormLayout {
	private static final long serialVersionUID = -6668213921050396590L;
	
	@PropertyId("titulo")
	protected TextField titulo;
	@PropertyId("director")
	protected TextField director;
	@PropertyId("productora")
	protected TextField productora;
	@PropertyId("interprete")
	protected TextField interprete;
	@PropertyId("estreno")
	protected TextField estreno;
	@PropertyId("duracion")
	protected TextField duracion;
	@PropertyId("genero")
	protected TextField genero;
	
	private Button accion;
	private Button borrar;
	private Pelicula pelicula;
	
	private VerticalLayout padre;
	
	public PeliculaForm(VistaPeliculas padre) {
		this.padre = padre;
		
		final HorizontalLayout horizontal= new HorizontalLayout();
		horizontal.setSpacing(true);
		
		final VerticalLayout vertical1 = new VerticalLayout();
		final VerticalLayout vertical2 = new VerticalLayout();
		final VerticalLayout horizontal3 = new VerticalLayout();

		vertical1.setSpacing(true);
		vertical2.setSpacing(true);
		horizontal3.setSpacing(true);
		
		titulo = new TextField("Título: ");
		titulo.setInputPrompt("Título");
		director = new TextField("Director: ");
		director.setInputPrompt("Director");
		productora = new TextField("Productora: ");
		productora.setInputPrompt("Productora");
		interprete = new TextField("Interprete: ");
		interprete.setInputPrompt("Interprete");
		estreno = new TextField("Estreno: ");
		estreno.setInputPrompt("2017");
		duracion = new TextField("Duración: ");
		duracion.setInputPrompt("100");
		genero = new TextField("Género: ");
		genero.setInputPrompt("Género");
		
		accion = new Button("Actualizar");
		accion.addClickListener(e -> {
			padre.cargaGrid(pelicula);
			padre.mostrarBotones();
			});
		borrar = new Button("Borrar");
		borrar.addClickListener(e->padre.borrarGrid(pelicula));
		borrar.setIcon(FontAwesome.CLOSE);
				
		horizontal.addComponents(vertical1,vertical2, horizontal3);
		vertical1.addComponents(titulo,director,productora,interprete);
		vertical2.addComponents(estreno,duracion,genero);
		horizontal3.addComponents(accion,borrar);

		addComponents(horizontal);
		
		
		setPelicula(null);
	}
	
	public void setPelicula(Pelicula pelicula) {
		this.setVisible(pelicula != null);
		this.pelicula = pelicula;

		if (pelicula != null) {
			BeanFieldGroup.bindFieldsUnbuffered(pelicula, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Pelicula(), this);

		}
	}
	
	
	public void ocultarBotones(){
		accion.setCaption("Nueva Pelicula");
		accion.setIcon(FontAwesome.PLUS);
		borrar.setVisible(false);
	}
	public void mostrarBotones(){
		accion.setVisible(true);
		accion.setCaption("Actualizar");
		accion.setIcon(FontAwesome.REFRESH);
		borrar.setVisible(true);
	}
	
}