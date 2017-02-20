package es.cic.curso.grupo5.ejercicio024.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio024.modelo.Sala;
import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;
import es.cic.curso.grupo5.ejercicio024.modelo.Venta;
import es.cic.curso.grupo5.ejercicio024.repositorio.RepositorioSala;
import es.cic.curso.grupo5.ejercicio024.repositorio.RepositorioSesion;
import es.cic.curso.grupo5.ejercicio024.repositorio.RepositorioVenta;

@Service
@Transactional
public class ServicioGestorTaquillaImpl implements ServicioGestorTaquilla {

	public static final float PRECIO_ENTRADA = 5.0F;

	private static final String ERROR_SALA_ID = "No existe ninguna sala en BB.DD. con ese ID";
	private static final String ERROR_SESION_ID = "No existe ninguna sesión en BB.DD. con ese ID";
	
	@Autowired
	private RepositorioSala salaRepo;
	
	@Autowired
	private RepositorioSesion sesionRepo;
	
	@Autowired
	private RepositorioVenta ventaRepo;
	
	private Sesion obtenSesion(Long id) {
		Sesion resultado = sesionRepo.read(id);
		if (resultado == null) {
			throw new IllegalArgumentException(ERROR_SESION_ID + ": " + id);
		}
		return resultado;
	}
	
	@Override
	public float calculaRecaudacion(Long idSala) {
		Sala sala = salaRepo.read(idSala);
		
		if (sala == null) {
			throw new IllegalArgumentException(ERROR_SALA_ID + ": " + idSala);
		}
		
		float resultado = 0;
		
		for(Sesion s: sesionRepo.listSesionesSala(idSala)){
			resultado+=PRECIO_ENTRADA * s.getAsientosOcupados();
		}
		
		return resultado;
	}

	@Override
	public float calculaRecaudacion() {
		float resultado = 0;
		for(Sesion s:sesionRepo.list()){
			resultado += PRECIO_ENTRADA * s.getAsientosOcupados();
		}
		return resultado;
	}

	@Override
	public void cambiaEstadoSesion(Long idSesion, boolean abierta) {
		Sesion sesion = obtenSesion(idSesion);
		sesion.setAbierta(abierta);
		sesionRepo.update(sesion);
	}

	@Override
	public void cambiaEstadoTaquilla(boolean abierta) {
		for(Sesion s: sesionRepo.list()){
			s.setAbierta(abierta);
			sesionRepo.update(s);
		}

	}

	@Override
	public boolean estaAbierta(Long idSesion) {
		Sesion sesion = obtenSesion(idSesion);
		
		return sesion.isAbierta();
	}

	@Override
	public int localidadesDisponibles(Long idSesion) {
		Sesion sesion = obtenSesion(idSesion);
		Sala sala = sesion.getSala();
		
		return sala.getCapacidad()-sesion.getAsientosOcupados();
	}

	@Override
	public double vendeEntrada(Long idSesion, Long idLocalidad) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double vendeEntradas(Long idSesion, int nEntradas) {
		Sesion sesion = obtenSesion(idSesion);
		if ((sesion.getSala().getCapacidad() - sesion.getAsientosOcupados()) < nEntradas) {
			throw new IndexOutOfBoundsException();
		}
		if (nEntradas < 1) {
			throw new IllegalArgumentException();
		}
		if (sesion.isAbierta() == false){
			throw new IllegalStateException("Sesion cerrada, lo sentimos.");
		}
		float resultado = nEntradas * PRECIO_ENTRADA;
		sesion.setAsientosOcupados(sesion.getAsientosOcupados()+nEntradas);
		sesionRepo.update(sesion);
		
		Venta venta = new Venta();
		venta.setDescuento(false);
		venta.setImporte(resultado);
		venta.setnEntradas((short) nEntradas);
		venta.setSesion(sesion);
		ventaRepo.create(venta);
		
		return resultado;
	}
	
	@Override
	public List<Venta> listaVentas() {
		return ventaRepo.list();
	}

}
