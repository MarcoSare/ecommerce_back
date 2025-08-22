package com.ecommerce.commons.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientesRequest (
		//No cadenas en blanco o vacias
		@NotBlank(message = "El nombre no puede ir en blanclo o vacio")
		//Numero maximo de caracteres:
		@Size(min = 3, max = 50, message = "El nombre debe tener no menos de 3 caracteres y no mas de 50")
		String nombre,
		
		@NotBlank(message = "El apellido no debe de ir vacio ni en blanco")
		@Size(min = 1, max = 50, message = "El appellido debe de tener no menos de 1 caracter y no mas de 50")
		String apellido,
		
		@NotBlank(message = "El email no debe de ir en blanco ni vacio")
		@Size(min = 1, max = 50, message = "El numero de caractereres del email no debe de ser menor que 1, ni mayor que 50")
		@Email(message = "El formato del email no es válido")
		@Pattern(regexp = "^[^\\s]+@[^\\s]+\\.[^\\s]+$", 
		         message = "El email no puede contener espacios")
		String email,
		
		@NotBlank(message = "El numero de telefono no puede estar vacio ni nulo")
		@Size(min = 10, max = 10, message = "El numero de dijitos del numero de telefono debe de ser de 10")
		@Pattern(regexp = "^[0-9]{10}$") // se permiten numero del 0 al 9 y se permiten 10 dijitos
		String telefono,

		@Size(min = 10, max = 100, message = "La direccion debe contener al menos 10 caracteres y como maximo 100")
		String direccion
) { }
