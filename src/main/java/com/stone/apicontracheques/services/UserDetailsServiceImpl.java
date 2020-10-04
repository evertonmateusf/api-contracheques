package com.stone.apicontracheques.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stone.apicontracheques.domain.Usuario;
import com.stone.apicontracheques.repositories.UsuarioRepository;
import com.stone.apicontracheques.security.UsuarioSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;

	public UserDetailsServiceImpl() {
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repo.findByEmail(email);

		if (usuario == null) {
			throw new UsernameNotFoundException(email);
		}

		return new UsuarioSpringSecurity(usuario.getCodigo(), usuario.getLogin(), usuario.getSenha(),
				usuario.getPerfis(), usuario.getStatus().getCodigo());
	}

}
