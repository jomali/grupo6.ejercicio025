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
@Table(name = "VENTA")
public class Venta implements Identificable<Long> {
	private static final long serialVersionUID = 2446020181181737934L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia a la sesión. */
	@JoinColumn(name = "id_sesion")
	@ManyToOne(fetch = FetchType.EAGER)
	private Sesion sesion;

	/** Número de entradas vendidas. */
	@Column(name = "numero_entradas")
	private short nEntradas;

	/** Importe de la venta. */
	@Column(name = "importe")
	private float importe;

	/** Indica si se aplica o no descuento. */
	@Column(name = "descuento")
	private boolean descuento;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the sesion
	 */
	public Sesion getSesion() {
		return sesion;
	}

	/**
	 * @return the nEntradas
	 */
	public short getnEntradas() {
		return nEntradas;
	}

	/**
	 * @return the importe
	 */
	public float getImporte() {
		return importe;
	}

	/**
	 * @return the descuento
	 */
	public boolean isDescuento() {
		return descuento;
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
	 * @param sesion
	 *            the sesion to set
	 */
	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	/**
	 * @param nEntradas
	 *            the nEntradas to set
	 */
	public void setnEntradas(short nEntradas) {
		this.nEntradas = nEntradas;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(float importe) {
		this.importe = importe;
	}

	/**
	 * @param descuento
	 *            the descuento to set
	 */
	public void setDescuento(boolean descuento) {
		this.descuento = descuento;
	}

}
