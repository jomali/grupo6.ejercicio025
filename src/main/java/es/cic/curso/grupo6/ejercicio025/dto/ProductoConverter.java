package es.cic.curso.grupo6.ejercicio025.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.cic.curso.grupo6.ejercicio025.modelo.Producto;

@Service
public class ProductoConverter {
	public ProductoDTO entity2DTO(Producto producto) {
		ProductoDTO resultado = new ProductoDTO();
		resultado.setId(producto.getId());
		resultado.setNombre(producto.getNombre());
		resultado.setPrecio(producto.getPrecio());
		return resultado;
	}
	
	public Producto DTO2Entity(ProductoDTO productoDTO) {
		Producto resultado = new Producto();
		resultado.setId(productoDTO.getId());
		resultado.setNombre(productoDTO.getNombre());
		resultado.setPrecio(productoDTO.getPrecio());
		return resultado;		
	}
	
	public List<ProductoDTO> entity2DTO(List<Producto> productos) {
		List<ProductoDTO> resultado = new ArrayList<ProductoDTO>();
		for(Producto producto: productos) {
			resultado.add(entity2DTO(producto));
		}
		return resultado;
	}
	
	public List<Producto> DTO2Entity(List<ProductoDTO> productosDTO) {
		List<Producto> resultado = new ArrayList<Producto>();
		for(ProductoDTO producto: productosDTO) {
			resultado.add(DTO2Entity(producto));
		};
		return resultado;		
	}	
}
