package com.ecommerce.clientes.controllers;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.dao.DataIntegrityViolationException; // Excepción que lanza Spring al violar constraints de BD (FK, UNIQUE, NOT NULL, etc.)
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class LocalExceptionHandler {
	// Logger para registrar el evento. Usamos java.util.logging por consistencia con el proyecto.
    // LOGGER.getName() devuelve el nombre de la clase para identificar la fuente en los logs.
    private final Logger LOGGER = Logger.getLogger(LocalExceptionHandler.class.getName());

    /*
     * Maneja las violaciones de integridad de datos (p. ej. constraint UNIQUE, FK, NOT NULL).
     *
     * ¿Qué es DataIntegrityViolationException?
     *  - Es una excepción de Spring (org.springframework.dao) que envuelve errores de la capa de persistencia
     *    provocados por violaciones de restricciones en la base de datos.
     *  - Ejemplos: insertar un email duplicado si existe UNIQUE(email), eliminar una fila referenciada por FK, etc.
     *
     * ¿Por qué capturarla aquí?
     *  - Permite devolver al cliente HTTP 400 (o 409 según convención) con un mensaje claro en lugar de un 500 genérico.
     *
     * Detalles del código:
     *  - e.getCause():
     *      - Devuelve la causa real de la excepción (p. ej. SQLException lanzada por el driver).
     *      - Útil para logging/diagnóstico; no conviene exponerla directamente al cliente en producción.
     *      - Ejemplo de posible valor: org.postgresql.util.PSQLException: duplicate key value violates unique constraint "un_email"
     *
     *  - ResponseEntity.badRequest().body(Map.of(...)):
     *      - Construye una respuesta HTTP 400 con un cuerpo tipo Map (serializable a JSON).
     *      - Map.of crea un mapa inmutable con pares clave/valor. Ejemplo resultante JSON:
     *          {"Code":400,"response":"violacion de restriccion: detalle..."}
     *
     * Nota de diseño:
     *  - Puedes cambiar el código devuelto a 409 CONFLICT si prefieres indicar conflicto por integridad.
     *  - Evita enviar stacktraces al cliente; deja detalles en los logs y mensajes genéricos/amigables en la respuesta.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        // Registrar la excepción con nivel SEVERE para facilitar diagnóstico en el log
        LOGGER.log(Level.SEVERE, "Violación de restricción: " + (e.getCause() != null ? e.getCause() : e.getMessage()));
        // Responder con 400 Bad Request y un cuerpo simple indicando el problema
        return ResponseEntity.badRequest().body(Map.of(
            "Code", HttpStatus.BAD_REQUEST.value(),
            "response", "Violación de restricción: " + e.getMessage()
        ));
    }
}
