// ...existing code...
package com.ecommerce.commons.mappers;

/*
 * Mapper genérico base para convertir entre:
 *  - RQ : Tipo de objeto request (DTO entrante, p.ej. ClientesRequest)
 *  - RS : Tipo de objeto response (DTO de salida, p.ej. ClientesResponse)
 *  - E  : Tipo de entidad JPA (p.ej. Cliente)
 *
 * ¿Por qué usar genéricos?
 * - Permite reutilizar la misma interfaz/abstracto para múltiples entidades/DTOs
 *   sin repetir la firma de métodos.
 *
 * ¿Por qué abstract class y no interface?
 * - Una clase abstracta permite ofrecer métodos utilitarios comunes (implementados)
 *   además de los métodos abstractos que cada mapper concreto debe implementar.
 * - También facilita mantener compatibilidad si se añaden utilidades compartidas.
 *
 * Ejemplo de tipos:
 *   CommonMapper<ClientesRequest, ClientesResponse, Cliente>
 *
 * Notas de estilo:
 * - Los nombres de los métodos indican la dirección de la conversión:
 *     entityToResponse: entidad -> DTO de salida
 *     requestToEntity:  DTO de entrada -> entidad
 */
public abstract class CommonMapper<RQ, RS, E> {

    /**
     * Convierte una entidad (E) a su DTO de respuesta (RS).
     *
     * ¿Para qué se usa?
     * - Cuando obtienes una entidad desde el repositorio y quieres devolverla
     *   en la API en forma de DTO (sin exponer relaciones perezosas, campos sensibles, etc.).
     *
     * ¿Qué recibe y qué devuelve?
     * - Parámetro: E entity  -> la entidad JPA que quieres transformar.
     * - Retorno:   RS       -> el DTO preparado para la respuesta JSON.
     *
     * Ejemplo de lo que puede devolver (pseudocódigo):
     *   entity = Cliente{id=1, nombre="Ana", email="a@x.com"}
     *   devuelve ClientesResponse{id=1, nombre="Ana", email="a@x.com"}
     *
     * Observación sobre nombres: el parámetro se llama 'entity' para dejar claro su propósito.
     */
    public abstract RS entityToResponse(E entity);

    /**
     * Convierte un DTO de request (RQ) a la entidad (E).
     *
     * ¿Para qué se usa?
     * - Cuando recibes datos desde el cliente (POST/PUT) y necesitas crear/actualizar
     *   una entidad JPA antes de persistirla.
     *
     * ¿Qué recibe y qué devuelve?
     * - Parámetro: RQ request -> DTO con datos de entrada (validados previamente).
     * - Retorno:   E         -> entidad preparada para guardar en el repositorio.
     *
     * Ejemplo de entrada/salida (pseudocódigo):
     *   request = ClientesRequest{nombre="Ana", email="a@x.com"}
     *   devuelve Cliente{nombre="Ana", email="a@x.com"}
     *
     * Notas prácticas:
     * - En la conversión se pueden inicializar campos por defecto (fechas, estados).
     * - Para actualizaciones, el mapper puede recibir la entidad existente y aplicar
     *   cambios desde el DTO (implementación concreta debe decidir eso).
     */
    public abstract E requestToEntity(RQ request);

    // ---- Métodos utilitarios opcionales que pueden ser útiles en mappers concretos ----
    //
    // Puedes implementar aquí helpers reutilizables, por ejemplo:
    //
    // protected String normalizeString(String s) {
    //     return s == null ? null : s.trim();
    // }
    //
    // Así, los mappers concretos llaman normalizeString(...) para sanitizar cadenas.
    //
    // También puedes añadir métodos que combinen información de DTO + entidad existente
    // para realizar actualizaciones parciales (patch).
}