package com.stone.apicontracheques.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.stone.apicontracheques.security.UsuarioSpringSecurity;

public class UserService {

	public UserService() {
	}

	public static UsuarioSpringSecurity authenticated() {
		try {
			return (UsuarioSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
