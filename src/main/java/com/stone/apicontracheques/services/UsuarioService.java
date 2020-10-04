package com.stone.apicontracheques.services;

import java.util.List;
import java.util.Optional;

import com.stone.apicontracheques.domain.Usuario;
import com.stone.apicontracheques.domain.enums.PerfilAcesso;
import com.stone.apicontracheques.domain.enums.StatusUsuario;
import com.stone.apicontracheques.dto.credenciais.UsuarioDTO;
import com.stone.apicontracheques.dto.credenciais.UsuarioNewDTO;
import com.stone.apicontracheques.repositories.UsuarioRepository;
import com.stone.apicontracheques.security.UsuarioSpringSecurity;
import com.stone.apicontracheques.services.exceptions.AuthorizationException;
import com.stone.apicontracheques.services.exceptions.DataIntegrityException;
import com.stone.apicontracheques.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public Usuario find(Integer codigo) throws ObjectNotFoundException {
		UsuarioSpringSecurity user = UserService.authenticated();
		if (user == null || (!user.hasRole(PerfilAcesso.ADMINISTRADOR) && !codigo.equals(user.getCodigo()))) {
			throw new AuthorizationException("Acesso negado.");
		}
		Optional<Usuario> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Codigo:" + codigo + ", Tipo" + Usuario.class.getName()));
	}
	
	public Usuario findById(Integer codigo) throws ObjectNotFoundException {
		Optional<Usuario> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Codigo:" + codigo + ", Tipo" + Usuario.class.getName()));
	}

	public Usuario insert(Usuario obj) {
		obj.setCodigo(null);
		obj = repo.save(obj);
		return obj;
	}

	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getCodigo());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setStatus(obj.getStatus());
		newObj.setUsuarioUltimaAlteracao(obj.getUsuarioUltimaAlteracao());
	}

	public void delete(Integer codigo) {
		find(codigo);
		try {
			repo.deleteById(codigo);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Usuario com pedidos associados.");
		}
	}

	public List<Usuario> findAll() {
		List<Usuario> list = (List<Usuario>) repo.findAll();
		return list;
	}

	public Usuario findByEmail(String email) {
		UsuarioSpringSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(PerfilAcesso.ADMINISTRADOR) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Usuario obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getCodigo() + ", Tipo: " + Usuario.class.getName());
		}
		return obj;
	}

	public Page<Usuario> pagedFind(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Usuario fromDTO(UsuarioDTO objDto) {
		Usuario usuario = new Usuario(objDto.getCodigo(), objDto.getNome(), objDto.getLogin(),
				passEncoder.encode(objDto.getSenha()), objDto.getDataHoraUltimoBloqueio(),
				objDto.getDataHoraUltimaAlteracao(), StatusUsuario.toEnum(objDto.getStatus()));
		for (PerfilAcesso perfil : objDto.getPerfis()) {
			usuario.addPerfil(perfil);
		}

		return usuario;
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		Usuario usuario = new Usuario(objDto.getCodigo(), objDto.getNome(), objDto.getEmail(),
				"", null,
				null, StatusUsuario.toEnum(objDto.getStatus()));
		usuario.addPerfil(PerfilAcesso.ADMINISTRADOR);
		
		return usuario;
	}
	
	public Page<Usuario> findByText(String text, Integer page, Integer linesPerPage, Sort sort) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, sort);
		return repo.findByText(text.toLowerCase(), pageRequest);
	}
}
