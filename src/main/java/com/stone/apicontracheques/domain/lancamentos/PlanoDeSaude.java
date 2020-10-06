package com.stone.apicontracheques.domain.lancamentos;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;

public class PlanoDeSaude extends Lancamento {
	private static final long serialVersionUID = 1L;

	public PlanoDeSaude(Funcionario funcionario) {
		super(TipoLancamento.DESCONTO, 0, "Plano De Saude",funcionario);
	}

	@Override
	public boolean aplicavel() {
		return funcionario.isDescontaPlanoDeSaude();
	}

	@Override
	public double calcular() {
		return 10;
	}

}
