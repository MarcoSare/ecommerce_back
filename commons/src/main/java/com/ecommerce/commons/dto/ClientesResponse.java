package com.ecommerce.commons.dto;

public record ClientesResponse (
		Long id,
		String nombre,
		String apellido,
		String email,
		String telefono,
		String direccion
){}
