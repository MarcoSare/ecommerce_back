package com.ecommerce.oauth.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLES_OAUTH")
public class Rol {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLES_SEQ")
	@SequenceGenerator(name = "ROLES_SEQ", sequenceName = "ROLES_SEQ", allocationSize = 1)
	@Column(name = "ID_ROL")
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false, length = 15, unique = true)
	private String nombre;

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

	
	
	


}
