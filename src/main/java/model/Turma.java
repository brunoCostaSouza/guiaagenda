package model;

import java.io.Serializable;

import anotations.Atributo;
import anotations.Bean;

@Bean(nome="turma")
public class Turma extends AbstractBean<Integer> implements Serializable{
	private static final long serialVersionUID = 1L;

	@Atributo(nome="id", nomeColuna="ID",pk=true)
	private Integer id;
	
	@Atributo(nome="descricao", nomeColuna="DESCRICAO")
	private String descricao;

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

	@Override
	public String getNomeTitulo() {
		return "Turma";
	}

	@Override
	public void resetCampos() {
		this.id = null;
		this.descricao = null;
	}

	@Override
	public String getStringChoice() {
		return descricao;
	}

	@Override
	public Class<?> getClassList() {
		return null;
	}
	
	
	

}
