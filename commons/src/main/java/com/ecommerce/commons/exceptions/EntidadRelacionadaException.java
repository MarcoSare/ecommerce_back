package com.ecommerce.commons.exceptions;

/*
 * Excepción personalizada para indicar que una entidad no se puede eliminar o modificar
 * por existencia de relaciones (por ejemplo: claves foráneas, referencias en otras tablas).
 *
 * Uso típico:
 *   throw new EntidadRelacionadaException("No se puede eliminar: existen registros relacionados");
 *
 * Se extiende RuntimeException para que sea unchecked: no obliga a declarar 'throws' ni capturarla.
 */
public class EntidadRelacionadaException extends IllegalStateException {

    // serialVersionUID: evita warnings al serializar/deserializar la excepción.
    // No es obligatorio, pero se recomienda declarar uno explícito en clases serializables.
    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje descriptivo.
     *
     * @param mensaje Texto que describe por qué no se puede realizar la operación.
     *
     * Ejemplo:
     *   throw new EntidadRelacionadaException("No se puede eliminar: existen registros relacionados");
     *
     * ¿Qué hace 'super(mensaje)'?
     *  - Llama al constructor de IllegalStateException y guarda el mensaje.
     *  - getMessage() sobre la excepción devolverá este mensaje (útil para logs/respuestas).
     */
    public EntidadRelacionadaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje y causa original.
     *
     * @param mensaje Mensaje descriptivo.
     * @param causa   Excepción original (p.ej. SQLException) que provocó este error.
     *
     * Uso recomendado cuando la causa proviene de la infraestructura (BD, DAO, etc.)
     * para preservar el stack trace original.
     *
     * Ejemplo:
     *   try {
     *       // operación que lanza SQLException por constraints de BD
     *   } catch (SQLException ex) {
     *       throw new EntidadRelacionadaException("No se puede eliminar cliente con ID 5", ex);
     *   }
     */
    public EntidadRelacionadaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}