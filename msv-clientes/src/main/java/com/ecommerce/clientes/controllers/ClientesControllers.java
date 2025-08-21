package com.ecommerce.clientes.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.clientes.services.ClienteService;
import com.ecommerce.commons.controllers.CommonController;
import com.ecommerce.commons.dto.ClientesRequest;
import com.ecommerce.commons.dto.ClientesResponse;

@RestController
public class ClientesControllers extends CommonController<ClientesRequest, ClientesResponse, ClienteService> {
	public ClientesControllers(ClienteService service) {
		// TODO Auto-generated constructor stub
		super(service);
	}
}
