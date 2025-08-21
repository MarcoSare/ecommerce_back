package com.ecommerce.oauth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.oauth.dto.LoginRequest;
import com.ecommerce.oauth.dto.UsuarioRequest;
import com.ecommerce.oauth.dto.UsuarioResponse;
import com.ecommerce.oauth.services.AuthService;
import com.ecommerce.oauth.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	private final AuthService authService;
	
	private final UsuarioService usuarioService;

	public AuthController(AuthService authService, UsuarioService usuarioService) {
		this.authService = authService;
		this.usuarioService = usuarioService;
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request)throws Exception {
		String token = authService.authenticate(request.username(), request.password());
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/admin/usuarios")
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.crearUsuario(request));
    }
	
	   @GetMapping("/admin/usuarios")
	    public ResponseEntity<Set<UsuarioResponse>> listarUsuarios() {
	    	return ResponseEntity.ok(usuarioService.listarUsuarios());
	    }
	   
	   @DeleteMapping("/admin/usuarios/{username}")
	    public ResponseEntity<UsuarioResponse> eliminarUsuarios(@PathVariable String username) {
	    	return ResponseEntity.ok(usuarioService.eliminarUsuario(username));
	    }
}
