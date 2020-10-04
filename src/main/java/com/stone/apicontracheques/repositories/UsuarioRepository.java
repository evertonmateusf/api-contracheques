 package com.stone.apicontracheques.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stone.apicontracheques.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Transactional(readOnly=true)
	Usuario findByEmail(String email);
	
	@Transactional(readOnly = true)
	@Query("SELECT usuario FROM Usuario usuario WHERE LOWER(usuario.nome) LIKE %:texto% OR codigo LIKE %:texto% ")
	Page<Usuario> findByText(@Param("texto") String texto, Pageable pageRequest);
}
