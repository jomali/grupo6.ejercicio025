package es.cic.curso.grupo6.ejercicio025.servicio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;

public interface ServicioGestorInventario {
	
	Inventario obtenEntradaInventario(Long idProducto, Long idAlmacen);
	
	Inventario obtenEntradaInventario(Long id);
	
	void estableceCantidadProductos(Long idProducto, Long idAlmacen, int cantidad);
	
	int modificaCantidadProductos(Long idProducto, Long idAlmacen, int delta);
	
	List<Inventario> listaEntradasPorAlmacen(Long idAlmacen);
	
	List<Inventario> listaEntradasPorProducto(Long idProducto);
	
}
