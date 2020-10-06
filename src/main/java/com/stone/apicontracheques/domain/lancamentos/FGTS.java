package com.stone.apicontracheques.domain.lancamentos;

import com.stone.apicontracheques.domain.Funcionario;
import com.stone.apicontracheques.domain.enums.TipoLancamento;

public class FGTS extends Lancamento {
	private static final long serialVersionUID = 1L;

	public FGTS(Funcionario funcionario) {
		super(TipoLancamento.DESCONTO, 0, "FGTS",funcionario);
	}

	@Override
	public boolean aplicavel() {
		return true;
	}

	@Override
	public double calcular() {
		return funcionario.getSalarioBruto() * 0.08;
	}

}
