package es.cic.curso.grupo6.ejercicio025.servicio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

public interface ServicioGestorInventario {

	public List<Producto> obtenListaAlmacen();

	public List<Producto> obtenListaTienda();
	
}
