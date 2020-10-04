package com.stone.apicontracheques.services;

import java.util.Random;

import com.stone.apicontracheques.domain.Usuario;
import com.stone.apicontracheques.dto.credenciais.NovaSenhaDTO;
import com.stone.apicontracheques.repositories.UsuarioRepository;
import com.stone.apicontracheques.services.exceptions.BusinessRuleException;
import com.stone.apicontracheques.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		usuario.setSenha(pe.encode(newPass));
		
		usuarioRepository.save(usuario);
	}
	
	public void changePassword(NovaSenhaDTO novaSenha) {
		
		Usuario usuario = usuarioRepository.findByEmail(novaSenha.getEmail());
		if (usuario == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		if ( !(pe.matches(novaSenha.getSenhaAtual(), usuario.getSenha())) ) {
			throw new BusinessRuleException("Senha atual inválida");
		}
		if (novaSenha.getSenhaAtual().equals(novaSenha.getSenhaNova())) {
			throw new BusinessRuleException("A nova senha não pode ser igual a anterior");
		}
		if (!novaSenha.getSenhaNova() .equals(novaSenha.getSenhaConfirmacao())) {
			throw new BusinessRuleException("Confirmação de senha é diferente da nova senha digitada");
		}
		usuario.setSenha(pe.encode(novaSenha.getSenhaNova()));
		
		usuarioRepository.save(usuario);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}