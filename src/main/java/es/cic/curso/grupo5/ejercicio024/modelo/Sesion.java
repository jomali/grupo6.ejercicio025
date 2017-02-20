package es.cic.curso.grupo5.ejercicio024.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SESION")
public class Sesion implements Identificable<Long> {
	private static final long serialVersionUID = 2155004191684344293L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia a la sala de la que forma parte la sesión. */
	@JoinColumn(name = "id_sala")
	@ManyToOne(fetch = FetchType.LAZY)
	private Sala sala;

	/** Número de asientos ocupados para la sesión. */
	@Column(name = "asientos_ocupados")
	private int asientosOcupados;

	/** Indica si la sesión está o no abierta. */
	@Column(name = "apertura")
	private boolean abierta;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the sala
	 */
	public Sala getSala() {
		return sala;
	}

	/**
	 * @return the asientosOcupados
	 */
	public int getAsientosOcupados() {
		return asientosOcupados;
	}

	/**
	 * @return Verdadero si la sesión está abierta; falso en caso contrario
	 */
	public boolean isAbierta() {
		return abierta;
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
	 * @param sala
	 *            the sala to set
	 */
	public void setSala(Sala sala) {
		this.sala = sala;
	}

	/**
	 * @param asientosOcupados
	 *            the asientosOcupados to set
	 */
	public void setAsientosOcupados(int asientosOcupados) {
		this.asientosOcupados = asientosOcupados;
	}

	/**
	 * @param abierta
	 *            the abierta to set
	 */
	public void setAbierta(boolean abierta) {
		this.abierta = abierta;
	}

}
