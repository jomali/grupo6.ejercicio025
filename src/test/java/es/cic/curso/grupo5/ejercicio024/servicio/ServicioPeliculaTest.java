package es.cic.curso.grupo5.ejercicio024.servicio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import es.cic.curso.grupo5.ejercicio024.modelo.Pelicula;
import es.cic.curso.grupo5.ejercicio024.repositorio.RepositorioPelicula;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations={
				"classpath:es/cic/curso/grupo5/ejercicio024/applicationContext.xml"
				})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
public class ServicioPeliculaTest {

	@Autowired
	private ServicioPelicula sut;
	
	@Autowired
	private RepositorioPelicula peliculaRepo;
	
	@PersistenceContext
	private EntityManager em;
	
	private String titulo;
	private String director;
	private String productora;
	private String interprete;
	private String genero;
	private int year;
	private int duracion;
	
	@Before
	public void setUp() throws Exception {
		limpiarPeliculas();
		
		titulo = "Pain & Gain";
		director = "Michael Bay";
		productora = "universal";
		interprete = "La Roca";
		year = 2013;
		duracion = 129;
		genero = "Crimen";
	}

	@Test
	public void testAniadirPelicula() {
		 Long peliId = sut.aniadirPelicula(titulo, director, productora, interprete, year, duracion, genero);
		 
		 List<Pelicula>p = sut.obtenerPeliculas();
		 assertNotNull(p);
		 assertNotNull(peliId);
	}
	
	@Test
	public void testObtenerPelicula(){
		Pelicula peli = new Pelicula();
		
		peli.setTitulo("Tomates verdes fritos");
		peli.setEstreno(1991);
		em.persist(peli);
		em.flush();
		Long peliId = peli.getId();
		
		sut.obtenerPelicula(peliId);
		
		assertNotNull(peli.getId());
		assertTrue(peli.getEstreno()==1991);
	}
	
	@Test
	public void testObtenerPeliculas(){
		int n= 4;
		
		for (int i = 0; i<n;i++){
			Pelicula p = new Pelicula();
			p.setEstreno(2000);
			peliculaRepo.create(p);
		}
		List<Pelicula> peliculas = sut.obtenerPeliculas();
		assertEquals(n,peliculas.size());
	}
	@Test
	public void testActualizarPeliculas(){
		Long peliId = sut.aniadirPelicula(titulo, director, productora, interprete, year, duracion, genero);

		Pelicula pMod = sut.obtenerPelicula(peliId);		
		pMod.setInterprete("Mark Whalber");
		
		Pelicula p = sut.obtenerPelicula(peliId);
		
		assertFalse(p.getInterprete() != pMod.getInterprete());
		
	}
	
	@Test
	public void testBorrarPelicula(){
		Long peliId = sut.aniadirPelicula(titulo, director, productora, interprete, year, duracion, genero);
		
		sut.borrarPelicula(peliId);
		
		List<Pelicula> pelis = sut.obtenerPeliculas();
		assertTrue(pelis.size() ==0);
	}

	
	private void limpiarPeliculas(){
		List<Pelicula> peliculas = sut.obtenerPeliculas();
		for (Pelicula p:peliculas){
			peliculaRepo.delete(p);
		}
	}
}
