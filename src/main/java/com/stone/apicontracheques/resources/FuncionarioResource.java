package com.stone.apicontracheques.resources;

import java.net.URI;

import javax.validation.Valid;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.dto.ContrachequeDTO;
import com.stone.apicontracheques.dto.FuncionarioDTO;
import com.stone.apicontracheques.services.FuncionarioService;
import com.stone.apicontracheques.services.exceptions.DataIntegrityException;

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

import io.swagger.annotations.ApiOperation;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioResource {

	@Autowired
	FuncionarioService service;

	@ApiOperation(value = "Busca funcionário por código")
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer codigo) throws ObjectNotFoundException {
		Funcionario obj = service.find(codigo);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Devolve o contracheque do funcionário do mês atual")
	@RequestMapping(value = "/{codigo}/contracheque", method = RequestMethod.GET)
	public ResponseEntity<?> getContracheque(@PathVariable Integer codigo) throws ObjectNotFoundException {
		ContrachequeDTO obj = service.getContracheque(codigo);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value="Busca paginada de funcionários")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Page<Funcionario>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) throws ObjectNotFoundException {
		Page<Funcionario> list;
		list = service.findAll(page, linesPerPage, Sort.by(orderBy.split(",")));
		return ResponseEntity.ok().body(list);
	}

	@ApiOperation(value="Insere um novo funcionário")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@Valid @RequestBody FuncionarioDTO objDTO)  throws DataIntegrityException {
		Funcionario obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@ApiOperation(value="Altera um funcionário")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@Valid @RequestBody FuncionarioDTO objDTO, @PathVariable Integer codigo) {
		Funcionario obj = service.fromDTO(codigo, objDTO);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Deleta um funcionário")
	@RequestMapping(value = "/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer codigo) throws ObjectNotFoundException {
		service.delete(codigo);
		return ResponseEntity.noContent().build();
	}

}
