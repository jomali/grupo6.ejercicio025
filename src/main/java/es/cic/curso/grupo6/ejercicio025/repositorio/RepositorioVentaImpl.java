package es.cic.curso.grupo6.ejercicio025.repositorio;

import es.cic.curso.grupo6.ejercicio025.modelo.Venta;

public class RepositorioVentaImpl extends RepositorioAbstractoImpl<Long, Venta> implements RepositorioVenta {

	@Override
	public Class<Venta> obtenClaseT() {
		return Venta.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Venta.class.getSimpleName().toUpperCase();
	}


}
