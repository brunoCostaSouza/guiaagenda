package model;

import java.io.Serializable;

import anotations.Atributo;
import anotations.Bean;

@Bean(nome="responsavel")
public class Responsavel extends AbstractBean<Integer> implements Serializable{
	private static final long serialVersionUID = 1L;

	@Atributo(nome="id", nomeColuna="ID",pk=true)
	private Integer id;
	
	@Atributo(nome="nome", nomeColuna="NOME")
	private String nome;
	
	@Atributo(nome="idade", nomeColuna="IDADE")
	private Integer idade;

	@Atributo(nome="cpf", nomeColuna="CPF")
	private String cpf;
	
	@Atributo(nome="rg", nomeColuna="RG")
	private String rg;
	
	@Atributo(nome="senha", nomeColuna="SENHA")
	private String senha;
	
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

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String getNomeTitulo() {
		return "Responsável";
	}

	@Override
	public void resetCampos() {
		this.id = null;
		this.nome = null;
		this.idade = null;
		this.rg = null;
		this.senha = null;
		this.cpf = null;
	}

	@Override
	public String getStringChoice() {
		return nome;
	}

	@Override
	public Class<?> getClassList() {
		return null;
	}
	
	
	

}
