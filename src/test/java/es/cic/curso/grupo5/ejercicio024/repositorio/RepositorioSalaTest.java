package es.cic.curso.grupo5.ejercicio024.repositorio;

import static org.junit.Assert.*;

import java.util.List;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5/ejercicio024/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioSalaTest {
	
	public static final int CAPACIDAD_SALA1 = 100;
	public static final int CAPACIDAD_SALA2 = 150;

	@Autowired
	private RepositorioSala sut;

	private Sala generaElementoPrueba() {
		Sala elemento = new Sala();
		elemento.setCapacidad(CAPACIDAD_SALA1);
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Sala sala;
		
		sala = new Sala();
		assertNull(sala.getId());
		
		sala = generaElementoPrueba();
		assertNotNull(sala.getId());
	}

	@Test
	public void testRead() {
		Sala elemento1 = generaElementoPrueba();
		Sala elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getCapacidad() == elemento2.getCapacidad());

		try {
			@SuppressWarnings("unused")
			Sala elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Sala original = generaElementoPrueba();
		Sala clon = new Sala();
		clon.setId(original.getId());
		clon.setCapacidad(original.getCapacidad());
		clon.setSesiones(original.getSesiones());

		original.setCapacidad(CAPACIDAD_SALA2);
		sut.update(original);

		Sala modificado = sut.read(original.getId());
		assertTrue(original.getCapacidad() == modificado.getCapacidad());
		assertFalse(clon.getCapacidad() == modificado.getCapacidad());
	}

	@Test
	public void testDelete() {
		Sala elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Sala resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaElementoPrueba();
		}

		List<Sala> lista = sut.list();
		assertEquals(numero, lista.size());
	}

}
