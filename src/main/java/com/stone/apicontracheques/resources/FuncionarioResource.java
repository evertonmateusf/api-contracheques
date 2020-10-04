package com.stone.apicontracheques.resources;

import java.net.URI;

import javax.validation.Valid;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.dto.FuncionarioDTO;
import com.stone.apicontracheques.services.FuncionarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioResource {

	@Autowired
	FuncionarioService service;
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer codigo) throws ObjectNotFoundException {

		Funcionario obj = service.find(codigo);
		return ResponseEntity.ok().body(obj);
	}

	// @PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Page<Funcionario>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) throws ObjectNotFoundException {
		Page<Funcionario> list;
		list = service.findAll(page, linesPerPage, Sort.by(orderBy.split(",")));
		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@Valid @RequestBody FuncionarioDTO objDTO) {
		Funcionario obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@Valid @RequestBody FuncionarioDTO objDTO, @PathVariable Integer codigo) {
		Funcionario obj = service.fromDTO(codigo, objDTO);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/findByName",method=RequestMethod.GET)
	public ResponseEntity<Page<Funcionario>> pagedFindByText(
			@RequestParam(value="name", defaultValue="") String text, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="codigo") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) throws ObjectNotFoundException {
		Sort sort = Sort.by(orderBy.split(","));
		
		Page<Funcionario> list = service.findByName(text, page, linesPerPage, sort);
		
		return ResponseEntity.ok().body(list);
	}

}
