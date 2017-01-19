package model;

import java.io.Serializable;

import anotations.Atributo;
import anotations.Bean;

import com.hunters.ListaRankingFuzil;

@Bean(nome="rankingfuzil")
public class JogadorRankingFuzilBean extends AbstractBean<Integer> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Atributo(nome="id", pk=true)
	private Integer id;
	
	@Atributo(nome="jogador")
	private JogadorBean jogador;
	
	@Atributo(nome="posicao")
	private Integer posicao;
	
	@Atributo(nome="pontos")
	private Double pontos;
	
	@Atributo(nome="nome")
	private String nome;
	
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
	
	public JogadorBean getJogador() {
		return jogador;
	}

	public void setJogador(JogadorBean jogador) {
		this.jogador = jogador;
		setNome(jogador!=null?jogador.getNome():"");
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Double getPontos() {
		return pontos;
	}

	public void setPontos(Double pontos) {
		this.pontos = pontos;
	}

	@Override
	public String getPKName() {
		return "id";
	}


	@Override
	public void resetCampos() {
		id = null;
		nome = null;
		pontos = null;
		posicao = null;
		jogador = null;
	}

	@Override
	public String getNomeTabela() {
		return "rankingfuzil";
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
		return ListaRankingFuzil.class;
	}
	
	@Override
	public String getStringChoice() {
		return nome;
	}
	
	@Override
	public String getNomeTitulo() {
		return "Jogador no Ranking de Fuzil";
	}
	
}
