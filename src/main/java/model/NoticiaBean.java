package model;

import java.io.Serializable;

import anotations.Atributo;
import anotations.Bean;

import com.hunters.ListaNoticia;

@Bean(nome="noticia")
public class NoticiaBean extends AbstractBean<Integer> implements Serializable{
	@Atributo(nome="id", pk=true)
	private Integer id;
	
	@Atributo(nome="dataNoticia")
    private String dataNoticia;
	
	@Atributo(nome="titulo")
    private String titulo;
	
	@Atributo(nome="conteudo")
    private String conteudo;
	
	private static final long serialVersionUID = 1L;
	
    public NoticiaBean(String titulo, String conteudo){
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public NoticiaBean(){

    }
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getPKName() {
		return "id";
	}


	@Override
	public void resetCampos() {
		id = null;
		titulo = null;
		conteudo = null;
		dataNoticia = null;
	}

	@Override
	public String getNomeTabela() {
		return "noticia";
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
		return titulo;
	}
	
	@Override
	public Class<?> getClassList() {
		return ListaNoticia.class;
	}
	
	@Override
	public String getStringChoice() {
		return titulo;
	}

	public String getDataNoticia() {
		return dataNoticia;
	}

	public void setDataNoticia(String dataNoticia) {
		this.dataNoticia = dataNoticia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	@Override
	public String getNomeTitulo() {
		return "Notícia";
	}
}
