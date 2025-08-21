package com.ecommerce.msvpedidos.controllers;

import com.ecommerce.commons.controllers.CommonController;
import com.ecommerce.commons.dto.PedidosRequest;
import com.ecommerce.commons.dto.PedidosResponse;
import com.ecommerce.msvpedidos.services.PedidosService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PedidosController extends CommonController<PedidosRequest, PedidosResponse, PedidosService>{

	public PedidosController(PedidosService service) {
		super(service);
	}

	@GetMapping("/id-cliente/{id}")
	public ResponseEntity<Integer> existsClienteById(@PathVariable Long id) {
		int count = service.countByClienteId(id);
		return ResponseEntity.ok(count);
	}
	
	@GetMapping("/id-producto/{id}")
	public boolean productoIsPresent(@PathVariable Long id) {
		return service.productoIsPresent(id);
	}

	@PatchMapping("/estado/{estado}/{id}")
	public PedidosResponse estado(@PathVariable String estado, @PathVariable Long id) {
		return service.cambiarEstado(estado, id);
	}
	
}
