package com.stone.apicontracheques.domain.lancamentos;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;

public class ImpostoDeRenda extends Lancamento {
	private static final long serialVersionUID = 1L;

	public ImpostoDeRenda(Funcionario funcionario) {
		super(TipoLancamento.DESCONTO, 0, "Imposto De Renda",funcionario);
	}

	@Override
	public boolean aplicavel() {
		return funcionario.getSalarioBruto() > 1903.98;
	}

	@Override
	public double calcular() {
		double valorDesconto = 0;
		if (funcionario.getSalarioBruto() <= 1903.98) {
			valorDesconto = 0;
		}else if (funcionario.getSalarioBruto() <= 2826.65) {
			valorDesconto = Math.min(funcionario.getSalarioBruto() * 0.075, 142.80);
		} else if (funcionario.getSalarioBruto() <= 3751.05) {
			valorDesconto = Math.min(funcionario.getSalarioBruto() * 0.15, 354.80);
		} else if (funcionario.getSalarioBruto() <= 4664.68) {
			valorDesconto = Math.min(funcionario.getSalarioBruto() * 0.225, 636.13);
		} else {
			valorDesconto = Math.min(funcionario.getSalarioBruto() * 0.275, 869.36);
		}
		return valorDesconto;
	}

}
