package com.stone.apicontracheques.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.stone.apicontracheques.domain.enums.TipoLancamento;
import com.stone.apicontracheques.domain.lancamentos.FGTS;
import com.stone.apicontracheques.domain.lancamentos.INSS;
import com.stone.apicontracheques.domain.lancamentos.ImpostoDeRenda;
import com.stone.apicontracheques.domain.lancamentos.Lancamento;
import com.stone.apicontracheques.domain.lancamentos.PlanoDeSaude;
import com.stone.apicontracheques.domain.lancamentos.PlanoDental;
import com.stone.apicontracheques.domain.lancamentos.Salario;
import com.stone.apicontracheques.domain.lancamentos.ValeTransporte;
import com.stone.apicontracheques.dto.ContrachequeDTO;

public class CalculadoraContracheque{

	private List<Lancamento> lancamentos = new ArrayList<>();
	private Funcionario funcionario;
	private double totalDescontos = 0;

	public CalculadoraContracheque() {
	}

	public CalculadoraContracheque(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public List<Lancamento> getLancamentos() {
		if(lancamentos.isEmpty()){
			adicionaLancamentoSeAplicavel(new Salario(funcionario));
			adicionaLancamentoSeAplicavel(new INSS(funcionario));
			adicionaLancamentoSeAplicavel(new ImpostoDeRenda(funcionario));
			adicionaLancamentoSeAplicavel(new PlanoDeSaude(funcionario));
			adicionaLancamentoSeAplicavel(new PlanoDental(funcionario));
			adicionaLancamentoSeAplicavel(new ValeTransporte(funcionario));
			adicionaLancamentoSeAplicavel(new FGTS(funcionario));
		}
		return lancamentos;
	}

	public void adicionaLancamentoSeAplicavel(Lancamento lancamento) {
		if(lancamento.aplicavel()){
			this.lancamentos.add(lancamento);
		}
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public ContrachequeDTO getContraCheque(){
		ContrachequeDTO contracheque = new ContrachequeDTO();
		contracheque.setMesReferencia(Calendar.getInstance().get(Calendar.MONTH)+1 );
		contracheque.setSalarioBruto(funcionario.getSalarioBruto());
		contracheque.setSalarioLiquido(getSalarioLiquido());
		contracheque.setTotalDescontos(getTotalDescontos());
		contracheque.setLancamentos(lancamentos);
		return contracheque;
	}

	private double getSalarioLiquido() {
		lancamentos = getLancamentos();
		double salarioLiquido = funcionario.getSalarioBruto() + getTotalDescontos();
		return Math.round(salarioLiquido * 100) / 100d;
	}

	private double getTotalDescontos() {
		lancamentos = getLancamentos();
		if( totalDescontos == 0){
			for (Lancamento lancamento : lancamentos) {
				if(lancamento.getTipoLancamento() == TipoLancamento.DESCONTO){
					totalDescontos -= lancamento.getValor();
				}
			}
		}
		return Math.round(totalDescontos * 100) / 100d;
	}
	
}
