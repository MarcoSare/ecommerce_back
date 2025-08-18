package com.ecommerce.productos.mappers;

import com.ecommerce.commons.dto.ProductoRequest;
import com.ecommerce.commons.dto.ProductoResponse;
import com.ecommerce.commons.mappers.CommonMapper;
import com.ecommerce.productos.entities.Producto;

public class ProductoMapper extends CommonMapper<ProductoRequest, ProductoResponse, Producto>{

	
	
	
	@Override
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
