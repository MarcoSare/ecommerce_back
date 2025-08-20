package com.ecommerce.clientes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msv-pedidos")
public interface PedidosClients {
	@GetMapping("/id-cliente/{id}")
	int existsClienteById(@PathVariable Long id);
}
