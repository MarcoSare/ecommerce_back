package com.ecommerce.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication(scanBasePackages  = {"com.ecommerce.productos", "com.ecommerce.commons" })
public class MsvProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvProductosApplication.class, args);
	}

}
