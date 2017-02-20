package es.cic.curso.grupo5.ejercicio024.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio024.modelo.Sala;
import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;
import es.cic.curso.grupo5.ejercicio024.repositorio.RepositorioSala;
import es.cic.curso.grupo5.ejercicio024.repositorio.RepositorioSesion;

@Service
@Transactional
public class ServicioGestorCineImpl implements ServicioGestorCine {
	
	private static final String ERROR_SALA_ID = "No existe ninguna sala en BB.DD. con ese ID";
	private static final String ERROR_SESION_ID = "No existe ninguna sesión en BB.DD. con ese ID";
	private static final String ERROR_ESTADO_SALA = "No puede haber sesiones programadas en la sala";
	
	@Autowired
	private RepositorioSala salaRepo;
	
	@Autowired
	private RepositorioSesion sesionRepo;

	@Override
	public void agregaSala(Sala sala) {
		salaRepo.create(sala);

	}

	@Override
	public void agregaSesion(Long idSala, Sesion sesion) {
		
		Sala sala = obtenSala(idSala);
		sesion.setSala(sala);
		sesionRepo.create(sesion);

	}

	@Override
	public Sala obtenSala(Long id) {
		Sala sala = salaRepo.read(id);
		if (sala == null) {
			throw new IllegalArgumentException(ERROR_SALA_ID + ": " + id);
		}
		return sala;
	}

	@Override
	public Sesion obtenSesion(Long id) {
		Sesion sesion = sesionRepo.read(id);
		if (sesion == null) {
			throw new IllegalArgumentException(ERROR_SESION_ID + ": " + id);
		}
		return sesion;		
	}

	@Override
	public Sala modificaSala(Long id, Sala sala) {
		Sala salaOriginal = obtenSala(id);
		if (!listaSesiones(id).isEmpty()) {
			throw new IllegalStateException(ERROR_ESTADO_SALA);
		}
		sala.setId(id);
		salaRepo.update(sala);
		return salaOriginal;
	}

	@Override
	public Sesion modificaSesion(Long id, Sesion sesion) {
		Sesion sesionOriginal = obtenSesion(id);
		sesion.setId(id);
		sesionRepo.update(sesion);
		return sesionOriginal;
	}

	@Override
	public Sala eliminaSala(Long id) {
		Sala sala = obtenSala(id);
		if (!listaSesiones(id).isEmpty()) {
			throw new IllegalStateException(ERROR_ESTADO_SALA);
		}
		salaRepo.delete(sala);
		return sala;
	}

	@Override
	public Sesion eliminaSesion(Long id) {
		Sesion sesion = obtenSesion(id);
		sesionRepo.delete(sesion);
		
		return sesion;
	}

	@Override
	public void eliminaSesiones(Long id) {
		if (!listaSesiones(id).isEmpty()) {
			throw new IllegalStateException(ERROR_ESTADO_SALA);
		}
		sesionRepo.deleteSesionesSala(id);
	}

	@Override
	public List<Sala> listaSalas() {
		return salaRepo.list();
	}

	@Override
	public List<Sesion> listaSesiones() {
		return sesionRepo.list();
	}

	@Override
	public List<Sesion> listaSesiones(Long id) {		
		Sala sala = salaRepo.read(id);
		if (sala == null) {
			throw new IllegalArgumentException(ERROR_SALA_ID + ": " + id);
		}
		return sesionRepo.listSesionesSala(id);
	}

}
