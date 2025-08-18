package com.ecommerce.clientes.services;

import com.ecommerce.commons.dto.ClientesRequest;
import com.ecommerce.commons.dto.ClientesResponse;
import com.ecommerce.commons.services.CommonService;

/**
 * Interfaz del servicio de Clientes.
 *
 * Qué es y para qué sirve:
 * - Es el contrato (API) de negocio para operaciones CRUD sobre clientes.
 * - Extiende CommonService fijando los tipos:
 *     RQ = ClientesRequest  (datos entrantes desde el controlador)
 *     RS = ClientesResponse (datos salientes que devuelve el controlador)
 *
 * De dónde obtiene los datos y hacia dónde los entrega:
 * - La implementación (ej. ClientesServiceImpl) obtendrá/almacenará entidades
 *   mediante ClienteRepository (JPA) y usará un mapper para convertir entre
 *   entidad <-> DTO.
 * - Entrada: recibe ClientesRequest desde el controlador REST.
 * - Salida: devuelve ClientesResponse al controlador, que a su vez responde al cliente HTTP.
 */

public interface ClienteService extends CommonService<ClientesRequest, ClientesResponse> {

}
