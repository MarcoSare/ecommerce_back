package com.ecommerce.oauth.services;

import java.util.Set;

import com.ecommerce.oauth.dto.UsuarioRequest;
import com.ecommerce.oauth.dto.UsuarioResponse;

public interface UsuarioService {

	Set<UsuarioResponse> listarUsuarios();

	UsuarioResponse crearUsuario(UsuarioRequest request);
	
	UsuarioResponse eliminarUsuario(String username);
}
