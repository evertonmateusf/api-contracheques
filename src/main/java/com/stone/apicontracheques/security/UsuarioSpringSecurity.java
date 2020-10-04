package com.stone.apicontracheques.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.stone.apicontracheques.domain.enums.PerfilAcesso;
import com.stone.apicontracheques.domain.enums.StatusUsuario;

public class UsuarioSpringSecurity implements UserDetails {
	private static final long serialVersionUID = 1L;
	public Integer codigo;
	public String login;
	public String senha;
	public Integer status;
	public Collection<? extends GrantedAuthority> authorities;

	public UsuarioSpringSecurity() {
	}

	public UsuarioSpringSecurity(Integer codigo, String login, String senha, Set<PerfilAcesso> perfis, Integer status) {
		this.codigo = codigo;
		this.login = login;
		this.senha = senha;
		this.status = status;
		this.authorities = perfis.stream().map( x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Integer getCodigo() {
		return codigo;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return status == StatusUsuario.ATIVO.getCodigo();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return status == StatusUsuario.ATIVO.getCodigo();
	}
	
	public boolean hasRole(PerfilAcesso perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
