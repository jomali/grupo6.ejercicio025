package es.cic.curso.grupo6.ejercicio025.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Representa un producto.
 * 
 * 
 * @serial 2017/02/20
 * @version 3.0
 *
 */
@Entity
public class Producto implements Identificable<Long> {
	private static final long serialVersionUID = -2585317617874092926L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** Nombre del producto. */
	@Column(name = "nombre")
	private String nombre;
	
	/** Precio del producto. */
	@Column(name = "precio")
	private float precio;

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
	 * @return the precio
	 */
	public float getPrecio() {
		return precio;
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
	 * @param precio the precio to set
	 */
	public void setPrecio(float precio) {
		this.precio = precio;
	}

}
