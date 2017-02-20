package es.cic.curso.grupo5.ejercicio024.servicio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio024.modelo.Sala;
import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5/ejercicio024/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorCineTest {
	public static final int CAPACIDAD_SALA1=100;
	public static final int CAPACIDAD_SALA2=50;
	public static final int ASIENTOS_OCUPADOS=22;
	
	@Autowired
	private ServicioGestorCine sut;
	
	@PersistenceContext
	private EntityManager em;
	
	
	///////////////////////  Before Test	///////////////////////////
	private Sala generaSala(int capacidad){
		Sala sala = new Sala();
		sala.setCapacidad(capacidad);
		em.persist(sala);
		em.flush();
		return sala;
	}
	
	private Sesion generaSesion(Sala sala){
		Sesion sesion = new Sesion();
		sesion.setSala(sala);
		em.persist(sesion);
		em.flush();
		return sesion;
	}
	//////////////////////////////////////////////////
	
	@Test
	public void testAgregarSala(){
		Sala sala;
		sala = new Sala();
		sala.setCapacidad(CAPACIDAD_SALA1);
		assertNull(sala.getId());
		
		sut.agregaSala(sala);
		assertNotNull(sala.getId());
	}
	
	@Test
	public void testAgregaSesion() {
		Sesion sesion;

		// 1) Sesión con una sala no registrada en BB.DD.
		sesion = new Sesion();
		assertNull(sesion.getId());
		try {
			sut.agregaSesion(0L, sesion);
			fail("No se debería poder añadir una sesión con un id de sala inválido");
		} catch (IllegalArgumentException iae) {

		} catch (PersistenceException pe) {

		}

		// 2) Sesión con una sala registrada en BB.DD.
		Sala sala = generaSala(CAPACIDAD_SALA1);
		sesion = new Sesion();
		assertNull(sesion.getId());
		sut.agregaSesion(sala.getId(), sesion);
		assertNotNull(sesion.getId());
	}
	
	@Test
	public void testObtenSala() {
		Sala elemento1 = generaSala(CAPACIDAD_SALA1);
		Sala elemento2 = sut.obtenSala(elemento1.getId());
		assertEquals(elemento1.getCapacidad(), elemento2.getCapacidad());

		try {
			Sala elemento3 = sut.obtenSala(Long.MAX_VALUE);
			assertNull(elemento3);
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testObtenSesion() {
		Sala sala = generaSala(CAPACIDAD_SALA1);

		Sesion elemento1 = generaSesion(sala);
		Sesion elemento2 = sut.obtenSesion(elemento1.getId());
		assertEquals(elemento1.getSala().getId(), elemento2.getSala().getId(), 0.0001);
		assertEquals(elemento1.getAsientosOcupados(), elemento2.getAsientosOcupados());

		try {
			Sesion elemento3 = sut.obtenSesion(Long.MAX_VALUE);
			assertNull(elemento3);
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testModificaSala() {
		Sala original, clon, modificado;

		// 1) Sala sin sesiones programadas
		original = generaSala(CAPACIDAD_SALA1);
		clon = new Sala();
		clon.setId(original.getId());
		clon.setCapacidad(original.getCapacidad());
		clon.setSesiones(original.getSesiones());

		original.setCapacidad(CAPACIDAD_SALA2);
		sut.modificaSala(original.getId(), original);

		modificado = sut.obtenSala(original.getId());
		assertTrue(original.getCapacidad() == modificado.getCapacidad());
		assertFalse(clon.getCapacidad() == modificado.getCapacidad());

		// 2) Sala con sesiones programadas
		original = generaSala(CAPACIDAD_SALA1);
		for (int i = 0; i < 5; i++) {
			generaSesion(original);
		}
		original.setCapacidad(CAPACIDAD_SALA2);
		try {
			sut.modificaSala(original.getId(), original);
			fail("No se debería poder modificar la sala.");
		} catch (IllegalStateException ise) {
			
		}
	}

	@Test
	public void testModificarSesion() {
		Sala sala = generaSala(CAPACIDAD_SALA1);

		Sesion original = generaSesion(sala);
		Sesion clon = new Sesion();
		clon.setId(original.getId());
		clon.setSala(original.getSala());
		clon.setAsientosOcupados(original.getAsientosOcupados());

		original.setAsientosOcupados(ASIENTOS_OCUPADOS);
		sut.modificaSesion(original.getId(), original);

		Sesion modificado = sut.obtenSesion(original.getId());
		assertTrue(original.getAsientosOcupados() == modificado.getAsientosOcupados());
		assertFalse(clon.getAsientosOcupados() == modificado.getAsientosOcupados());
	}

	@Test
	public void testEliminaSala() {
		Sala sala;

		// 1) Sala sin sesiones programadas
		sala = generaSala(CAPACIDAD_SALA1);
		sut.eliminaSala(sala.getId());

		try {
			@SuppressWarnings("unused")
			Sala resultado = sut.obtenSala(sala.getId());
			fail("La sala ya no debería estar registrada en BB.DD.");
		} catch (IllegalArgumentException iae) {
			
		}

		// 2) Sala con sesiones programadas
		sala = generaSala(CAPACIDAD_SALA1);
		for (int i = 0; i < 5; i++) {
			generaSesion(sala);
		}
		try {
			sut.eliminaSala(sala.getId());
			fail("No se debería poder eliminar la sala.");
		} catch (IllegalStateException ise) {
			
		}
	}

	@Test
	public void testEliminaSesion() {
		Sala sala = generaSala(CAPACIDAD_SALA1);
		Sesion elemento = generaSesion(sala);
		sut.eliminaSesion(elemento.getId());

		try {
			@SuppressWarnings("unused")
			Sesion resultado = sut.obtenSesion(elemento.getId());
			fail("La sesión ya no debería estar registrada en BB.DD.");
		} catch (IllegalArgumentException iae) {
			
		}
	}

	@Test
	public void testListaSalas() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaSala(CAPACIDAD_SALA1);
		}

		List<Sala> lista = sut.listaSalas();
		assertEquals(numero, lista.size());
	}

	@Test
	public void testListaSesiones() {
		int numero = 5;
		Sala sala = generaSala(CAPACIDAD_SALA1);
		for (int i = 0; i < numero; i++) {
			generaSesion(sala);
		}

		List<Sesion> lista = sut.listaSesiones();
		assertEquals(numero, lista.size());

	}
	
	
	
	
}
