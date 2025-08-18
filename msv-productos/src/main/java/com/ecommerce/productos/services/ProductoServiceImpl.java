package com.ecommerce.productos.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.commons.dto.ProductoRequest;
import com.ecommerce.commons.dto.ProductoResponse;
import com.ecommerce.productos.entities.Producto;
import com.ecommerce.productos.mappers.ProductoMapper;
import com.ecommerce.productos.repositories.ProductoRepository;

public class ProductoServiceImpl implements ProductoService{

	private final ProductoRepository repository;
	
	private final ProductoMapper mapper;

	public ProductoServiceImpl(ProductoRepository repository, ProductoMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductoResponse> listar() {
		return repository.findAll().stream()
				.map(mapper::entityToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public ProductoResponse obtenerPorId(Long id) {
		return mapper.entityToResponse(repository.findById(id).orElseThrow());
	}

	@Override
	@Transactional
	public ProductoResponse insertar(ProductoRequest request) {
		return mapper.entityToResponse(repository.save(mapper.requestToEntity(request)));
		
	}

	@Override
	public ProductoResponse actualizar(ProductoRequest request, Long id) {
		Producto producto = repository.findById(id).orElseThrow();
		producto.setNombre(request.nombre());
		producto.setDescripcion(request.descripcion());
		producto.setPrecio(request.precio());
		producto.setStock(request.stock());
		
		return mapper.entityToResponse(repository.save(producto));
	}

	@Override
	@Transactional
	public ProductoResponse eliminar(Long id) {
		Producto producto = repository.findById(id).orElseThrow();
		repository.deleteById(id);
		return mapper.entityToResponse(producto);
	}
	
	
	
}
