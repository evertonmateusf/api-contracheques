package com.stone.apicontracheques.domain.enums;

public enum PerfilAcesso {
	
	ADMINISTRADOR(1,"ROLE_ADMIN");
	
	private Integer codigo;
	private String descricao;
	
	private PerfilAcesso(int codigo, String descricao) {
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
		
		throw new IllegalArgumentException("Perfil inválido:" + codigo);
	}
}
