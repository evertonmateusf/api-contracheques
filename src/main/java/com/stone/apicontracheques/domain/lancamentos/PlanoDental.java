package com.stone.apicontracheques.domain.lancamentos;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;

public class PlanoDental extends Lancamento {
	private static final long serialVersionUID = 1L;

	public PlanoDental(Funcionario funcionario) {
		super(TipoLancamento.DESCONTO, 0, "Plano Dental",funcionario);
	}

	@Override
	public boolean aplicavel() {
		return funcionario.isDescontaPlanoDental();
	}

	@Override
	public double calcular() {
		return 5;
	}

}
