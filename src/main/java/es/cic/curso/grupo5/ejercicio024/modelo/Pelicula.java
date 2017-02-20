package es.cic.curso.grupo5.ejercicio024.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "PELICULA")
public class Pelicula implements Identificable<Long> {
	private static final long serialVersionUID = 686401216172301952L;

	/** Identificador. Rango de valores: <code>[-2^63, 2^63)</code>. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** Título de la película. */
	@Column(name = "titulo")
	private String titulo;

	/** Director de la película. */
	@Column(name = "director")
	private String director;

	/** Año en que se ha estrenado la película. */
	private int estreno;
	
	/**el actor/actriz principal de la pelicula */
	@Column(name="interprete")
	private String interprete;
	
	/** adivina, genero de la pelicula.*/
	@Column(name="genero")
	private String genero;
	
	/**productora de la película*/
	@Column(name="productora")
	private String productora;
	
	/** duracion de la película*/
	@Column(name="duracion")
	private int duracion;
	
	public Pelicula(){
		super();
	}
	
	public Pelicula(String titulo, String director, String productora, String interprete, int year, int duracion,String genero){
		super();
		this.titulo=titulo;
		this.director=director;
		this.productora=productora;
		this.interprete=interprete;
		this.estreno=year;
		this.duracion=duracion;
		this.genero = genero;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return the annoEstreno
	 */
	public int getEstreno() {
		return estreno;
	}

	/**
	 * @param annoEstreno the annoEstreno to set
	 */
	public void setEstreno(int Estreno) {
		this.estreno = Estreno;
	}

	/**
	 * @return the interprete
	 */
	public String getInterprete() {
		return interprete;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @return the productora
	 */
	public String getProductora() {
		return productora;
	}

	/**
	 * @param interprete the interprete to set
	 */
	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @param productora the productora to set
	 */
	public void setProductora(String productora) {
		this.productora = productora;
	}

	/**
	 * @return the duracion
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * @param duracion the duracion to set
	 */
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	

}
