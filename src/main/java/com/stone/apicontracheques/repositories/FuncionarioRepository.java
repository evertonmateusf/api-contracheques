package com.stone.apicontracheques.repositories;

import java.util.Optional;

import com.stone.apicontracheques.domain.Funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

	@Transactional(readOnly = true)
	Optional<Funcionario> findById(Integer codigo);

}
