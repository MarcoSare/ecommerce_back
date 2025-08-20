package com.ecommerce.clientes.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.commons.dto.ClientesRequest;
import com.ecommerce.commons.dto.ClientesResponse;
import com.ecommerce.commons.exceptions.EntidadRelacionadaException;
import com.ecommerce.clientes.clients.PedidosClients;
import com.ecommerce.clientes.entities.Cliente;
import com.ecommerce.clientes.mappers.ClienteMapper;
import com.ecommerce.clientes.repositories.ClienteRepository;

/**
 * Implementación del servicio de Clientes.
 *
 * Basado en el patrón del ejemplo de Tipos: centraliza la lógica CRUD,
 * usa el repositorio para acceder a la BD y el mapper para convertir
 * entre entidad <-> DTOs.
 *
 * NOTAS:
 * - Si en el futuro necesitas verificar referencias en otros microservicios
 *   (por ej. pedidos que referencien clientes), inyecta aquí los Feign clients
 *   y realiza las comprobaciones en el método eliminar().
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    
    private final PedidosClients pedidosClients;

    

	public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper,
			PedidosClients pedidosClients) {
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
		this.pedidosClients = pedidosClients;
	}

	@Override
    @Transactional(readOnly = true)
    public List<ClientesResponse> listar() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClientesResponse insertar(ClientesRequest request) {
        // Validaciones de unicidad antes de persistir
        if (clienteRepository.existsByEmail(request.email())) {
            throw new EntidadRelacionadaException("El email ya está registrado");
        }
        if (clienteRepository.existsByTelefono(request.telefono())) {
            throw new EntidadRelacionadaException("El teléfono ya está registrado");
        }

        Cliente entidad = clienteMapper.requestToEntity(request);
        Cliente guardado = clienteRepository.save(entidad);
	     // Convierte la entidad JPA "guardado" a su DTO de respuesta (ClientesResponse) y lo devuelve.
	     // - clienteMapper: componente encargado de mapear entidad -> DTO.
	     // - entityToResponse(...): método que recibe la entidad y construye el DTO que se enviará al cliente.
	     // - guardado: entidad retornada por repo.save(...) con el id generado y campos persistidos.
	     return clienteMapper.entityToResponse(guardado);
    }

    @Override
    @Transactional
    public ClientesResponse actualizar(ClientesRequest request, Long id) {
        Cliente existente = clienteRepository.findById(id).orElseThrow(NoSuchElementException::new);

        // Verificar que no exista otro cliente con el mismo email
        clienteRepository.findByEmail(request.email()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new EntidadRelacionadaException("El email ya está registrado por otro cliente");
            }
        });

        // Verificar que no exista otro cliente con el mismo teléfono
        clienteRepository.findByTelefono(request.telefono()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new EntidadRelacionadaException("El teléfono ya está registrado por otro cliente");
            }
        });

        // Aplicar cambios y persistir
        existente.setNombre(request.nombre());
        existente.setApellido(request.apellido());
        existente.setEmail(request.email());
        existente.setTelefono(request.telefono());
        existente.setDireccion(request.direccion());

        Cliente actualizado = clienteRepository.save(existente);
        return clienteMapper.entityToResponse(actualizado);
    }

    @Override
    @Transactional
    public ClientesResponse eliminar(Long id) {
        Cliente existente = clienteRepository.findById(id).orElseThrow(NoSuchElementException::new);

        // Si se requiere validar referencias en otros microservicios, inyectar Feign clients
        // y comprobar aquí (similar al ejemplo de TipoServiceImpl).
        // Ejemplo (placeholder):
        // if (pedidoClient.existsByCliente(id) > 0) {
        //     throw new EntidadRelacionadaException("El cliente está referenciado en pedidos y no puede eliminarse");
        // }
        
        boolean presenteEnPedidos = pedidosClients.existsClienteById(id) > 0;
        if (presenteEnPedidos) {
        	throw new EntidadRelacionadaException("El Cliente no se puede eliminar por que hay pedidos pendientes de este cliente");
        }
        

        clienteRepository.deleteById(id);
        return clienteMapper.entityToResponse(existente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientesResponse obtenerPorId(Long id) {
        Cliente c = clienteRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return clienteMapper.entityToResponse(c);
    }
}