package es.cic.curso.grupo6.ejercicio025.servicio;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import es.cic.curso.grupo6.ejercicio025.modelo.Almacen;
import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;
import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:es/cic/curso/grupo6/ejercicio025/applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@Transactional
public class ServicioGestorInventarioTest {

	@Autowired
	private ServicioGestorInventario sut;

	@PersistenceContext
	private EntityManager em;

	private Producto producto;

	private Almacen almacen;

	// /////////////////////////////////////////////////////////////////////////

	@Before
	public void setUp() {
		producto = new Producto();
		producto.setNombre("Galletas");
		producto.setPrecio(2.0F);
		almacen = new Almacen();
		almacen.setNombre("Almacén");
		almacen.setCapacidad(100);

		em.persist(producto);
		em.persist(almacen);
		em.flush();
	}

	// /////////////////////////////////////////////////////////////////////////

	@Test
	public void testObtenEntradaInventario1() {
		Inventario inventario;
		
		// 1) Obtener una entrada incorrecta
		try {
			inventario = sut.obtenEntradaInventario(0L, 0L);
			fail("IDs de producto y almacén incorrectos");
		} catch (IllegalArgumentException iae) {
			
		}

		// 2) Obtener una entrada no registrada en BB.DD.
		try {
			inventario = sut.obtenEntradaInventario(producto.getId(), almacen.getId());
			fail("Entrada no registrada en BB.DD.");
		} catch (NoResultException e) {
			
		}
		
		// 3) Obtener una entrada registrada en BB.DD.
		Inventario i = new Inventario();
		i.setProducto(producto);
		i.setAlmacen(almacen);
		i.setCantidad(10);
		em.persist(i);
		em.flush();
		inventario = sut.obtenEntradaInventario(producto.getId(), almacen.getId());
		assertNotNull(inventario);
		assertEquals(10, inventario.getCantidad());
	}

	@Test
	public void testObtenEntradaInventario2() {
		assertTrue(true);
	}

	@Test
	public void obtenEstableceCantidadProductos() {
		assertTrue(true);
	}

	@Test
	public void modificaCantidadProductos() {
		assertTrue(true);
	}

}
