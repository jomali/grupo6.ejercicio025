package es.cic.curso.grupo6.ejercicio025.servicio;

import java.util.ArrayList;
import java.util.List;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

public class ServicioGestorInventarioImpl implements ServicioGestorInventario {
	
	@Override
	public List<Producto> obtenListaAlmacen() {
		return new ArrayList<>();
	}

	@Override
	public List<Producto> obtenListaTienda() {
		return new ArrayList<>();
	}

}
