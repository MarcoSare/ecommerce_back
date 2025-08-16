package com.marco.oauth.services;


public interface AuthService {
	
	String authenticate(String username, String password) throws Exception;

	
}
