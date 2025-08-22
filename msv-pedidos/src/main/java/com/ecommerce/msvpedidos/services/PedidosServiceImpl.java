package com.ecommerce.msvpedidos.services;
import com.ecommerce.commons.dto.PedidosRequest;
import com.ecommerce.commons.dto.PedidosResponse;
import com.ecommerce.msvpedidos.clients.ProductoClient;
import com.ecommerce.msvpedidos.entities.Pedido;
import com.ecommerce.msvpedidos.entities.ProductoPedido;
import com.ecommerce.msvpedidos.mappers.PedidoMapper;
import com.ecommerce.msvpedidos.repositories.PedidosRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidosServiceImpl implements PedidosService{

    private final PedidosRepository repository;
	private final PedidoMapper mapper;
	private final ProductoClient  productoClient;

	public PedidosServiceImpl(PedidosRepository repository, PedidoMapper mapper, ProductoClient productoClient) {
		super();
		this.repository = repository;
		this.mapper = mapper;
        this.productoClient = productoClient;
    }

	@Override
	@Transactional(readOnly = true)
	public List<PedidosResponse> listar() {
		return repository.findAll().stream()
				.map(mapper::entityToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public PedidosResponse obtenerPorId(Long id) {
		return mapper.entityToResponse(repository.findById(id).orElseThrow());
	}

	@Override
	@Transactional
	public PedidosResponse insertar(PedidosRequest request) {
		return mapper.entityToResponse(repository.save(mapper.requestToEntity(request)));
	}

	@Override
	public PedidosResponse actualizar(PedidosRequest request, Long id) {
		Pedido pedido = repository.findById(id).orElseThrow();

		pedido.setEstado(request.estado());
		//pedido.setFechaCreacion(request.fechaCreacion());
		pedido.setIdCliente(request.idCliente());

		Set<ProductoPedido> nuevosProductos = request.productos().stream().map(item -> {
			var productoResponse = productoClient.getProductoById(item.idProducto());
			ProductoPedido pp = new ProductoPedido();
			pp.setPedido(pedido);
			pp.setIdProducto(productoResponse.id());
			pp.setCantidad(item.cantidad());
			pp.setPrecio(item.precio());

			return pp;
		}).collect(Collectors.toSet());


		pedido.getProductos().clear();
		pedido.getProductos().addAll(nuevosProductos);

		Pedido updated = repository.save(pedido);
		return mapper.entityToResponse(updated);
	}

	@Override
	@Transactional
	public PedidosResponse eliminar(Long id) {
		Pedido pedido = repository.findById(id).orElseThrow();
		repository.deleteById(id);
		return mapper.entityToResponse(pedido);
	}
	
	
	@Override
	public int countByClienteId(Long id) {
		return repository.existsByIdCliente(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean productoIsPresent(Long id) {

		return repository.existsByIdProducto(id)>0;
	}

	@Override
	@Transactional
	public PedidosResponse cambiarEstado(String estado,Long id) {
		Pedido pedido = repository.findById(id).orElseThrow();
		pedido.setEstado(estado);
		Pedido updated = repository.save(pedido);
		return mapper.entityToResponse(updated);
	}

}
