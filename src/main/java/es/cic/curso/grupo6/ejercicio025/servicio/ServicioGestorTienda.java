package es.cic.curso.grupo6.ejercicio025.servicio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio025.dto.ProductoDTO;
import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

/**
 * Define un conjunto de operaciones CRUD sobre las entidades
 * <strong>Producto</strong> y <strong>Almacen</strong>.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @version 1.0
 * @serial 2017/02/02
 *
 */
public interface ServicioGestorTienda {

	Producto agregaProducto(String nombre, float precio);
	
	void agregaProducto(Producto producto);

	Producto obtenProducto(Long id);

	Producto modificaProducto(Long id, Producto producto);

	Producto eliminaProducto(Long id);

	void eliminaProducto(Producto producto);

	List<Producto> listaProductos();
	
	List<ProductoDTO> listaProductosDTO();	
	
	Almacen agregaAlmacen(String nombre, int capacidad);

	void agregaAlmacen(Almacen almacen);
	
	Almacen obtenAlmacen(Long id);
	
	Almacen modificaAlmacen(Long id, Almacen almacen);
	
	Almacen eliminaAlmacen(Long id);
	
	void eliminaAlmacen(Almacen almacen);
	
	List<Almacen> listaAlmacenes();

}
