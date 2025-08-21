package com.ecommerce.msvpedidos.services;

import com.ecommerce.commons.dto.PedidosRequest;
import com.ecommerce.commons.dto.PedidosResponse;

import com.ecommerce.commons.services.CommonService;

public interface PedidosService extends CommonService<PedidosRequest, PedidosResponse>{
	int countByClienteId(Long clienteId);
	
	
	boolean productoIsPresent(Long id);

	PedidosResponse cambiarEstado(String estado,Long id);
	
	
	
}
