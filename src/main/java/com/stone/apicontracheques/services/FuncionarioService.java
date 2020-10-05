package com.stone.apicontracheques.services;

import java.util.Optional;

import javax.validation.Valid;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.dto.FuncionarioDTO;
import com.stone.apicontracheques.repositories.FuncionarioRepository;
import com.stone.apicontracheques.services.exceptions.DataIntegrityException;
import com.stone.apicontracheques.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repo;

	// @Autowired
	// private UsuarioService usuarioService;

	public Funcionario find(Integer codigo) throws ObjectNotFoundException {
		// UsuarioSpringSecurity user = UserService.authenticated();
		// if (user == null || (!user.hasRole(PerfilAcesso.ADMINISTRADOR) &&
		// !tecnicoCodigo.equals(user.getId()))) {
		// throw new AuthorizationException("Acesso negado." + tecnicoCodigo + "-" +
		// user.getId());
		// }
		Optional<Funcionario> obj = repo.findById(codigo);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Codigo:" + codigo + ", Tipo" + Funcionario.class.getName()));
	}

	public Page<Funcionario> findAll(Integer page, Integer linesPerPage, Sort orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, orderBy);
		return repo.findAll(pageRequest);
	}

	public Funcionario fromDTO(@Valid Integer codigo, FuncionarioDTO objDto) {
		Funcionario funcionario = find(codigo);

		funcionario.setNome(objDto.getNome());
		funcionario.setSobrenome(objDto.getSobrenome());
		funcionario.setDocumento(objDto.getDocumento());
		funcionario.setSetor(objDto.getSetor());
		funcionario.setSalarioBruto(objDto.getSalarioBruto());
		funcionario.setDataDeAdmissao(objDto.getDataDeAdmissao());
		funcionario.setDescontaPlanoDeSaude(objDto.isDescontaPlanoDeSaude());
		funcionario.setDescontaPlanoDental(objDto.isDescontaPlanoDental());
		funcionario.setDescontaValeTransporte(objDto.isDescontaValeTransporte());

		return funcionario;
	}

	public Funcionario fromDTO(FuncionarioDTO objDto) {
		Funcionario funcionario = new Funcionario();

		funcionario.setId(null);
		funcionario.setNome(objDto.getNome());
		funcionario.setSobrenome(objDto.getSobrenome());
		funcionario.setDocumento(objDto.getDocumento());
		funcionario.setSetor(objDto.getSetor());
		funcionario.setSalarioBruto(objDto.getSalarioBruto());
		funcionario.setDataDeAdmissao(objDto.getDataDeAdmissao());
		funcionario.setDescontaPlanoDeSaude(objDto.isDescontaPlanoDeSaude());
		funcionario.setDescontaPlanoDental(objDto.isDescontaPlanoDental());
		funcionario.setDescontaValeTransporte(objDto.isDescontaValeTransporte());

		return funcionario;
	}

	@Transactional
	public Funcionario insert(Funcionario obj) throws DataIntegrityException {
		obj.setId(null);
		try {
			return repo.save(obj);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("CPF " + obj.getDocumento() + " já existente.");
		}
	}

	public Funcionario update(Funcionario obj) {
		find(obj.getId());
		// obj.setValorComissao(this.calculaComissao(obj));
		return repo.save(obj);
	}

	public void delete(Integer codigo) {
		find(codigo);
		try {
			repo.deleteById(codigo);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir o funcionário.");
		}
	}

	// public ComissoesDTO sumValorComissaoAPagar(Integer tecnicoCodigo) throws
	// ObjectNotFoundException {
	// UsuarioSpringSecurity user = UserService.authenticated();
	// if (user == null || (!user.hasRole(PerfilAcesso.ADMINISTRADOR) &&
	// !tecnicoCodigo.equals(user.getId()))) {
	// throw new AuthorizationException("Acesso negado." + tecnicoCodigo + "-" +
	// user.getId());
	// }
	// Tecnico tecnico = tecnicoService.find(tecnicoCodigo);
	// return new ComissoesDTO(repo.sumValorComissao(tecnico,
	// StatusFuncionario.REALIZADO.getId(), false));
	// }

	// private double calculaComissao(Funcionario obj) {
	// return (obj.getValorTotalCobrado() - (obj.getValorTotalCobrado() *
	// obj.getTaxaMaquina() / 100)
	// - obj.getCustoServico()) / 2;
	// }

	// @Transactional
	// public Funcionario reservarServico(Integer codigo, Integer tecnico) {

	// Calendar now = Calendar.getInstance();
	// if (now.get(Calendar.HOUR_OF_DAY) > 0 && now.get(Calendar.HOUR_OF_DAY) < 24)
	// {

	// Tecnico tec = tecnicoService.find(tecnico);
	// if (tec.getStatusAtendimento().equals(StatusAtendimento.ATIVO)) {
	// Funcionario jaReservado = findFirstByTecnicoAndStatus(tec,
	// StatusFuncionario.RESERVADO.getId());
	// if (jaReservado != null) {
	// throw new BusinessRuleException(
	// "O técnico já possui um serviço reservado. Finalize o serviço para reservar
	// outro.");
	// }
	// Funcionario obj = findByCodigoAndStatus(codigo,
	// StatusFuncionario.ABERTO.getId());
	// obj.setStatus(StatusFuncionario.RESERVADO.getId());
	// obj.setTecnico(tec);
	// return repo.save(obj);
	// } else {
	// throw new BusinessRuleException(
	// "Técnico bloqueado para reserver novos serviços. Entre em contato com o
	// escritório");
	// }
	// } else {
	// throw new BusinessRuleException("É permitido reservar Funcionarios apenas
	// entre às 8h e às 19h.");
	// }
	// }

}
