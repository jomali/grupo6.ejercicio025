 package es.cic.curso.grupo5.ejercicio024.servicio;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;
import es.cic.curso.grupo5.ejercicio024.repositorio.RepositorioPelicula;

@Transactional
@Service
public class ServicioPeliculaImpl implements ServicioPelicula {

	@Autowired
	private RepositorioPelicula repositorioPelicula;
		
	
	/* (non-Javadoc)
	 * @see es.cic.curso.curso12.ejercicio023.servicio.PeliculaService#aniadirPelicula(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	@Override
	public Long aniadirPelicula(String titulo, String director, String productora, String interprete, int year, int duracion, String genero){
		Pelicula peli = new Pelicula(titulo, director, productora, interprete, year, duracion, genero);
		
		Pelicula peliAdd = aniadirPelicula(peli);		
		
		return peliAdd.getId();
	}
	
	@Override
	public Pelicula aniadirPelicula(Pelicula nueva) {
		return repositorioPelicula.create(nueva);
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.curso12.ejercicio023.servicio.PeliculaService#actualizarPelicula(es.cic.curso.curso12.ejercicio023.dominio.Pelicula)
	 */
	@Override
	public Pelicula actualizarPelicula(Pelicula peli){
		return repositorioPelicula.update(peli);
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.curso12.ejercicio023.servicio.PeliculaService#obtenerPelicula(java.lang.Long)
	 */
	@Override
	public Pelicula obtenerPelicula(Long id){
		return repositorioPelicula.read(id);
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.curso12.ejercicio023.servicio.PeliculaService#obtenerPeliculas()
	 */
	@Override
	public List<Pelicula> obtenerPeliculas(){
		return repositorioPelicula.list();
	}
	
	/* (non-Javadoc)
	 * @see es.cic.curso.curso12.ejercicio023.servicio.PeliculaService#borrarPelicula(java.lang.Long)
	 */
	@Override
	public void  borrarPelicula(Long id){
		Pelicula aBorrar = obtenerPelicula(id);
		repositorioPelicula.delete(aBorrar);
	}
}
