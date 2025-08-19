package com.ecommerce.clientes.mappers;

import org.springframework.stereotype.Component;

import com.ecommerce.clientes.entities.Cliente;
import com.ecommerce.commons.dto.ClientesRequest;
import com.ecommerce.commons.dto.ClientesResponse;
import com.ecommerce.commons.mappers.CommonMapper;


/*
 * Mapper para convertir entre:
 *  - ClientesRequest (RQ)  -> Cliente (E)
 *  - Cliente (E)           -> ClientesResponse (RS)
 *
 * @Component permite que Spring lo inyecte en servicios.
 */

@Component
public class ClienteMapper extends CommonMapper<ClientesRequest, ClientesResponse, Cliente> {

    /**
     * Convierte el DTO de entrada (ClientesRequest) a la entidad JPA (Cliente).
     *
     * Detalles:
     *  - ClientesRequest es un record: sus accesores son request.nombre(), request.email(), etc.
     *  - Se usa al crear nuevas entidades antes de repo.save(entity).
     *  - NO se setea el id aquí; la BD (secuencia) lo generará al persistir.
     */
    @Override
    public Cliente requestToEntity(ClientesRequest request) {
        if (request == null) {
            return null;
        }

        Cliente c = new Cliente();
        // request.nombre() -> accede al campo "nombre" del record ClientesRequest
        c.setNombre(request.nombre());
        c.setApellido(request.apellido());
        c.setEmail(request.email());
        c.setTelefono(request.telefono());
        c.setDireccion(request.direccion());
        return c;
    }

    /**
     * Convierte la entidad JPA (Cliente) al DTO de salida (ClientesResponse).
     *
     * Detalles:
     *  - Se usa al devolver datos desde el servicio/controlador.
     *  - Incluye el id generado por la BD: entity.getId().
     *  - Ideal para evitar exponer directamente la entidad en la API.
     */
    @Override
    public ClientesResponse entityToResponse(Cliente entity) {
        if (entity == null) {
            return null;
        }

        // ClientesResponse es un record con constructor: new ClientesResponse(id, nombre, apellido, email, telefono, direccion)
        return new ClientesResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getDireccion()
        );
    }
}