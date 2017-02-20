package es.cic.curso.grupo6.ejercicio025.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Representa una venta de un tipo de producto.
 * 
 * 
 * @serial 2017/02/20
 * @version 3.0
 *
 */
@Entity
public class Venta implements Identificable<Long> {
	private static final long serialVersionUID = -5612199016136752211L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** Referencia al tipo de producto. */
	@JoinColumn(name = "id_producto")
	@OneToMany(fetch = FetchType.LAZY)
	private Producto producto;

	/** Número de unidades vendidas. */
	@Column(name = "unidades")
	private int unidades;

	/** Precio total de la venta. */
	@Column(name = "importe")
	private float importe;

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @return the unidades
	 */
	public int getUnidades() {
		return unidades;
	}

	/**
	 * @return the importe
	 */
	public float getImporte() {
		return importe;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * @param unidades the unidades to set
	 */
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(float importe) {
		this.importe = importe;
	}

}
