package com.stone.apicontracheques.domain.enums;

public enum TipoLancamento {
	
	DESCONTO(1,"DESCONTO"),
	REMUNERACAO(2,"REMUNERACAO");
	
	private Integer codigo;
	private String descricao;
	
	private TipoLancamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static PerfilAcesso toEnum(Integer codigo) {
		
		if ( codigo == null)  {
			return null;
		}
		
		for (PerfilAcesso x: PerfilAcesso.values()) {
			if ( codigo == x.getCodigo()) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Tipo de remuneração inválido:" + codigo);
	}
}
