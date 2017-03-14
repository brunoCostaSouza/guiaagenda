package model;

import java.io.Serializable;

import anotations.Atributo;
import anotations.Bean;

import com.hunters.ListaJogador;

@Bean(nome="jogador")
public class JogadorBean extends AbstractBean<Integer> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Atributo(nome="id", pk=true)
	private Integer id;
	
	@Atributo(nome="nome")
	private String nome;
	
	@Atributo(nome="cpf")
	private String cpf;
	
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

	public String getCpf() {
		if(cpf!=null){
			return cpf;
		}
		return "";
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Override
	public String getPKName() {
		return "id";
	}


	@Override
	public void resetCampos() {
		id = null;
		nome = null;
		cpf = null;
	}

	@Override
	public String getNomeTabela() {
		return "jogador";
	}

	@Override
	public Integer getPK() {
		return this.getId();
	}

	@Override
	public void setPK(Integer pk) {
		this.setId(pk);
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	@Override
	public Class<?> getClassList() {
		return ListaJogador.class;
	}
	
	@Override
	public String getStringChoice() {
		return nome;
	}
	
	@Override
	public String getNomeTitulo() {
		return "Jogador";
	}
	
}
