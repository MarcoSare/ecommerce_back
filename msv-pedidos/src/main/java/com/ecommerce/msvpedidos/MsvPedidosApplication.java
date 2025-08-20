package com.ecommerce.msvpedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ecommerce.msvpedidos.clients")
public class MsvPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvPedidosApplication.class, args);
	}

}
