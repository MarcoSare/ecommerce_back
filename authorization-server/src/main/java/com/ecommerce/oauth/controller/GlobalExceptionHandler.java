package com.ecommerce.oauth.controller;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

	// Validar restricciones de anotaciones como @NotNull, @Min, @Max, etc...
	// devuelve una respuesta 400 BadRequest  en vez de que la app truene
			@ExceptionHandler(ConstraintViolationException.class)
			public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) {
				LOGGER.log(Level.WARNING, "Violación de restricción: " +
						(e.getCause() != null ? e.getCause() : e.getMessage()));
				return ResponseEntity.badRequest().body(Map.of(
						"code", HttpStatus.BAD_REQUEST.value(),
						"response", "Violación de restricción: " + e.getMessage()
				));
			}
	
	
			// Validar fallidas en los dto
			@ExceptionHandler(MethodArgumentNotValidException.class)
			public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
				LOGGER.log(Level.WARNING, "Error de validación de argumentos: " + e.getMessage());
				String mensaje = e.getBindingResult().getFieldErrors().stream()
						.map(err -> err.getField() + ": " + err.getDefaultMessage())
						.findFirst()
						.orElse("Error de validación en los datos enviados");
				return ResponseEntity.badRequest().body(Map.of(
						"code", HttpStatus.BAD_REQUEST.value(),
						"response", mensaje
				)); 
			}
			
			// No se encontró el recurso solicitado
			//Devuelve un HTTP 404 (Not Found).
			@ExceptionHandler(NoSuchElementException.class)
			public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException e) {
				LOGGER.log(Level.WARNING, "No se encontró información asociada con el identificador ingresado");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
						"code", HttpStatus.NOT_FOUND.value(),
						"response", "No se encontró información asociada con el identificador ingresado"
				));
			}
			
			// Cualquier otro error que no esté registrado
			//captura cualquier excepción no controlada que herede de Exception
			@ExceptionHandler(Exception.class)
			public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
				LOGGER.log(Level.SEVERE, "Error interno del servidor: " +
					(e.getCause() != null ? e.getCause() : e.getMessage()));
				return ResponseEntity.badRequest().body(Map.of(
					"code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"response", "Error interno del servidor: " + e.getMessage()
				));
			}
	
	
}
