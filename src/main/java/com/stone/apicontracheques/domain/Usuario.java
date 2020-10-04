package com.stone.apicontracheques.domain;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.stone.apicontracheques.domain.enums.PerfilAcesso;
import com.stone.apicontracheques.domain.enums.StatusUsuario;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonTypeName("usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer codigo;
    private String nome;
    //@Column(unique=true)
    private String email;
    @JsonIgnore
    private String senha;
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
    private Date dataHoraUltimoBloqueio;
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
    private Date dataHoraUltimaAlteracao;
    @OneToOne()
    @JsonIgnore
    private Usuario usuarioInclusao;
    @OneToOne()
    @JsonIgnore
    private Usuario usuarioUltimaAlteracao;
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="PERFIS")
    private Set<Integer> perfis = new HashSet<Integer>() ;
    
    private Integer status;
    
    public Usuario() {
    	
    }
    
	public Usuario(Integer codigo, String nome, String login, String senha, Date dataHoraUltimoBloqueio,
			Date dataHoraUltimaAlteracao, StatusUsuario status) {
		this.codigo = codigo;
		this.nome = nome;
		this.email = login;
		this.senha = senha;
		this.dataHoraUltimoBloqueio = dataHoraUltimoBloqueio;
		this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
		this.status = status.getCodigo();
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return email;
	}
	public void setLogin(String login) {
		this.email = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Set<PerfilAcesso> getPerfis() {
		return perfis.stream().map(x -> PerfilAcesso.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(PerfilAcesso perfilAcesso) {
		this.perfis.add(perfilAcesso.getCodigo());
	}

	public Date getDataHoraUltimoBloqueio() {
		return dataHoraUltimoBloqueio;
	}
	public void setDataHoraUltimoBloqueio(Date dataHoraUltimoBloqueio) {
		this.dataHoraUltimoBloqueio = dataHoraUltimoBloqueio;
	}
	public Date getDataHoraUltimaAlteracao() {
		return dataHoraUltimaAlteracao;
	}
	public void setDataHoraUltimaAlteracao(Date dataHoraUltimaAlteracao) {
		this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
	}
	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}
	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	public Usuario getUsuarioUltimaAlteracao() {
		return usuarioUltimaAlteracao;
	}
	public void setUsuarioUltimaAlteracao(Usuario usuarioUltimaAlteracao) {
		this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
	}
	
	public StatusUsuario getStatus() {
		return StatusUsuario.toEnum(status);
	}
	public void setStatus(StatusUsuario status) {
		this.status = status.getCodigo();
	}

    
    
}
