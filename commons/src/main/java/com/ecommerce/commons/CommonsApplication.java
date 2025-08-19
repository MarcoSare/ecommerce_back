package com.ecommerce.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/// Cambio para ver que show
@SpringBootApplication
@EnableFeignClients
public class CommonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonsApplication.class, args);
	}

}
