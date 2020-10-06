package com.stone.apicontracheques.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stone.apicontracheques.domain.lancamentos.Lancamento;

public class Contracheque implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer mesReferencia;
	private double totalDescontos;
	private double salarioBruto;
	private double salarioLiquido;
	private List<Lancamento> lancamentos = new ArrayList<>();

	public Contracheque() {
	}

	public Contracheque(Integer mesReferencia, double totalDescontos, double salarioBruto, double salarioLiquido,
			List<Lancamento> lancamentos) {
		this.mesReferencia = mesReferencia;
		this.totalDescontos = totalDescontos;
		this.salarioBruto = salarioBruto;
		this.salarioLiquido = salarioLiquido;
		this.lancamentos = lancamentos;
	}

	public Integer getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(Integer i) {
		this.mesReferencia = i;
	}

	public double getTotalDescontos() {
		return totalDescontos;
	}

	public void setTotalDescontos(double totalDescontos) {
		this.totalDescontos = totalDescontos;
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getSalarioLiquido() {
		return salarioLiquido;
	}

	public void setSalarioLiquido(double salarioLiquido) {
		this.salarioLiquido = salarioLiquido;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public void addLancamentos(Lancamento lancamento) {
		this.lancamentos.add(lancamento);
	}

	public List<Lancamento> getLancamentos() {
		return this.lancamentos;
	}
	
}
