package com.stone.apicontracheques.domain.enums;

public enum StatusUsuario {
	
	ATIVO(1,"Ativo"),
	BLOQUEADO(2,"Bloqueado");
	
	private Integer codigo;
	private String descricao;
	
	private StatusUsuario(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static StatusUsuario toEnum(Integer codigo) {
		
		if ( codigo == null)  {
			return null;
		}
		
		for (StatusUsuario x: StatusUsuario.values()) {
			if ( codigo == x.getCodigo()) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Status inválido:" + codigo);
	}
}
