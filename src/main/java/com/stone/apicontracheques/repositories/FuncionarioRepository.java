package com.stone.apicontracheques.repositories;

import java.util.Optional;

import com.stone.apicontracheques.domain.Funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT Funcionario FROM Funcionario Funcionario WHERE LOWER(Funcionario.nome) LIKE %:texto% ")
	Page<Funcionario> findByNome(@Param("texto") String texto, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	Optional<Funcionario> findById(Integer codigo);

	@Query(value = "SELECT obj FROM Funcionario obj ORDER BY obj.id DESC")
	Optional<Funcionario> getLastIncluded(@Param("status") Integer status, @Param("comissaoPaga") boolean comissaoPaga);
	
	// @Transactional(readOnly = true)
	// @Query("SELECT obj FROM Funcionario obj WHERE obj.usuarioInclusao = :usuario ")
	// Page<Funcionario> findByUser(@Param("usuario") Usuario usuario, Pageable pageRequest);

}
