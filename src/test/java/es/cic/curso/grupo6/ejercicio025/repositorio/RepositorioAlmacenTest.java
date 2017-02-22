package es.cic.curso.grupo6.ejercicio025.repositorio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio025/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioAlmacenTest {

	@Autowired
	private RepositorioAlmacen sut;

	private Almacen generaElementoPrueba() {
		Almacen elemento = new Almacen();
		elemento.setNombre("Principal");
		elemento.setCapacidad(5000);

		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Almacen elemento;
		
		elemento = new Almacen();
		assertNull(elemento.getId());

		elemento = generaElementoPrueba();
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Almacen elemento1 = generaElementoPrueba();
		Almacen elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getNombre().equals(elemento2.getNombre()));
		assertEquals(elemento1.getCapacidad(), elemento2.getCapacidad(), 0.0001);

		try {
			@SuppressWarnings("unused")
			Almacen elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Almacen original = generaElementoPrueba();
		Almacen clon = new Almacen();
		clon.setId(original.getId());
		clon.setNombre(original.getNombre());
		clon.setCapacidad(original.getCapacidad());

		original.setNombre("Principal");
		original.setCapacidad(2000);
		sut.update(original);

		Almacen modificado = sut.read(original.getId());
		assertEquals(original.getNombre(), modificado.getNombre());
		assertNotEquals(clon.getCapacidad(), modificado.getCapacidad());
	}

	@Test
	public void testDelete() {
		Almacen elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Almacen resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaElementoPrueba();
		}

		List<Almacen> lista = sut.list();
		assertEquals(numero, lista.size());
	}

}
