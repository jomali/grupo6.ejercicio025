package es.cic.curso.grupo6.ejercicio025.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioAlmacen;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioInventario;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioProducto;

@Service
@Transactional
public class ServicioGestorInventarioImpl implements ServicioGestorInventario {

	private static final String ERROR_PRODUCTO_ID = "No existe ningún producto en BB.DD. con ese ID";
	private static final String ERROR_ALMACEN_ID = "No existe ningún almacénen BB.DD. con ese ID";
	private static final String ERROR_INVENTARIO_ID = "No existe ninguna entrada de inventario en BB.DD. con ese ID";

	@Autowired
	private RepositorioAlmacen repositorioAlmacen;

	@Autowired
	private RepositorioInventario repositorioInventario;

	@Autowired
	private RepositorioProducto repositorioProducto;

	private Producto obtenProducto(Long id) {
		Producto producto = repositorioProducto.read(id);
		if (producto == null) {
			throw new IllegalArgumentException(ERROR_PRODUCTO_ID + ": " + id);
		}
		return producto;
	}

	private Almacen obtenAlmacen(Long id) {
		Almacen almacen = repositorioAlmacen.read(id);
		if (almacen == null) {
			throw new IllegalArgumentException(ERROR_ALMACEN_ID + ": " + id);
		}
		return almacen;
	}

	@Override
	public Inventario obtenEntradaInventario(Long idProducto, Long idAlmacen) {
		// Comprobamos que los IDs se correspondan con entidades registradas en
		// BB.DD.
		Producto producto = obtenProducto(idProducto);
		Almacen almacen = obtenAlmacen(idAlmacen);
		// Retorna la entrada de inventario para los IDs dados
		Inventario resultado;
		resultado = repositorioInventario.read(idProducto, idAlmacen);
		if (resultado == null) {
			resultado = new Inventario();
			resultado.setProducto(producto);
			resultado.setAlmacen(almacen);
			resultado.setCantidad(0);
			repositorioInventario.create(resultado);
		}
		return repositorioInventario.read(idProducto, idAlmacen);
	}

	@Override
	public Inventario obtenEntradaInventario(Long id) {
		Inventario inventario = repositorioInventario.read(id);
		if (inventario == null) {
			throw new IllegalArgumentException(ERROR_INVENTARIO_ID + ": " + id);
		}
		return inventario;
	}

	@Override
	public void estableceCantidadProductos(Long idProducto, Long idAlmacen, int cantidad) {
		if (cantidad < 0) {
			throw new IllegalArgumentException("La cantidad debe ser >= 0: " + cantidad);
		}
		Inventario inventario = obtenEntradaInventario(idProducto, idAlmacen);
		inventario.setCantidad(cantidad);
		repositorioInventario.update(inventario);
	}

	@Override
	public int modificaCantidadProductos(Long idProducto, Long idAlmacen, int delta) {
		Inventario inventario = obtenEntradaInventario(idProducto, idAlmacen);
		int cantidad = inventario.getCantidad();
		if (cantidad + delta < 0) {
			throw new IllegalArgumentException("La cantidad final debe ser >= 0: " + (cantidad - delta));
		}
		inventario.setCantidad(cantidad + delta);
		repositorioInventario.update(inventario);
		return (cantidad + delta);
	}
	
	@Override
	public List<Inventario> listaEntradasPorAlmacen(Long idAlmacen) {
		return repositorioInventario.listByAlmacen(idAlmacen);
	}
	
	@Override
	public List<Inventario> listaEntradasPorProducto(Long idProducto) {
		return repositorioInventario.listByProducto(idProducto);
	}

}
