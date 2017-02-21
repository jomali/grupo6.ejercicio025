package es.cic.curso.grupo6.ejercicio025.repositorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio025.modelo.Inventario;

@Repository
@Transactional
public class RepositorioInventarioImpl extends RepositorioAbstractoImpl<Long, Inventario>
		implements RepositorioInventario {

	@Override
	public Class<Inventario> obtenClaseT() {
		return Inventario.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Inventario.class.getSimpleName().toUpperCase();
	}

	@Override
	public Inventario read(Long idProducto, Long idAlmacen) {
		Inventario resultado = null;
		try {
			resultado = (Inventario) entityManager
					.createNativeQuery("SELECT * FROM INVENTARIO WHERE id_producto = ? AND id_almacen = ?", obtenClaseT())
					.setParameter(1, idProducto).setParameter(2, idAlmacen).getSingleResult();
		} catch (Exception e) {
			
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inventario> listByAlmacen(Long idAlmacen) {
		List<Inventario> resultado = new ArrayList<>();
		try {
			resultado = entityManager.createNativeQuery("SELECT * FROM INVENTARIO WHERE id_almacen = ?", obtenClaseT())
					.setParameter(1, idAlmacen).getResultList();
		} catch (Exception e) {

		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inventario> listByProducto(Long idProducto) {
		List<Inventario> resultado = new ArrayList<>();
		try {
			resultado = entityManager.createNativeQuery("SELECT * FROM INVENTARIO WHERE id_producto = ?", obtenClaseT())
					.setParameter(1, idProducto).getResultList();
		} catch (Exception e) {

		}
		return resultado;
	}

}
