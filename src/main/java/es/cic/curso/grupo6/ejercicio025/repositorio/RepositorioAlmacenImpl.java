package es.cic.curso.grupo6.ejercicio025.repositorio;

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;

public class RepositorioAlmacenImpl extends RepositorioAbstractoImpl<Long, Almacen> implements RepositorioAlmacen {

	@Override
	public Class<Almacen> obtenClaseT() {
		return Almacen.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Almacen.class.getSimpleName().toUpperCase();
	}

}
