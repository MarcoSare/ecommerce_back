package com.ecommerce.clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.ecommerce.clientes", "com.ecommerce.commons"})
public class MsvClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvClientesApplication.class, args);
	}

}
