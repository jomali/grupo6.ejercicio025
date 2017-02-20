package es.cic.curso.grupo5.ejercicio024.servicio;

import java.util.List;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;


public interface ServicioPelicula {

	Long aniadirPelicula(String titulo, String director, String productora, String interprete, int year, int duracion,
			String genero);

	Pelicula actualizarPelicula(Pelicula peli);

	Pelicula obtenerPelicula(Long id);

	List<Pelicula> obtenerPeliculas();

	void borrarPelicula(Long id);

	Pelicula aniadirPelicula(Pelicula nueva);

}