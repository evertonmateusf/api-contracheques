package com.stone.apicontracheques.dto.credenciais;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UsuarioNewDTO {

	private Integer codigo;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 3, max = 120, message = "O tamanho deve ser entre 3 e 120 caracteres")
	private String nome;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	private Set<Integer> perfis = new HashSet<Integer>();
	private Integer status;

	public UsuarioNewDTO() {
	}

	public UsuarioNewDTO(Integer codigo, String nome, String email, Set<Integer> perfis, Integer status) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.email = email;
		this.perfis = perfis;
		this.status = status;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Integer> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
