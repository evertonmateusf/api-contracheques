package com.stone.apicontracheques.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

public class FuncionarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String sobrenome;
	@CPF(message = "CPF inválido")
	private String documento;
	private String setor;
	private double salarioBruto;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
	private Date dataDeAdmissao;
	private boolean descontaPlanoDeSaude;
	private boolean descontaPlanoDental;
	private boolean descontaValeTransporte;

	public FuncionarioDTO() {

	}

	public FuncionarioDTO(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String nome,
			@NotEmpty(message = "Preenchimento obrigatório") String sobrenome, String documento, String setor,
			double salarioBruto, Date dataDeAdmissao, boolean descontaPlanoDeSaude, boolean descontaPlanoDental,
			boolean descontaValeTransporte) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.documento = documento;
		this.setor = setor;
		this.salarioBruto = salarioBruto;
		this.dataDeAdmissao = dataDeAdmissao;
		this.descontaPlanoDeSaude = descontaPlanoDeSaude;
		this.descontaPlanoDental = descontaPlanoDental;
		this.descontaValeTransporte = descontaValeTransporte;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public Date getDataDeAdmissao() {
		return dataDeAdmissao;
	}

	public void setDataDeAdmissao(Date dataDeAdmissao) {
		this.dataDeAdmissao = dataDeAdmissao;
	}

	public boolean isDescontaPlanoDeSaude() {
		return descontaPlanoDeSaude;
	}

	public void setDescontaPlanoDeSaude(boolean descontaPlanoDeSaude) {
		this.descontaPlanoDeSaude = descontaPlanoDeSaude;
	}

	public boolean isDescontaPlanoDental() {
		return descontaPlanoDental;
	}

	public void setDescontaPlanoDental(boolean descontaPlanoDental) {
		this.descontaPlanoDental = descontaPlanoDental;
	}

	public boolean isDescontaValeTransporte() {
		return descontaValeTransporte;
	}

	public void setDescontaValeTransporte(boolean descontaValeTransporte) {
		this.descontaValeTransporte = descontaValeTransporte;
	}

}
