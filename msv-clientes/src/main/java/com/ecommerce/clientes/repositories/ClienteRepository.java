package com.ecommerce.clientes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.clientes.entities.Cliente;


/**
 * Repositorio JPA para la entidad de dominio que representa clientes.
 *
 * Qué hace:
 * - Extiende JpaRepository<E, ID> y por tanto hereda métodos CRUD listos para usar:
 *     findAll(), findById(ID), save(E), deleteById(ID), etc.
 *
 * Importante: debe existir la entidad correspondiente en este mismo microservicio.
 * - Si la clase entidad no existe (p. ej. "Clientes" no está definida), el código no compilará.
 * - Si existe la clase pero NO está anotada con @Entity, Hibernate/JPA no la tratará como tabla y
 *   el mapeo fallará al arrancar la aplicación (MappingException / errors de inicialización).
 * - La entidad debe residir en un package que sea escaneado por Spring Boot (misma raíz de paquete o configurada).
 *
 * Por qué la entidad debe estar en este microservicio:
 * - El repositorio opera sobre la entidad JPA concreta; esa entidad contiene el mapeo (nombres de columnas,
 *   esquema, secuencia, etc.) que corresponde a la tabla en la base de datos.
 * - Aunque exista una clase DTO en commons, la entidad JPA normalmente pertenece al módulo que accede a la BD
 *   (aquí msv-clientes). Separar entidad y DTO evita acoplamientos innecesarios y problemas de mapeo.
 *
 * Qué pasa si intentas configurar y arrancar sin la entidad:
 * - Caso 1 (clase inexistente): error de compilación (clase no encontrada).
 * - Caso 2 (clase existe pero no @Entity): la aplicación puede compilar, pero Hibernate no reconocerá la entidad;
 *   al inicializar JPA/Hibernate verás errores de mapeo o el repositorio no funcionará correctamente.
 * - En resumen: la entidad JPA es obligatoria para que JpaRepository funcione correctamente.
 *
 * Por qué se usa Long como tipo de ID:
 * - El segundo parámetro de JpaRepository<E, ID> indica el tipo del identificador primario (PK) de la entidad.
 * - Se usa java.lang.Long (envoltorio) en lugar de long (primitivo) porque:
 *     - Long puede ser null: antes de persistir una nueva entidad su id suele ser null y el proveedor JPA lo genera.
 *     - Muchos drivers/BD mappean NUMBER/INTEGER/BigInt a Long en Java.
 * - Asegúrate de que el tipo aquí coincide con el @Id de la entidad (p. ej. @Column ID_CLIENTES -> Long).
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // findByEmail:
    // - Método derivado por convención de Spring Data JPA: "findBy" + "Email".
    // - Spring genera la consulta automáticamente (SELECT ... WHERE email = ?).
    // - Devuelve Optional<Cliente> para expresar que puede no existir un resultado.
    // - Uso típico: repository.findByEmail(email).ifPresent(...) o .orElseThrow(...)
    Optional<Cliente> findByEmail(String email);

    // findByTelefono:
    // - Igual que findByEmail, pero busca por el campo 'telefono'.
    // - Devuelve Optional<Cliente> para indicar presencia/ausencia.
    Optional<Cliente> findByTelefono(String telefono);

    // existsByEmail:
    // - Método derivado que devuelve boolean indicando si existe al menos un registro
    //   con el email proporcionado.
    // - Ventaja: más eficiente si solo interesa saber si existe (no recuperar toda la entidad).
    // - Spring implementa la consulta automáticamente a partir del nombre (SELECT COUNT(...) > 0).
    boolean existsByEmail(String email);

    // existsByTelefono:
    // - Igual que existsByEmail, pero para el campo 'telefono'.
    // - Útil para validaciones de unicidad en la capa de servicio antes de insertar/actualizar.
    boolean existsByTelefono(String telefono);
}
