package es.cic.curso.grupo5.ejercicio024.repositorio;

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
public class RepositorioSesionTest {
	
	public static final int CAPACIDAD_SALA = 100;
	public static final int ASIENTOS_OCUPADOS1 = 12;
	public static final int ASIENTOS_OCUPADOS2 = 50;

	@Autowired
	private RepositorioSesion sut;

	@PersistenceContext
	protected EntityManager em;

	private Sesion generaElementoPrueba() {
		Sala sala = new Sala();
		sala.setCapacidad(CAPACIDAD_SALA);
		em.persist(sala);
		em.flush();
		
		Sesion elemento = new Sesion();
		elemento.setSala(sala);
		elemento.setAsientosOcupados(ASIENTOS_OCUPADOS1);
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Sesion sesion;
		
		sesion = new Sesion();
		assertNull(sesion.getId());

		sesion = generaElementoPrueba();
		assertNotNull(sesion.getId());
	}

	@Test
	public void testRead() {
		Sesion elemento1 = generaElementoPrueba();
		Sesion elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getAsientosOcupados() == elemento2.getAsientosOcupados());

		try {
			@SuppressWarnings("unused")
			Sesion elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Sesion original = generaElementoPrueba();
		Sesion clon = new Sesion();
		clon.setId(original.getId());
		clon.setSala(original.getSala());
		clon.setAsientosOcupados(original.getAsientosOcupados());

		original.setAsientosOcupados(ASIENTOS_OCUPADOS2);
		sut.update(original);

		Sesion modificado = sut.read(original.getId());
		assertTrue(original.getAsientosOcupados() == modificado.getAsientosOcupados());
		assertFalse(clon.getAsientosOcupados() == modificado.getAsientosOcupados());
	}

	@Test
	public void testDelete() {
		Sesion elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Sesion resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaElementoPrueba();
		}

		List<Sesion> lista = sut.list();
		assertEquals(numero, lista.size());
	}
	
	@Test
	public void testListSesionesSala() {
		// Genera salas de prueba
		Sala sala1 = new Sala();
		sala1.setCapacidad(CAPACIDAD_SALA);
		Sala sala2 = new Sala();
		sala2.setCapacidad(CAPACIDAD_SALA);
		em.persist(sala1);
		em.persist(sala2);
		em.flush();
		
		int numero = 5;
		Sesion sesion;
		
		for (int i = 0; i < numero; i++) {
			sesion = new Sesion();
			sesion.setSala(sala1);
			sut.create(sesion);
		}
		for (int i = 0; i < numero; i++) {
			sesion = new Sesion();
			sesion.setSala(sala2);
			sut.create(sesion);
		}
		
		List<Sesion> lista;
		
		lista = sut.listSesionesSala(sala1.getId());
		assertEquals(numero, lista.size());
		lista = sut.listSesionesSala(sala2.getId());
		assertEquals(numero, lista.size());
		lista = sut.list();
		assertEquals(numero * 2, lista.size());
	}
	
	@Test
	public void testDeleteSesionesSala() {
		// Genera salas de prueba
		Sala sala1 = new Sala();
		sala1.setCapacidad(CAPACIDAD_SALA);
		Sala sala2 = new Sala();
		sala2.setCapacidad(CAPACIDAD_SALA);
		em.persist(sala1);
		em.persist(sala2);
		em.flush();
		
		int numero = 5;
		Sesion sesion;
		
		for (int i = 0; i < numero; i++) {
			sesion = new Sesion();
			sesion.setSala(sala1);
			sut.create(sesion);
			sesion = new Sesion();
			sesion.setSala(sala2);
			sut.create(sesion);
		}
		
		List<Sesion> listaCompleta;
		List<Sesion> listaParcial;
		
		listaCompleta = sut.list();
		listaParcial = sut.listSesionesSala(sala1.getId());
		assertEquals(numero * 2, listaCompleta.size());
		assertEquals(numero, listaParcial.size());
		
		sut.deleteSesionesSala(sala1.getId());
		listaCompleta = sut.list();
		listaParcial = sut.listSesionesSala(sala1.getId());
		assertEquals(numero, listaCompleta.size());
		assertEquals(0, listaParcial.size());
	}

}
