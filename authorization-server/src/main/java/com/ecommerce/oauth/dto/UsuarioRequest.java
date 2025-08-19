package com.ecommerce.oauth.dto;

import java.util.Set;

import jakarta.validation.constraints.*;

public record UsuarioRequest(
		@NotBlank(message = "El username es requerido")
		@Size(min = 5, max = 20, message = "El username debe tener entre 5 y 20 caracteres")
		String username,
		@NotBlank(message = "La password es requerida")
		@Size(min = 8,  message = "La password debe tener al menos 8 caracteres")
		String password,
		@NotNull(message = "Los roles son requeridos")
		@Size(min = 1,  message = "El usuario debe tener al menos 1 rol")
		Set<String> roles
		) {

}
