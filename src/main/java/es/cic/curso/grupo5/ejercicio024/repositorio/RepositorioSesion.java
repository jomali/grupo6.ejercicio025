package es.cic.curso.grupo5.ejercicio024.repositorio;

import java.util.List;

import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;

public interface RepositorioSesion extends Repositorio<Long, Sesion> {
	
	List<Sesion> listSesionesSala(Long idSala);
	
	void deleteSesionesSala(Long idSala);

}
