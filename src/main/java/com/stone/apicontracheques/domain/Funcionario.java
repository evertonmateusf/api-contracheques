package com.stone.apicontracheques.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// @ManyToOne
	// @JoinColumn(name = "tecnico_codigo")
	// private Tecnico tecnico;

	private String nome;
	private String sobrenome;

	private String documento;
	private String setor;
	private double salarioBruto;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
	private Date dataDeAdmissao;
	private boolean descontaPlanoDeSaude;
	private boolean descontaPlanoDental;
	private boolean descontaValeTransporte;

	public Funcionario() {

	}

	public Funcionario(Integer id, String nome, String sobrenome, String documento, String setor, double salarioBruto,
			Date dataDeAdmissao, boolean descontaPlanoDeSaude, boolean descontaPlanoDental,
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario [dataDeAdmissao=" + dataDeAdmissao + ", descontaPlanoDeSaude=" + descontaPlanoDeSaude
				+ ", descontaPlanoDental=" + descontaPlanoDental + ", descontaValeTransporte=" + descontaValeTransporte
				+ ", documento=" + documento + ", id=" + id + ", nome=" + nome + ", salarioBruto=" + salarioBruto
				+ ", setor=" + setor + ", sobrenome=" + sobrenome + "]";
	}

	

}
