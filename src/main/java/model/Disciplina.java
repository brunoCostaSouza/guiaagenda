package model;

import java.io.Serializable;

import anotations.Atributo;
import anotations.Bean;

@Bean(nome="disciplina")
public class Disciplina extends AbstractBean<Integer> implements Serializable{
	private static final long serialVersionUID = 1L;

	@Atributo(nome="id", nomeColuna="ID",pk=true)
	private Integer id;
	
	@Atributo(nome="descricao", nomeColuna="DESCRICAO")
	private String descricao;
	
	@Atributo(nome="ativo", nomeColuna="ATIVO")
	private Boolean ativo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String getNomeTitulo() {
		return "Disciplina";
	}

	@Override
	public void resetCampos() {
		this.id = null;
		this.descricao = null;
		this.ativo = null;
	}

	@Override
	public String getStringChoice() {
		return descricao;
	}

	@Override
	public Class<?> getClassList() {
		// TODO Auto-generated method stub
		return null;
	}

}
