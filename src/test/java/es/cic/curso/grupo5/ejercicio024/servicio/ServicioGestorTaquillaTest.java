package es.cic.curso.grupo5.ejercicio024.servicio;

import static org.junit.Assert.*;

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

import es.cic.curso.grupo5.ejercicio024.modelo.Sala;
import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo5/ejercicio024/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorTaquillaTest {
	public static final int CAPACIDAD_SALA = 100;
	public static final int ENTRADAS = 10;

	@Autowired
	private ServicioGestorTaquilla sut;

	@PersistenceContext
	private EntityManager em;
	
	private Sesion sesion;
	
	@Before
	public void setUp() {
		Sala sala = new Sala();
		sala.setCapacidad(CAPACIDAD_SALA);
		em.persist(sala);
		em.flush();
		sesion = new Sesion();
		sesion.setSala(sala);
		sesion.setAbierta(true);
		em.persist(sesion);
		em.flush();	
	}

	@Test
	public void testVendeEntradas() {
		sut.vendeEntradas(sesion.getId(), ENTRADAS);
		assertEquals(ServicioGestorTaquillaImpl.PRECIO_ENTRADA * ENTRADAS, sut.calculaRecaudacion(), 0.0001);
		assertEquals(CAPACIDAD_SALA - ENTRADAS, sut.localidadesDisponibles(sesion.getId()));
		
		try {
			sut.vendeEntradas(sesion.getId(), CAPACIDAD_SALA);
			fail("No existían suficientes localidades disponibles.");
		} catch (IndexOutOfBoundsException ioobe) {
			
		}
	}
	
	@Test
	public void testCalculaRecaudacion(){
		sut.vendeEntradas(sesion.getId(), 5);
		sut.vendeEntradas(sesion.getId(), 10);
		
		float importe = sut.calculaRecaudacion();
		
		assertEquals(75.0, importe, 0.001);
	}

	@Test
	public void testCalculaRecaudacionSala(){
		sut.vendeEntradas(sesion.getId(), 7);
		sut.vendeEntradas(sesion.getId(), 8);
		
		Sala sala = sesion.getSala();
		float importe = sut.calculaRecaudacion(sala.getId());
		
		assertEquals(75.0, importe, 0.001);
	}
	
	@Test
	public void testCambiaEstadoSesion(){
		Long id = sesion.getId();
		sut.cambiaEstadoSesion(id, true);
		
		assertTrue(sesion.isAbierta());
		
		sut.cambiaEstadoSesion(id, false);
		boolean estado = sut.estaAbierta(id);
		
		assertFalse(estado);
	}
	
	@Test
	public void testCambiaEstadoTaquilla(){
		sut.cambiaEstadoTaquilla(false);
				
		try{
			sut.vendeEntradas(sesion.getId(), 5);
			fail("No puedes vender entradas, la taquilla esta cerrada");
		}catch(IllegalStateException ise){
			
		}
	}
}
