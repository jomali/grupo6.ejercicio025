package es.cic.curso.grupo6.ejercicio025.repositorio;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

@Repository
@Transactional
public class RepositorioProductoImpl extends RepositorioAbstractoImpl<Long, Producto> implements RepositorioProducto {

	@Override
	public Class<Producto> obtenClaseT() {
		return Producto.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Producto.class.getSimpleName().toUpperCase();
	}

}
