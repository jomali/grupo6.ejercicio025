package es.cic.curso.grupo5.ejercicio024.servicio;

import java.util.List;

import es.cic.curso.grupo5.ejercicio024.modelo.Sala;
import es.cic.curso.grupo5.ejercicio024.modelo.Sesion;

/**
 * Define un conjunto de operaciones CRUD sobre las entidades
 * <strong>Sala</strong> y <strong>Sesion</strong>.
 * 
 * 
 * @author J. Francisco Martín
 * @author Enrique Jara
 * @version 2.0
 * @serial 2017/02/14
 *
 */
public interface ServicioGestorCine {

	/**
	 * Añade una nueva sala al sistema.
	 * 
	 * @param sala
	 *            La sala que se añade al sistema
	 */
	void agregaSala(Sala sala);

	/**
	 * Añade una nueva sesión en el sistema.
	 * 
	 * @param idSala
	 *            Identificador de la sala para la que se añade la sesión nueva
	 * @param sesion
	 *            La sesión que se añade al sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sala
	 *             registrada en el sistema
	 */
	void agregaSesion(Long idSala, Sesion sesion);

	/**
	 * Retorna la sala que se corresponde con el identificador dado como
	 * parámetro.
	 * 
	 * @param id
	 *            Identificador de la sala
	 * @return La sala que se corresponde con el identificador dado
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sala
	 *             registrada en el sistema
	 */
	Sala obtenSala(Long id);

	/**
	 * Retorna la sesión que corresponde con el identificador dado como
	 * parámetro.
	 * 
	 * @param id
	 *            Identificador de la sesión
	 * @return La sesión que se corresponde con el identificador dado
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sesión
	 *             registrada en el sistema
	 */
	Sesion obtenSesion(Long id);

	/**
	 * Reemplaza una sala dada por otra nueva pasada como parámetro. Para
	 * efectuar la operación, la sala original no puede tener sesiones
	 * programadas.
	 * 
	 * @param id
	 *            Identificador de la sala que se desea modificar
	 * @param sala
	 *            Nueva sala que reemplaza la actual del sistema
	 * @return La sala que es reemplazada
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sala
	 *             registrada en el sistema
	 * @throws IllegalStateException
	 *             Si hay sesiones programadas (activas) en esa sala
	 */
	Sala modificaSala(Long id, Sala sala);

	/**
	 * Reemplaza una sesión dada por otra nueva pasada como parámetro.
	 * 
	 * @param id
	 *            Identificador de la sesión que se desea modificar
	 * @param sesion
	 *            Nueva sesión que reemplaza la actual del sistema
	 * @return La sesión que es reemplazada
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sesión
	 *             registrada en el sistema
	 */
	Sesion modificaSesion(Long id, Sesion sesion);

	/**
	 * Elimina la sala que se corresponde con el identificador dado como
	 * parámetro. Sólo se puede efectuar la operación si la sala no tiene
	 * sesiones programadas.
	 * 
	 * @param id
	 *            Identificador de la sala que se desea eliminar
	 * @return La sala que es eliminada
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sala
	 *             registrada en el sistema
	 * @throws IllegalStateException
	 *             Si hay sesiones programadas (activas) en esa sala
	 */
	Sala eliminaSala(Long id);

	/**
	 * Elimina la sesión que se corresponde con el identificador dado como
	 * parámetro.
	 * 
	 * @param id
	 *            Identificador de la sesión que se desea eliminar
	 * @return La sesión que es eliminada
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sesión
	 *             registrada en el sistema
	 */
	Sesion eliminaSesion(Long id);

	/**
	 * Elimina todas las sesiones programadas con la sala que se corresponde con
	 * el identificador dado como parámeto.
	 * 
	 * @param id
	 *            Identificador de la sala de la que se desean eliminar todas
	 *            sus sesiones programadas
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sala
	 *             registrada en el sistema
	 */
	void eliminaSesiones(Long id);

	/**
	 * Retorna una lista con todas las salas registradas en el sistema.
	 * 
	 * @return Lista con todas las salas del sistema
	 */
	List<Sala> listaSalas();

	/**
	 * Retorna una lista con todas las sesiones registradas en el sistema.
	 * 
	 * @return Lista con todas las sesiones del sistema
	 */
	List<Sesion> listaSesiones();

	/**
	 * Retorna una lista con todas las sesiones programadas para una sala dada.
	 * 
	 * @param id
	 *            Identificador de la sala
	 * @return Lista con todas las sesiones programadas para la sala
	 * @throws IllegalArgumentException
	 *             Si el identificador no se corresponde con ninguna sala
	 *             registrada en el sistema
	 */
	List<Sesion> listaSesiones(Long id);

}
