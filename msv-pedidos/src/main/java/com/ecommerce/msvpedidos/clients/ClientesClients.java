package com.ecommerce.msvpedidos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.commons.configuration.FeignClientConfig;
import com.ecommerce.commons.dto.ClientesResponse;

@FeignClient(name = "msv-clientes", configuration = FeignClientConfig.class)
public interface ClientesClients {
	@GetMapping("/{id}")
	ClientesResponse getClienteById(@PathVariable Long id);
}
