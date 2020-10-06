package com.stone.apicontracheques.domain.lancamentos;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;

public class ValeTransporte extends Lancamento {
	private static final long serialVersionUID = 1L;

	public ValeTransporte(Funcionario funcionario) {
		super(TipoLancamento.DESCONTO, 0, "Vale Transporte",funcionario);
	}

	@Override
	public boolean aplicavel() {
		return funcionario.getSalarioBruto() > 1500 && funcionario.isDescontaValeTransporte();
	}

	@Override
	public double calcular() {
		double valorDesconto = funcionario.getSalarioBruto() * 0.06;
		return valorDesconto;
	}

}
