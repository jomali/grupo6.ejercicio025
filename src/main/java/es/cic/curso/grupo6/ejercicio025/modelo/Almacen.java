package es.cic.curso.grupo6.ejercicio025.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa un espacio físico en el que se pueden almacenar productos, con una
 * cierta capacidad limitada.
 * 
 * 
 * @serial 2017/02/20
 * @version 1.0
 *
 */
@Entity
@Table(name = "ALMACEN")
public class Almacen implements Identificable<Long> {
	private static final long serialVersionUID = 1993977395175319316L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Nombre con que describir al almacén. */
	@Column(name = "nombre")
	private String nombre;

	/** Capacidad total del almacén. */
	@Column(name = "capacidad")
	private int capacidad;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param capacidad the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

}
