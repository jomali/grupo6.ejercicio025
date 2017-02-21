package es.cic.curso.grupo6.ejercicio025.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioAlmacen;
import es.cic.curso.grupo6.ejercicio025.repositorio.RepositorioProducto;

@Service
@Transactional
public class ServicioGestorTiendaImpl implements ServicioGestorTienda {

	private static final String ERROR_PRODUCTO_ID = "No existe ningún producto en BB.DD. con ese ID";
	private static final String ERROR_ALMACEN_ID = "No existe ningún almacénen BB.DD. con ese ID";
	private static final String ERROR_ESTADO_ALMACEN = "No puede haber productos en el almacén";

	@Autowired
	private RepositorioProducto repositorioProducto;
	
	@Autowired
	private RepositorioAlmacen repositorioAlmacen;
	
	@Override
	public Producto agregaProducto(String nombre, float precio) {
		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		repositorioProducto.create(producto);
		return producto;
	}

	@Override
	public void agregaProducto(Producto producto) {
		repositorioProducto.create(producto);
	}

	@Override
	public Producto obtenProducto(Long id) {
		Producto producto = repositorioProducto.read(id);
		if (producto == null) {
			throw new IllegalArgumentException(ERROR_PRODUCTO_ID + ": " + id);
		}
		return producto;
	}

	@Override
	public Producto modificaProducto(Long id, Producto producto) {
		Producto productoOriginal = obtenProducto(id);
		producto.setId(id);
		repositorioProducto.update(producto);
		return productoOriginal;
	}

	@Override
	public Producto eliminaProducto(Long id) {
		Producto producto = obtenProducto(id);
		repositorioProducto.delete(producto);
		return producto;
	}

	@Override
	public void eliminaProducto(Producto producto) {
		repositorioProducto.delete(producto);
	}

	@Override
	public List<Producto> listaProductos() {
		return repositorioProducto.list();
	}

	@Override
	public Almacen agregaAlmacen(String nombre, int capacidad) {
		Almacen almacen = new Almacen();
		almacen.setNombre(nombre);
		almacen.setCapacidad(capacidad);
		repositorioAlmacen.create(almacen);
		return almacen;
	}

	@Override
	public void agregaAlmacen(Almacen almacen) {
		repositorioAlmacen.create(almacen);
	}

	@Override
	public Almacen obtenAlmacen(Long id) {
		Almacen almacen = repositorioAlmacen.read(id);
		if (almacen == null) {
			throw new IllegalArgumentException(ERROR_ALMACEN_ID + ": " + id);
		}
		return almacen;
	}

	@Override
	public Almacen modificaAlmacen(Long id, Almacen almacen) {
		Almacen almacenOriginal = obtenAlmacen(id);
		almacen.setId(id);
		repositorioAlmacen.update(almacen);
		return almacenOriginal;
	}
	
	@Override
	public Almacen eliminaAlmacen(Long id) {
		Almacen almacen = obtenAlmacen(id);
		repositorioAlmacen.delete(almacen);
		return almacen;
	}

	@Override
	public void eliminaAlmacen(Almacen almacen) {
		repositorioAlmacen.delete(almacen);
	}

	@Override
	public List<Almacen> listaAlmacenes() {
		return repositorioAlmacen.list();
	}

}
