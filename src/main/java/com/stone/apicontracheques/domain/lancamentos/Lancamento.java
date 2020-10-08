package com.stone.apicontracheques.domain.lancamentos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;
import com.stone.apicontracheques.domain.interfaces.IAplicavel;
import com.stone.apicontracheques.domain.interfaces.ICalculavel;

public abstract class Lancamento implements Serializable, IAplicavel, ICalculavel {
	private static final long serialVersionUID = 1L;

	protected TipoLancamento tipoLancamento;
	protected double valor;
	protected String descricao;
	@JsonIgnore
	protected Funcionario funcionario;

	public Lancamento() {
	}

	public Lancamento(TipoLancamento tipoLancamento, double valor, String descricao, Funcionario funcionario) {
		this.tipoLancamento = tipoLancamento;
		this.valor = valor;
		this.descricao = descricao;
		this.funcionario = funcionario;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public double getValor() {
		return Math.round(this.calcular() * 100) / 100d;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
}
