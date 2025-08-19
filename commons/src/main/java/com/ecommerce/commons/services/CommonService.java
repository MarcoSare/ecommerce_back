// ...existing code...
package com.ecommerce.commons.services;

import java.util.List;

/**
 * Interfaz genérica de servicio con operaciones CRUD básicas.
 *
 * RQ -> tipo del DTO de entrada (request), p. ej. ClientesRequest
 * RS -> tipo del DTO de salida (response), p. ej. ClientesResponse
 *
 * NOTAS:
 * - Se deja la lógica concreta a la implementación del microservicio
 *   (p. ej. msv-clientes implementará CommonService<ClientesRequest, ClientesResponse>).
 * - Se recomienda que las implementaciones lancen excepciones de negocio
 *   (NoSuchElementException, EntidadRelacionadaException, etc.) para que el
 *   GlobalExceptionHandler las transforme en respuestas HTTP adecuadas.
 */
public interface CommonService<RQ, RS> {

    /**
     * Lista todos los recursos.
     *
     * ¿Qué devuelve?
     * - List<RS>: lista de DTOs de respuesta (puede estar vacía si no hay datos).
     *
     * Uso típico:
     * - controller -> service.listar() -> devuelve lista mapeada desde entidades.
     *
     * Ejemplo de retorno:
     * - []  (lista vacía) o [ClientesResponse{id:1,...}, ClientesResponse{id:2,...}]
     */
    List<RS> listar();

    /**
     * Inserta un nuevo recurso a partir del DTO de entrada.
     *
     * Parámetros:
     * - RQ request: DTO validado recibido en el controlador (p. ej. @Valid ClientesRequest).
     *
     * ¿Qué hace internamente en la implementación?
     * - 1) mapear request -> entidad
     * - 2) persistir entidad (repo.save(entity))
     * - 3) mapear entidad guardada -> RS y devolverla
     *
     * ¿Qué devuelve?
     * - RS: DTO de respuesta con los datos guardados (incluyendo id generado).
     *
     * Errores comunes:
     * - Violar constraints DB -> lanzar excepción (DataIntegrityViolationException)
     * - Email duplicado -> lanzar excepción de negocio (EntidadRelacionadaException / custom)
     */
    RS insertar(RQ request);

    /**
     * Actualiza un recurso existente identificado por id usando los datos del request.
     *
     * Parámetros:
     * - RQ request: DTO con nuevos valores (validado).
     * - Long id: identificador del recurso a actualizar.
     *
     * Comportamiento esperado en la implementación:
     * - Buscar entidad por id (repo.findById(id))
     *   - Si no existe -> lanzar NoSuchElementException o una excepción de negocio
     * - Aplicar cambios del request a la entidad encontrada (merge)
     * - Guardar entidad actualizada (repo.save(entity))
     * - Mapear a RS y devolverlo
     *
     * ¿Qué devuelve?
     * - RS: DTO con la entidad actualizada.
     */
    RS actualizar(RQ request, Long id);

    /**
     * Elimina un recurso por id.
     *
     * Parámetros:
     * - Long id: identificador del recurso a eliminar.
     *
     * Comportamiento habitual:
     * - Verificar existencia (repo.findById)
     * - Si existen relaciones que impiden la eliminación -> lanzar EntidadRelacionadaException
     * - Realizar repo.deleteById(id)
     *
     * ¿Qué devolver?
     * - RS: opción 1) devolver el DTO del recurso eliminado (snapshot antes de borrar)
     *       opción 2) devolver información mínima (mensaje) o null
     *
     */
    RS eliminar(Long id);

    /**
     * Obtiene un recurso por su id.
     *
     * Parámetros:
     * - Long id: identificador del recurso.
     *
     * ¿Qué devuelve?
     * - RS: DTO con los datos del recurso.
     *
     * Errores:
     * - Si no existe, lanzar NoSuchElementException para que el GlobalExceptionHandler devuelva 404.
     *
     */
    RS obtenerPorId(Long id);
}
//