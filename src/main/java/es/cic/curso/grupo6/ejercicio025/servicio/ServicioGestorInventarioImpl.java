package es.cic.curso.grupo6.ejercicio025.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

@Service
@Transactional
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
