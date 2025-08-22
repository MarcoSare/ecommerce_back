package com.ecommerce.clientes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "CLIENTES")
public class Cliente {
	/**
     * Identificador primario.
     *
     * @Id
     *   - Marca el campo como PK de la entidad.
     *
     * @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTES_SEQ")
     * @SequenceGenerator(name = "CLIENTES_SEQ", sequenceName = "CLIENTES_SEQ", allocationSize = 1)
     *   - Indica que JPA debe obtener valores del lado de la BD usando una SEQUENCE.
     *   - name: nombre interno que relaciona GeneratedValue con este SequenceGenerator.
     *   - sequenceName: nombre real de la secuencia en la BD (debe existir: CLIENTES_SEQ).
     *   - allocationSize = 1: hace que JPA pida la secuencia 1 por 1 (coincide con NEXTVAL).
     *     Ejemplo: si la secuencia en BD incrementa de 1 en 1 y allocationSize fuera 50,
     *     Hibernate reservaría bloques de 50 ids y los valores no coincidirían exactamente
     *     con NEXTVAL en cada inserción (importante en Oracle).
     *
     * ¿Qué devuelve getId() antes y después de persistir?
     * - Antes de repo.save(entity): id == null (Long permite null).
     * - Después de repo.save(entity): id contiene el valor generado por la secuencia (ej. 1, 2, ...).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTES_SEQ")
    @SequenceGenerator(name = "CLIENTES_SEQ", sequenceName = "CLIENTES_SEQ", allocationSize = 1)
    @Column(name = "ID_CLIENTES")
    private Long id;

    /**
     * Nombre del cliente.
     * - length = 50 corresponde a VARCHAR2(50) en la definición SQL.
     * - nullable = false corresponde a NOT NULL.
     */
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    /**
     * Apellido del cliente.
     */
    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    /**
     * Email del cliente.
     * - En la BD existe una constraint UNIQUE (UN_EMAIL). Aquí se puede indicar unique=true,
     *   pero si estás usando spring.jpa.hibernate.ddl-auto=validate o none no se intentará crearla.
     * - La validación de unicidad debe hacerse en la capa servicio (antes de insertar/actualizar)
     *   para lanzar excepciones de negocio manejables.
     */
    @Column(name = "EMAIL", nullable = false, length = 50, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", 
             message = "El formato del email no es válido")
    private String email;

    /**
     * Teléfono del cliente.
     * - maxlength 10 según la tabla.
     * - unique = true para reflejar la constraint UN_TELEFONO.
     */
    @Column(name = "TELEFONO", nullable = false, length = 10, unique = true)
    private String telefono;

    /**
     * Dirección (opcional según la definición SQL: puede ser NULL).
     */
    @Column(name = "DIRECCION", length = 100)
    private String direccion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
