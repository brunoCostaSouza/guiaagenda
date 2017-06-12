package model;

import java.io.Serializable;
import java.util.Date;

import anotations.Atributo;
import anotations.Bean;

@Bean(nome="professor")
public class Professor extends AbstractBean<Integer> implements Serializable{
	private static final long serialVersionUID = 1L;

	@Atributo(nome="id", nomeColuna="ID", pk=true)
	private Integer id;
	
	@Atributo(nome="nome", nomeColuna="NOME")
	private String nome;
	
	@Atributo(nome="senha", nomeColuna="SENHA")
	private String senha;
	
	@Atributo(nome="dataCadastro", nomeColuna="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Atributo(nome="cpf", nomeColuna="CPF")
	private String cpf;
	
	@Atributo(nome="email", nomeColuna="EMAIL")
	private String email;
	
	@Atributo(nome="ativo", nomeColuna="ATIVO")
	private Boolean ativo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String getNomeTitulo() {
		return "Professor";
	}
	
	@Override
	public void resetCampos() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getStringChoice() {
		return nome;
	}

	@Override
	public Class<?> getClassList() {
		// TODO Auto-generated method stub
		return null;
	}

}
