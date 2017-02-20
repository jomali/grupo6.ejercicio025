package es.cic.curso.grupo5.ejercicio024.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;

@Repository
@Transactional
public class RepositorioSesionImpl extends RepositorioAbstractoImpl<Long, Sesion> implements RepositorioSesion {

	@Override
	public Class<Sesion> obtenClaseT() {
		return Sesion.class;
	}

	@Override
	public String obtenNombreTabla() {
		return Sesion.class.getSimpleName().toUpperCase();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sesion> listSesionesSala(Long idSala) {
		return entityManager.createNativeQuery("SELECT * FROM SESION WHERE id_sala = ?", obtenClaseT())
				.setParameter(1, idSala).getResultList();
	}

	@Override
	public void deleteSesionesSala(Long idSala) {
		entityManager.createNativeQuery("DELETE FROM SESION WHERE id_sala = ?").setParameter(1, idSala).executeUpdate();
	}

}
