package es.cic.curso.grupo6.ejercicio025.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INVENTARIO")
public class Inventario implements Identificable<Long> {
	private static final long serialVersionUID = -4368875676419421652L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** Referencia al tipo de producto almacenado en inventario. */
	@JoinColumn(name = "id_producto")
	@OneToOne(fetch = FetchType.LAZY)
	private Producto producto;

	/** Referencia al tipo de producto almacenado en inventario. */
	@JoinColumn(name = "id_almacen")
	@OneToOne(fetch = FetchType.LAZY)
	private Almacen almacen;

	/** Cantidad de productos del tipo <code>producto</code> almacenados. */
	@Column(name = "cantidad")
	private int cantidad;

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
	 * @return the almacen
	 */
	public Almacen getAlmacen() {
		return almacen;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
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
	 * @param almacen the almacen to set
	 */
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
