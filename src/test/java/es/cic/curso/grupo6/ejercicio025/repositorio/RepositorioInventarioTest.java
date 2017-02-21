package es.cic.curso.grupo6.ejercicio025.repositorio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio025/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioInventarioTest {

	@Autowired
	private RepositorioInventario sut;

	@PersistenceContext
	protected EntityManager em;

	private Inventario generaElementoPrueba() {
		Producto producto = new Producto();
		producto.setNombre("Galletas");
		producto.setPrecio(2.0F);
		Almacen almacen = new Almacen();
		almacen.setNombre("Almacén");
		almacen.setCapacidad(100);
		em.persist(producto);
		em.persist(almacen);
		em.flush();
		
		Inventario elemento = new Inventario();
		elemento.setProducto(producto);
		elemento.setAlmacen(almacen);
		elemento.setCantidad(50);
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Inventario inventario;
		
		inventario = new Inventario();
		assertNull(inventario.getId());

		inventario = generaElementoPrueba();
		assertNotNull(inventario.getId());
	}

	@Test
	public void testRead() {
		Inventario elemento1 = generaElementoPrueba();
		Inventario elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getProducto().equals(elemento2.getProducto()));
		assertTrue(elemento1.getAlmacen().equals(elemento2.getAlmacen()));
		assertEquals(elemento1.getCantidad(), elemento2.getCantidad());

		try {
			@SuppressWarnings("unused")
			Inventario elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Inventario original = generaElementoPrueba();
		Inventario clon = new Inventario();
		clon.setId(original.getId());
		clon.setProducto(original.getProducto());
		clon.setAlmacen(original.getAlmacen());
		clon.setCantidad(original.getCantidad());

		original.setCantidad(10);
		sut.update(original);

		Inventario modificado = sut.read(original.getId());
		assertEquals(original.getCantidad(), modificado.getCantidad());
		assertNotEquals(clon.getCantidad(), modificado.getCantidad());
	}

	@Test
	public void testDelete() {
		Inventario elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Inventario resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaElementoPrueba();
		}

		List<Inventario> lista = sut.list();
		assertEquals(numero, lista.size());
	}

}
