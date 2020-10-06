package com.stone.apicontracheques.domain.lancamentos;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;

public class INSS extends Lancamento {
	private static final long serialVersionUID = 1L;

	public INSS(Funcionario funcionario) {
		super(TipoLancamento.DESCONTO, 0, "INSS",funcionario);
	}

	@Override
	public boolean aplicavel() {
		return true;
	}

	@Override
	public double calcular() {
		double valorDesconto = 0;
		if (funcionario.getSalarioBruto() <= 1045) {
			valorDesconto = funcionario.getSalarioBruto() * 0.075;
		} else if (funcionario.getSalarioBruto() <= 2089) {
			valorDesconto = funcionario.getSalarioBruto() * 0.09;
		} else if (funcionario.getSalarioBruto() <= 3134.40) {
			valorDesconto = funcionario.getSalarioBruto() * 0.12;
		} else {
			valorDesconto = Math.min(funcionario.getSalarioBruto() * 0.14, 713.09);
		}
		return valorDesconto;
	}

}
