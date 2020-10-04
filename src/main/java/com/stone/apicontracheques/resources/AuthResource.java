package com.stone.apicontracheques.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.stone.apicontracheques.dto.EmailDTO;
import com.stone.apicontracheques.dto.credenciais.NovaSenhaDTO;
import com.stone.apicontracheques.security.JWTUtil;
import com.stone.apicontracheques.security.UsuarioSpringSecurity;
import com.stone.apicontracheques.services.AuthService;
import com.stone.apicontracheques.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UsuarioSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<Void> changePassword(@Valid @RequestBody NovaSenhaDTO objDto) {
		service.changePassword(objDto);
		return ResponseEntity.noContent().build();
	}
}