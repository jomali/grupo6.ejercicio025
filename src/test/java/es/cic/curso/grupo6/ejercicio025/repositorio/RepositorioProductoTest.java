package es.cic.curso.grupo6.ejercicio025.repositorio;

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

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio025/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioProductoTest {
	
	@Autowired
	private RepositorioProducto sut;

	private Producto generaElementoPrueba() {
		Producto elemento = new Producto();
		elemento.setNombre("Galletas");
		elemento.setPrecio(2.0F);
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Producto producto;
		
		producto = new Producto();
		assertNull(producto.getId());

		producto = generaElementoPrueba();
		assertNotNull(producto.getId());
	}

	@Test
	public void testRead() {
		Producto elemento1 = generaElementoPrueba();
		Producto elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getNombre().equals(elemento2.getNombre()));
		assertEquals(elemento1.getPrecio(), elemento2.getPrecio(), 0.0001);

		try {
			@SuppressWarnings("unused")
			Producto elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Producto original = generaElementoPrueba();
		Producto clon = new Producto();
		clon.setId(original.getId());
		clon.setNombre(original.getNombre());
		clon.setPrecio(original.getPrecio());

		original.setPrecio(5.0F);
		sut.update(original);

		Producto modificado = sut.read(original.getId());
		assertEquals(original.getPrecio(), modificado.getPrecio(), 0.0001);
		assertNotEquals(clon.getPrecio(), modificado.getPrecio(), 0.0001);
	}

	@Test
	public void testDelete() {
		Producto elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Producto resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaElementoPrueba();
		}

		List<Producto> lista = sut.list();
		assertEquals(numero, lista.size());
	}

}
