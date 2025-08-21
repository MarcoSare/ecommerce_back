package com.ecommerce.oauth.entities;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIOS_OAUTH")
public class Usuario {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIOS_SEQ")
	@SequenceGenerator(name = "USUARIOS_SEQ", sequenceName = "USUARIOS_SEQ", allocationSize = 1)
	@Column(name = "ID_USUARIO")
	private Long id;
	
	@Column(name = "USERNAME", nullable = false, length = 20, unique = true)
	private String username;
	
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "USUARIOS_ROLES",
        joinColumns = @JoinColumn(name = "ID_USUARIO"),
        inverseJoinColumns = @JoinColumn(name = "ID_ROL")
    )
	private Set<Rol> roles;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Rol> getRoles() {
		return roles;
	}


	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
	
	

}
