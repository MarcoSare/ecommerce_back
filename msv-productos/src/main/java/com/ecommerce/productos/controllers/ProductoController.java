package com.ecommerce.productos.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.commons.controllers.CommonController;
import com.ecommerce.commons.dto.ProductoRequest;
import com.ecommerce.commons.dto.ProductoResponse;
import com.ecommerce.productos.services.ProductoService;


@RestController
public class ProductoController extends CommonController<ProductoRequest, ProductoResponse, ProductoService>{

	public ProductoController(ProductoService service) {
		super(service);
	}

	
	
}
