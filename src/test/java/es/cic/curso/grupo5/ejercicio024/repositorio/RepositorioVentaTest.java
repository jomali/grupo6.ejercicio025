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
import es.cic.curso.grupo5.ejercicio024.modelo.Venta;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5/ejercicio024/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioVentaTest {
	
	public static final int CAPACIDAD_SALA = 100;
	public static final short N_ENTRADAS = 2;
	public static final float PRECIO_ENTRADA = 5.0F;

	@Autowired
	private RepositorioVenta sut;

	@PersistenceContext
	protected EntityManager em;

	private Venta generaElementoPrueba() {
		Sala sala = new Sala();
		sala.setCapacidad(CAPACIDAD_SALA);
		em.persist(sala);
		em.flush();
		
		Sesion sesion = new Sesion();
		sesion.setSala(sala);
		em.persist(sesion);
		em.flush();
		
		Venta elemento = new Venta();
		elemento.setSesion(sesion);
		elemento.setDescuento(false);
		elemento.setnEntradas(N_ENTRADAS);
		elemento.setImporte(PRECIO_ENTRADA * N_ENTRADAS);
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Venta venta;
		
		venta = new Venta();
		assertNull(venta.getId());
		
		venta = generaElementoPrueba();
		assertNotNull(venta.getId());
	}

	@Test
	public void testRead() {
		Venta elemento1 = generaElementoPrueba();
		Venta elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertEquals(elemento1.getImporte(), elemento2.getImporte(), 0.0001);
		assertTrue(elemento1.getnEntradas() == elemento2.getnEntradas());
		assertEquals(elemento1.getSesion().getId(), elemento2.getSesion().getId(), 0.0001);

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
		clon.setDescuento(original.isDescuento());
		clon.setId(original.getId());
		clon.setImporte(original.getImporte());
		clon.setnEntradas(original.getnEntradas());
		clon.setSesion(original.getSesion());

		original.setDescuento(true);
		sut.update(original);

		Venta modificado = sut.read(original.getId());
		assertTrue(original.isDescuento() == modificado.isDescuento());
		assertFalse(clon.isDescuento() == modificado.isDescuento());
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
