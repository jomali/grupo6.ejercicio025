package es.cic.curso.grupo6.ejercicio025.repositorio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.Before;
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
import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;
import es.cic.curso.grupo6.ejercicio025.modelo.Venta;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio025/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioVentaTest {

	@Autowired
	private RepositorioVenta sut;

	private Producto producto;
	
	@PersistenceContext
	protected EntityManager em;

	@Before
	public void setUp(){
		
		producto = new Producto();
		em.persist(producto);
		em.flush();
		
	}
	private Venta generaElementoPrueba() {
		
		Venta elemento = new Venta();
		
		elemento.setProducto(producto);
		elemento.setImporte(20);
		elemento.setUnidades(12);

		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Venta elemento;
		
		elemento = new Venta();
		assertNull(elemento.getId());

		elemento = generaElementoPrueba();
		assertNotNull(elemento.getId());
	}

	@Test
	public void testRead() {
		Venta elemento1 = generaElementoPrueba();
		Venta elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getImporte() == (elemento2.getImporte()));
		assertTrue(elemento1.getUnidades() == (elemento2.getUnidades()));


		try {
			@SuppressWarnings("unused")
			Venta elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Venta original = generaElementoPrueba();
		Venta clon = new Venta();
		clon.setId(original.getId());
		clon.setImporte(original.getImporte());
		clon.setUnidades(original.getUnidades());

		original.setImporte(10);;
		original.setUnidades(5);
		sut.update(original);

		Venta modificado = sut.read(original.getId());
		assertNotEquals(clon.getUnidades(), modificado.getUnidades());
		assertNotEquals(clon.getImporte(), modificado.getImporte());
	}

	@Test
	public void testDelete() {
		Venta elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Venta resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaElementoPrueba();
		}

		List<Venta> lista = sut.list();
		assertEquals(numero, lista.size());
	}

}
