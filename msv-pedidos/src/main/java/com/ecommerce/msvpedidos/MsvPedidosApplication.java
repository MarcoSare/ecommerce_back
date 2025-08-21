package com.ecommerce.msvpedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.ecommerce.msvpedidos", "com.ecommerce.commons"})
@EnableFeignClients
public class MsvPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvPedidosApplication.class, args);
	}

}
