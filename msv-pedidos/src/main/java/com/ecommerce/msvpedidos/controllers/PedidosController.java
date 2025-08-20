package com.ecommerce.msvpedidos.controllers;

import com.ecommerce.commons.controllers.CommonController;
import com.ecommerce.commons.dto.PedidosRequest;
import com.ecommerce.commons.dto.PedidosResponse;
import com.ecommerce.msvpedidos.services.PedidosService;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class PedidosController extends CommonController<PedidosRequest, PedidosResponse, PedidosService>{

	public PedidosController(PedidosService service) {
		super(service);
	}

	
	
}
