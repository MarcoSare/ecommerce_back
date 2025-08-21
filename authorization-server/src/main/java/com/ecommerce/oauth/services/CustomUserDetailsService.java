package com.ecommerce.oauth.services;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.oauth.entities.Usuario;
import com.ecommerce.oauth.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UsuarioRepository usuarioRepository;

	public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado en BD"));
		
		return new User(
				
				 usuario.getUsername(),
	                usuario.getPassword(),
	                usuario.getRoles().stream()
	                		.map(r -> new SimpleGrantedAuthority(r.getNombre()))
	                			.collect(Collectors.toSet())
		
				);
	}
	
	

}
