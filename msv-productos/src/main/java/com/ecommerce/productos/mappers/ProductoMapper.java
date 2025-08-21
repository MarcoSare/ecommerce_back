package com.ecommerce.productos.mappers;

import org.springframework.stereotype.Component;

import com.ecommerce.commons.dto.ProductoRequest;
import com.ecommerce.commons.dto.ProductoResponse;
import com.ecommerce.commons.mappers.CommonMapper;
import com.ecommerce.productos.entities.Producto;

@Component
public class ProductoMapper extends CommonMapper<ProductoRequest, ProductoResponse, Producto>{


	@Override
	
	//lo que hace es convertir un objeto de tipo ProductoRequest en una entidad Producto.
	public Producto requestToEntity(ProductoRequest request) {
		if (request == null) {
			return null;
		}
		
		Producto producto = new Producto();
		producto.setNombre(request.nombre());
		producto.setDescripcion(request.descripcion());
		producto.setPrecio(request.precio());
		producto.setStock(request.stock());
		
		return producto;
	}

	//transforma un objeto Producto (entidad de base de datos)
	//en un objeto ProductoResponse (DTO de salida).
	@Override
	public ProductoResponse entityToResponse(Producto entity) {
		if (entity == null) {
			return null;
		}
		ProductoResponse response = new ProductoResponse(
				entity.getId(),
				entity.getNombre(),
				entity.getDescripcion(),
				entity.getPrecio(),
				entity.getStock()
				);
		return response;
	}

}
