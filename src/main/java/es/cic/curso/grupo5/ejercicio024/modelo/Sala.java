package es.cic.curso.grupo5.ejercicio024.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SALA")
public class Sala implements Identificable<Long> {
	private static final long serialVersionUID = -3994113697057346082L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Número de localidades de la sala. */
	@Column(name = "capacidad")
	private int capacidad;

	/** Lista de sesiones programadas en la sala. */
	@OneToMany(mappedBy = "sala")
	private List<Sesion> sesiones = new ArrayList<>();

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * @return the sesiones
	 */
	public List<Sesion> getSesiones() {
		return sesiones;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param capacidad
	 *            the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @param sesiones
	 *            the sesiones to set
	 */
	public void setSesiones(List<Sesion> sesiones) {
		this.sesiones = sesiones;
	}

}
