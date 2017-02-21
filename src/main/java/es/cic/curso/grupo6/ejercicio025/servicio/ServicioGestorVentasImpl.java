package es.cic.curso.grupo6.ejercicio025.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.modelo.Venta;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioAlmacen;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioInventario;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioProducto;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioVenta;

@Service
@Transactional
public class ServicioGestorVentasImpl implements ServicioGestorVentas {

	private static final String ERROR_PRODUCTO_ID = "No existe ningún producto en BB.DD. con ese ID";
	private static final String ERROR_ALMACEN_ID = "No existe ningún almacénen BB.DD. con ese ID";
	
	@Autowired
	private RepositorioAlmacen repositorioAlmacen;
	
	@Autowired
	private RepositorioInventario repositorioInventario;
	
	@Autowired
	private RepositorioProducto repositorioProducto;

	@Autowired
	private RepositorioVenta repositorioVenta;
	
	private Producto obtenProducto(Long idProducto) {
		Producto producto = repositorioProducto.read(idProducto);
		if (producto == null) {
			throw new IllegalArgumentException(ERROR_PRODUCTO_ID + ": " + idProducto);
		}
		return producto;
	}
	
	private Almacen obtenAlmacen(Long idAlmacen) {
		Almacen almacen = repositorioAlmacen.read(idAlmacen);
		if (almacen == null) {
			throw new IllegalArgumentException(ERROR_ALMACEN_ID + ": " + idAlmacen);
		}
		return almacen;
	}
	
	@Override
	public void vende(Long idProducto, Long idAlmacen, int cantidad) {
		Producto producto = obtenProducto(idProducto);
		Almacen almacen = obtenAlmacen(idAlmacen);
		
		Inventario entradaInventario = repositorioInventario.read(producto.getId(), almacen.getId());
		if (entradaInventario.getCantidad() < cantidad) {
			throw new IllegalArgumentException();
		}
		entradaInventario.modifyCantidad(-cantidad);
		repositorioInventario.update(entradaInventario);
		
		Venta venta = new Venta();
		venta.setProducto(producto);
		venta.setUnidades(cantidad);
		venta.setImporte(producto.getPrecio() * cantidad);
		repositorioVenta.create(venta);
	}

}
