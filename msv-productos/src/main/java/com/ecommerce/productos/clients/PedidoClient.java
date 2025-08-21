package com.ecommerce.productos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.commons.configuration.FeignClientConfig;

@FeignClient(name = "msv-pedidos", configuration = FeignClientConfig.class)
public interface PedidoClient {
	
	
	@GetMapping("/id-producto/{id}")
	boolean productoIsPresent(@PathVariable Long id);

}
