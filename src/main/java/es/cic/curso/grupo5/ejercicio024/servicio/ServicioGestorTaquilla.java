package es.cic.curso.grupo5.ejercicio024.servicio;

import java.util.List;

import es.cic.curso.grupo5.ejercicio024.modelo.Venta;

/**
 * Definición de las operaciones que debe implementar un servicio encargado de
 * manejar la lógica de venta de entradas.
 * 
 * 
 * @author J. Francisco Martín
 * @author Enrique Jara
 * @version 2.0
 * @serial 2017/02/14
 *
 */
public interface ServicioGestorTaquilla {

	/**
	 * Retorna la recaudación de la sala pasada como parámetro.
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return Recaudación de la sala indicada
	 * @throws IllegalArgumentException
	 *             Si no existe una sala con el identificador dado
	 */
	float calculaRecaudacion(Long idSala);

	/**
	 * Retorna la recaudación total de todas las salas del cine.
	 * 
	 * @return Recaudación total de todas las salas de cine
	 */
	float calculaRecaudacion();

	/**
	 * Modifica el estado de apertura de una sesión dada.
	 * 
	 * @param idSesion
	 *            Identificador de la sesión
	 * @param abierta
	 *            Verdadero si la sesión pasa a estar abierta; Falso en caso
	 *            contrario
	 * @throws IllegalArgumentException
	 *             Si no existe una sesión con el identificador dado
	 */
	void cambiaEstadoSesion(Long idSesion, boolean abierta);

	/**
	 * Modifica el estado de apertura para todas las sesiones del cine.
	 * 
	 * @param abierta
	 *            Verdadero si las sesiones pasan a estar abiertas; Falso en
	 *            caso contrario
	 */
	void cambiaEstadoTaquilla(boolean abierta);

	/**
	 * Indica si una sesión dada está o no abierta.
	 * 
	 * @param idSesion
	 *            Identificador de la sesión
	 * @return Verdadero si la sesión indicada está abierta; Falso en caso
	 *         contrario
	 * @throws IllegalArgumentException
	 *             Si no existe una sesión con el identificador dado
	 */
	boolean estaAbierta(Long idSesion);

	/**
	 * Retorna el número de localidades disponibles para la sesión indicada.
	 * 
	 * @param idSesion
	 *            Identificador de la sesión
	 * @return Número de localidades disponibles para la sesión indicada
	 * @throws IllegalArgumentException
	 *             Si no existe una sesión con el identificador dado
	 */
	int localidadesDisponibles(Long idSesion);

	/**
	 * Vende la entrada para la sesión y localidad indicadas.
	 * 
	 * @param idSesion
	 *            Identificador de la sesión
	 * @param idLocalidad
	 *            Identificador de la localidad
	 * @return Precio de la venta
	 * @throws IllegalArgumentException
	 *             Si no existe una sesión con el identificador dado. O si para
	 *             la sesión y localidad indicadas ya se había vendido la
	 *             entrada
	 */
	double vendeEntrada(Long idSesion, Long idLocalidad);

	/**
	 * Vende varias entradas para la sesión indicada.
	 * 
	 * @param idSesion
	 *            Identificador de la sesión
	 * @param nEntradas
	 *            Número de entradas
	 * @return Precio de la venta
	 * @throws IllegalArgumentException
	 *             Si no existe una sesión el el identificador dado
	 * @throws IndexOutOfBoundsException
	 *             Si para la sesión indicada hay menos localidades disponibles
	 *             de las que se intentan vender
	 */
	double vendeEntradas(Long idSesion, int nEntradas);
	
	List<Venta> listaVentas();

}
