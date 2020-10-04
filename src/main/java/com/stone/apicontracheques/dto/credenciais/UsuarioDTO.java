package com.stone.apicontracheques.dto.credenciais;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stone.apicontracheques.domain.Usuario;
import com.stone.apicontracheques.domain.enums.PerfilAcesso;

public class UsuarioDTO {
	
	private Integer codigo;

	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
    private String email;
	@JsonIgnore
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=20, message="O tamanho deve ser entre 7 e 20 caracteres")
	private String senha;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dataHoraUltimoBloqueio;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dataHoraUltimaAlteracao;
    private Usuario usuarioInclusao;
    private Usuario usuarioUltimaAlteracao;
	private Set<Integer> perfis = new HashSet<Integer>();
    private Integer status;
	private boolean solicitacaoRegiaoEnviada;
    

	public UsuarioDTO() {
    }
	
    public UsuarioDTO(Integer codigo, String nome, String login, Date dataHoraUltimoBloqueio,
			Date dataHoraUltimaAlteracao, Usuario usuarioInclusao, Usuario usuarioUltimaAlteracao, PerfilAcesso prefilAcesso,
			Integer status, String senha) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.email = login;
		this.dataHoraUltimoBloqueio = dataHoraUltimoBloqueio;
		this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
		this.usuarioInclusao = usuarioInclusao;
		this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
		addPerfis(prefilAcesso);
		this.status = status;
		this.senha = senha;
		this.solicitacaoRegiaoEnviada = false;
	}
    
    public UsuarioDTO(Usuario obj) {
        this.codigo = obj.getCodigo();
		this.nome = obj.getNome();
		this.email = obj.getLogin();
		this.dataHoraUltimoBloqueio = obj.getDataHoraUltimoBloqueio();
		this.dataHoraUltimaAlteracao = obj.getDataHoraUltimaAlteracao();
		this.usuarioInclusao = obj.getUsuarioInclusao();
		this.usuarioUltimaAlteracao = obj.getUsuarioUltimaAlteracao();
		for (PerfilAcesso perfil: obj.getPerfis() ) {
			this.addPerfis(perfil);
		};
		this.status = obj.getStatus().getCodigo();
    }
    
    public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public Integer getUsuarioInclusao() {
		return usuarioInclusao.getCodigo();
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public Integer getUsuarioUltimaAlteracao() {
		return usuarioUltimaAlteracao.getCodigo() ;
	}

	public void setUsuarioUltimaAlteracao(Usuario usuarioUltimaAlteracao) {
		this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
	}

	public Set<PerfilAcesso> getPerfis() {
		return perfis.stream().map(x -> PerfilAcesso.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfis(PerfilAcesso perfilAcesso) {
		this.perfis.add(perfilAcesso.getCodigo());
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public boolean isSolicitacaoRegiaoEnviada() {
		return solicitacaoRegiaoEnviada;
	}

	public void setSolicitacaoRegiaoEnviada(boolean solicitacaoRegiaoEnviada) {
		this.solicitacaoRegiaoEnviada = solicitacaoRegiaoEnviada;
	}

}
