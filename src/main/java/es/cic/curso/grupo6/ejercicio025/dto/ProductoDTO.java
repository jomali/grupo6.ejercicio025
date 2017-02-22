package es.cic.curso.grupo6.ejercicio025.dto;


public class ProductoDTO {

	private Long id;
	
	private String nombre;
	
	private float precio;

	private String inventario1;
	
	private String inventario2;
	
	/**
	 * @return the id
	 */
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

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
	}

	public String getInventario1() {
		return inventario1;
	}

	public void setInventario1(String inventario1) {
		this.inventario1 = inventario1;
	}

	public String getInventario2() {
		return inventario2;
	}

	public void setInventario2(String inventario2) {
		this.inventario2 = inventario2;
	}
}
