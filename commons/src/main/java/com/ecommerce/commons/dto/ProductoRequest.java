package com.ecommerce.commons.dto;

import jakarta.validation.constraints.*;

public record ProductoRequest(
	
		@NotBlank(message = "El nombre no puede estar vacio o nulo")
		@Size(min = 20, max = 30, message = "El nombre debe tener maximo 30 caracteres")
		String nombre,
		@NotBlank(message = "La descripcion no puede estar vacio o nulo")
		@Size(min = 20, max = 150, message = "La descripcion debe tener maximo 50 caracteres")
		String descripcion,
		@NotNull(message = "El precio es obligatorio")
	    @Positive(message = "El precio debe ser mayor a 0")
		Double precio,
		@NotNull (message = "El stock es requerido")
		@Min(value = 0, message = "El stock debe ser positivo")
		Integer stock
		
		) {

}
