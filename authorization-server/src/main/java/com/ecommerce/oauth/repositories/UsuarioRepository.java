package com.ecommerce.oauth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.oauth.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	

Optional<Usuario> findByUsername(String username);

void deleteByUsername(String username);

}
