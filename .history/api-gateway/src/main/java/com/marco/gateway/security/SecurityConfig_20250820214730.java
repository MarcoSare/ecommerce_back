package com.marco.gateway.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
<<<<<<< HEAD

=======
>>>>>>> a5eb3bd24e51b60c2c7df64867a05eb33751399b

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {	

	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.csrf(csrf -> csrf.disable())
				// .cors(Customizer.withDefaults())
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(List.of("http://localhost:4200"));
					config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
					config.setAllowCredentials(true);
					return config;
				})).authorizeExchange(exchange -> exchange
					// Permitir todas las peticiones OPTIONS (preflight CORS)
					.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

					// Restricciones por método
					.pathMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN", "USER")
					.pathMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN", "USER")
					.pathMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
					.pathMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
					.anyExchange().authenticated())
					.oauth2ResourceServer(oauth2 -> oauth2
							.jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverterAdapter()
					)));

		return http.build();
	}
    
    @Bean
<<<<<<< HEAD
   ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverterAdapter() {
=======
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    	http.csrf(csrf -> csrf.disable())
    			.cors(cors -> cors.configurationSource(request -> {
    				CorsConfiguration config = new CorsConfiguration();
    				config.setAllowedOrigins(List.of("http://localhost:4200"));
    				config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    				config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    				config.setAllowCredentials(true);
    				return config;
    			})).authorizeExchange(ex -> ex
    		            .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    		            .pathMatchers(HttpMethod.GET, "/**").permitAll()//hasAnyRole("ADMIN", "USER")
    		            .pathMatchers(HttpMethod.POST, "/**").permitAll()//hasAnyRole("ADMIN", "USER")
    		            .pathMatchers(HttpMethod.PUT, "/**").permitAll() //hasRole("ADMIN")
    		            .pathMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
    		            .anyExchange().authenticated()
    		        )
    		        .oauth2ResourceServer(oauth2 -> oauth2
    		            .jwt(jwt -> jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverter()))
    		        );
    	return http.build();
    }
    
    @Bean
    ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverter() {
>>>>>>> a5eb3bd24e51b60c2c7df64867a05eb33751399b
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("roles");
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return new ReactiveJwtAuthenticationConverterAdapter(jwtConverter);
    }
}