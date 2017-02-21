package es.cic.curso.grupo6.ejercicio025.repositorio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;

public interface RepositorioInventario extends Repositorio<Long, Inventario> {

	Inventario read(Long idProducto, Long idAlmacen);
	
	List<Inventario> listByAlmacen(Long idAlmacen);
	
	List<Inventario> listByProducto(Long idTienda);
	
}
