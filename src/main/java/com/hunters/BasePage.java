package com.hunters;

import model.AbstractBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;


public abstract class BasePage extends WebPage{
	private static final long serialVersionUID = 1L;
	
	public static final int CASO_USO_INCLUIR 	= 1;
	public static final int CASO_USO_EDITAR 	= 2;
	public static final int CASO_USO_EXCLUIR 	= 3;
	public static final int CASO_USO_Listar 	= 4;
	
	public AjaxSubmitLink linkFiltro;
	public TextField<?> textFieldPesquisa;
	public TextField<?> textFieldPesquisaCpf;
	
	public abstract AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table);
	public abstract Link<String> criarLinkEditar(final AbstractBean<?> table);
	public abstract void filtrar(AjaxRequestTarget target);
	
	public BasePage() {
		
		String logado = (String) getSession().getAttribute("logado");
		
		if(logado == null || logado.equals("false")){
			setResponsePage(Login.class);
			return;
		}
		
		add(criarLinkInicio());
		addOrReplace(criarLinkListaGasto());
		addOrReplace(criarLinkListaViagem());
		addOrReplace(criarLinkListaRankingPistola());
		addOrReplace(criarLinkListaRankingFuzil());
		addOrReplace(criarLinkListaRankingArco());
	}
	
	private Link<String> criarLinkInicio(){
		
		Link<String> link = new Link<String>("inicio") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(Index.class);
			}
		};
		
		return link;
	}
	
	private Link<String> criarLinkListaViagem(){
		
		Link<String> link = new Link<String>("menuListaJogadores") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaJogador.class);
			}
		};
		
		return link;
	}
	
	private Link<String> criarLinkListaGasto(){
		
		Link<String> link = new Link<String>("menuListaNoticias") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaNoticia.class);
			}
		};
		
		return link;
	}
	
	private Link<String> criarLinkListaRankingPistola(){
		
		Link<String> link = new Link<String>("menuListaRankingPistola") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaRankingPistola.class);
			}
		};
		
		return link;
	}
	
	private Link<String> criarLinkListaRankingFuzil(){
		
		Link<String> link = new Link<String>("menuListaRankingFuzil") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaRankingFuzil.class);
			}
		};
		
		return link;
	}
	
	private Link<String> criarLinkListaRankingArco(){
		
		Link<String> link = new Link<String>("menuListaRankingArco") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaRankingArco.class);
			}
		};
		
		return link;
	}
	
}