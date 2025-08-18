package com.ecommerce.commons.controllers;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.commons.exceptions.EntidadRelacionadaException;

import feign.FeignException;
import feign.RetryableException;
import jakarta.validation.ConstraintViolationException;

// Responda en caso de que haya excepciones (manejo global para controladores REST)
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());
    
    // Validar restricciones de anotaciones como @NotNull, @Min, @Max, @Pattern, etc...
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) {
        // Registrar advertencia con detalle. e.getMessage() contiene el mensaje de las violaciones.
        LOGGER.log(Level.WARNING, "Violación de restricción: " + (e.getCause() != null ? e.getCause() : e.getMessage()));
        // Devuelve 400 con un cuerpo simple. La clave "code" indica el status y "response" el mensaje.
        return ResponseEntity.badRequest().body(Map.of(
                "code", HttpStatus.BAD_REQUEST.value(),
                "response", "Violación de restricción: " + e.getMessage()
        ));
    } 
    
    // Maneja errores de validación cuando se usa @Valid sobre @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // Registro del error para debugging
        LOGGER.log(Level.WARNING, "ERROR de validación de argumentos: " + e.getMessage());
        
        /*
         * e.getBindingResult().getFieldErrors():
         *  - Devuelve List<FieldError> con los errores de cada campo validados.
         *  - Cada FieldError contiene: getField() (nombre del campo), getDefaultMessage() (mensaje de la anotación),
         *    getRejectedValue() (valor recibido), etc.
         *  - Aquí tomamos el primer error (findFirst) para devolver un mensaje conciso.
         *  - Ejemplo de retorno de getFieldErrors(): [{field: "email", defaultMessage: "El email no es válido"}, ...]
         */
        String mensaje = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");
        
        // Responde 400 con el primer detalle de validación
        return ResponseEntity.badRequest().body(Map.of(
                "code", HttpStatus.BAD_REQUEST.value(),
                "response", mensaje
        ));
    } 
    
    // No se encontró el recurso solicitado (por ejemplo Optional.get() en vacío)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException e) {
        LOGGER.log(Level.WARNING, "No se encontró información asociada con el identificador");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "code", HttpStatus.NOT_FOUND.value(), 
                "response", "No se encontró información asociada con el identificador"
        )); 
    }
    
    // Excepción personalizada para conflictos por entidades relacionadas (FK, integridad referencial)
    @ExceptionHandler(EntidadRelacionadaException.class)
    public ResponseEntity<Map<String, Object>> handleEntidadRelacionadaException(EntidadRelacionadaException e) {
        LOGGER.log(Level.WARNING, "Error al eliminar/operar el recurso: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "code", HttpStatus.CONFLICT.value(),
                "response", e.getMessage()
        ));
    }

    // Excepciones Feign genéricas (errores al llamar a otros servicios remotos)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleGenericFeignException(FeignException e) {
        // FeignException.getMessage() incluye info útil; e.status() devuelve el código HTTP remoto si existe.
        LOGGER.log(Level.SEVERE, "Error en la comunicación Feign: " + e.getMessage());
        
        // e.status() puede ser -1 o 0 si Feign no obtuvo respuesta; en tal caso usamos 500 como fallback.
        int status = e.status() > 0 ? e.status() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        
        // Mapeamos códigos HTTP remotos a mensajes más claros para el cliente
        String message = switch (status) {
            case 400 -> "Solicitud incorrecta al servicio remoto.";
            case 401 -> "No autorizado para acceder al servicio remoto.";
            case 403 -> "Acceso prohibido al servicio remoto.";
            case 404 -> "Recurso no encontrado en el servicio remoto.";
            case 503 -> "Servicio remoto no disponible.";
            default -> "Error al comunicarse con el servicio remoto.";
        };
        
        // Devolvemos el mismo código que devolvió el servicio remoto (o 500 si no hay código).
        return ResponseEntity.status(status).body(Map.of(
                "code", status,
                "response", message
        ));
    }

    // Cuando el servicio remoto no responde o está caído (timeout/reintentos desde Feign)
    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<Map<String, Object>> handleRetryable(RetryableException e) {
        LOGGER.log(Level.SEVERE, "Servicio remoto no disponible o no responde: " + e.getMessage());
        // Devolvemos 503 SERVICE_UNAVAILABLE para indicar que el servicio dependiente está caído
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "code", HttpStatus.SERVICE_UNAVAILABLE.value(),
                "response", "El servicio remoto no está disponible o no responde en este momento."
        ));
    }
    
    // Cualquier otro error que no esté registrado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        LOGGER.log(Level.SEVERE, "Error interno del servidor: " + (e.getCause() != null ? e.getCause() : e.getMessage()));
        // Devolvemos 500 con el mensaje de la excepción (evita exponer detalles sensibles en producción)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "response", "Error interno del servidor: " + e.getMessage()
        ));
    }
}