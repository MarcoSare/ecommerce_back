package com.ecommerce.commons.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.commons.services.CommonService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * Controlador genérico que define operaciones CRUD comunes para todos los microservicios.
 * Utiliza genéricos para manejar diferentes tipos de Request (RQ), Response (RS) y Service (S).
 * 
 * @param <RQ> Tipo del objeto Request (DTO de entrada)
 * @param <RS> Tipo del objeto Response (DTO de salida)
 * @param <S> Tipo del servicio que extiende CommonService
 */
@Validated // Habilita validación de parámetros a nivel de clase
public class CommonController<RQ, RS, S extends CommonService<RQ, RS>> {
    
    // Servicio inyectado que maneja la lógica de negocio
    protected S service;
    
    /**
     * Constructor que inyecta el servicio específico del microservicio.
     * @param service Implementación específica del servicio
     */
    public CommonController(S service) {
        this.service = service;
    }

    /**
     * Endpoint para listar todos los registros.
     * @return ResponseEntity con lista de objetos Response y status 200 OK
     */
    @GetMapping
    public ResponseEntity<List<RS>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    /**
     * Endpoint para obtener un registro por su ID.
     * @param id ID del registro a buscar (debe ser positivo)
     * @return ResponseEntity con el objeto Response encontrado y status 200 OK
     * @throws EntidadNoEncontradaException si el ID no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<RS> obtenerPorId(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    /**
     * Endpoint para crear un nuevo registro.
     * @param request Objeto Request con los datos a insertar (validado)
     * @return ResponseEntity con el objeto Response creado y status 201 CREATED
     * @throws EntidadRelacionadaException si hay datos duplicados (email, teléfono)
     */
    @PostMapping
    public ResponseEntity<RS> insertar(@Valid @RequestBody RQ request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insertar(request));
    }

    /**
     * Endpoint para actualizar un registro existente.
     * @param request Objeto Request con los datos actualizados (validado)
     * @param id ID del registro a actualizar (debe ser positivo)
     * @return ResponseEntity con el objeto Response actualizado y status 200 OK
     * @throws EntidadNoEncontradaException si el ID no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<RS> actualizar(
            @Valid @RequestBody RQ request,
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }

    /**
     * Endpoint para eliminar un registro por su ID.
     * @param id ID del registro a eliminar (debe ser positivo)
     * @return ResponseEntity con el objeto Response eliminado y status 200 OK
     * @throws EntidadNoEncontradaException si el ID no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RS> eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}