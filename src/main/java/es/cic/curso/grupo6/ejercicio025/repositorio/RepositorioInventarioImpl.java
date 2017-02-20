package es.cic.curso.grupo6.ejercicio025.repositorio;

import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;

public class RepositorioInventarioImpl extends RepositorioAbstractoImpl<Long, Inventario> implements RepositorioInventario {

	@Override
	public Class<Inventario> obtenClaseT() {
		return Inventario.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Inventario.class.getSimpleName().toUpperCase();
	}

}
