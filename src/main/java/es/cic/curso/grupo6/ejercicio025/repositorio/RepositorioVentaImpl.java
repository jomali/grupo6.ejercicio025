package es.cic.curso.grupo6.ejercicio025.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio025.modelo.Venta;

@Repository
@Transactional
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
