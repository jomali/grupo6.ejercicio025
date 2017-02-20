package es.cic.curso.grupo5.ejercicio024.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;

@Repository
@Transactional
public class RepositorioPeliculaImpl extends RepositorioAbstractoImpl<Long, Pelicula> implements RepositorioPelicula {

	@Override
	public Class<Pelicula> obtenClaseT() {
		return Pelicula.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Pelicula.class.getSimpleName().toUpperCase();
	}

}
