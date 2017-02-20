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

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5/ejercicio024/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class RepositorioPeliculaTest {
	
	@Autowired
	private RepositorioPelicula sut;

	private Pelicula generaElementoPrueba() {
		Pelicula elemento = new Pelicula();
		elemento.setTitulo(" Viaje a la Luna");
		elemento.setEstreno(1902);
		elemento.setDirector("Georges Méliès");
		sut.create(elemento);
		return elemento;
	}

	@Test
	public void testCreate() {
		Pelicula pelicula;
		
		pelicula = new Pelicula();
		assertNull(pelicula.getId());

		pelicula = generaElementoPrueba();
		assertNotNull(pelicula.getId());
	}

	@Test
	public void testRead() {
		Pelicula elemento1 = generaElementoPrueba();
		Pelicula elemento2 = sut.read(elemento1.getId());

		assertTrue(elemento1.getId().equals(elemento2.getId()));
		assertTrue(elemento1.getTitulo().equals(elemento2.getTitulo()));
		assertTrue(elemento1.getDirector().equals(elemento2.getDirector()));
		assertEquals(elemento1.getEstreno(), elemento2.getEstreno());

		try {
			@SuppressWarnings("unused")
			Pelicula elemento3 = sut.read(Long.MIN_VALUE);
			fail("No deberían existir elementos con el ID pasado");
		} catch (PersistenceException pe) {

		}
	}

	@Test
	public void testUpdate() {
		Pelicula original = generaElementoPrueba();
		Pelicula clon = new Pelicula();
		clon.setId(original.getId());
		clon.setTitulo(original.getTitulo());
		clon.setDirector(original.getDirector());
		clon.setEstreno(original.getEstreno());
		clon.setDuracion(original.getDuracion());
		clon.setGenero(original.getGenero());
		clon.setInterprete(original.getInterprete());
		clon.setProductora(original.getProductora());

		original.setEstreno(2017);
		sut.update(original);

		Pelicula modificado = sut.read(original.getId());
		assertTrue(original.getEstreno() == modificado.getEstreno());
		assertFalse(clon.getEstreno() == modificado.getEstreno());
	}

	@Test
	public void testDelete() {
		Pelicula elemento = generaElementoPrueba();
		sut.delete(elemento.getId());

		Pelicula resultado = sut.read(elemento.getId());
		assertNull(resultado);
	}

	@Test
	public void testList() {
		int numero = 5;
		for (int i = 0; i < numero; i++) {
			generaElementoPrueba();
		}

		List<Pelicula> lista = sut.list();
		assertEquals(numero, lista.size());
	}

}
