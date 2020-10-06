package com.stone.apicontracheques.domain.lancamentos;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;

public class Salario extends Lancamento {
	private static final long serialVersionUID = 1L;

	public Salario(Funcionario funcionario) {
		super(TipoLancamento.REMUNERACAO, 0, "Salário",funcionario);
	}

	@Override
	public boolean aplicavel() {
		return true;
	}

	@Override
	public double calcular() {
		return funcionario.getSalarioBruto();
	}

}
