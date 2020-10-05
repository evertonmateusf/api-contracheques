package com.stone.apicontracheques.resources;

import java.net.URI;

import javax.validation.Valid;

import com.stone.apicontracheques.domain.Usuario;
import com.stone.apicontracheques.dto.credenciais.UsuarioDTO;
import com.stone.apicontracheques.dto.credenciais.UsuarioNewDTO;
import com.stone.apicontracheques.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	UsuarioService service;
	
	@ApiOperation(value="Busca usuário por código")
	@RequestMapping(value="/{codigo}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer codigo) throws ObjectNotFoundException {
		 
		Usuario obj = service.find(codigo);	
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value="Busca paginada de usuários")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Page<UsuarioDTO>> pagedFind(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "codigo") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) throws ObjectNotFoundException {

		Page<Usuario> list = service.pagedFind(page, linesPerPage, orderBy, direction);
		Page<UsuarioDTO> listDto = list.map(obj -> new UsuarioDTO(obj));

		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value="Insere um novo usuário")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@Valid @RequestBody UsuarioNewDTO objDto) {
		Usuario obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(obj.getCodigo())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value="Altera um usuário")
	@RequestMapping(value = "/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@Valid @RequestBody UsuarioNewDTO objDTO, @PathVariable Integer codigo) {
		Usuario obj = service.fromDTO(objDTO);
		obj.setCodigo(codigo);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value="Deleta um usuário")
	@RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer codigo) throws ObjectNotFoundException {
		service.delete(codigo);
		return ResponseEntity.noContent().build();
	}

}
