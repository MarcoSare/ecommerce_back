package com.ecommerce.oauth.services;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.KeyType;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class AuthServiceImpl implements AuthService{

	
	private final UserDetailsService userDetailsService;
	private final RSAKey rsaKey;
	
	public AuthServiceImpl(UserDetailsService userDetailsService, JWKSource<SecurityContext> jwkSource) {
		this.userDetailsService = userDetailsService;
		try {
			// Selecciona las claves de RSA
			JWKSelector jwkSelector = new JWKSelector(
					new JWKMatcher.Builder().keyType(KeyType.RSA).build()
			);
			var jwks = jwkSource.get(jwkSelector, null);
			if (jwks == null || jwks.isEmpty()) {
				throw new RuntimeException("No se pudo obtener la clave RSA");
			}
			this.rsaKey = (RSAKey) jwks.get(0);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo obtener la clave RSA");
		}
	}
	
	@Override
	public String authenticate(String username, String password) throws Exception {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (userDetails == null || !new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
			throw new RuntimeException("Credenciales inválidas");
		}
		
		Instant now = Instant.now();
		JWTClaimsSet claims = new JWTClaimsSet.Builder()
				.issuer("http://localhost:9000")
				.subject(userDetails.getUsername())
				.issueTime(Date.from(now))
				.expirationTime(Date.from(now.plusSeconds(3600)))
				.jwtID(UUID.randomUUID().toString())
				//.claim("roles", userDetails.getAuthorities())
				.claim("roles", userDetails.getAuthorities()
				        .stream()
				        .map(a -> a.getAuthority())
				        .toList())
				.build();
		SignedJWT signedJWT = new SignedJWT(
				new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build()
				,claims);
		JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());
		signedJWT.sign(signer);
		
		return signedJWT.serialize();
	}
}
