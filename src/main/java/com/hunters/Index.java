package com.hunters;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

public class Index extends WebPage{
	private static final long serialVersionUID = 1L;
	
	public Index() {
		
		String logado = (String) getSession().getAttribute("logado");
		
		if(logado == null || logado.equals("false")){
			setResponsePage(Login.class);
		}
		
		add(criarLinkComecar());
		add(criarLinkJogadores());
		add(criarLinkNoticias());
		add(criarLinkPistola());
		add(criarLinkFuzil());
		add(criarLinkArcoFlecha());
		add(criarLinkSair());
	}
	
	private Link<String> criarLinkComecar(){
		Link<String> link = new Link<String>("comecar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaJogador.class);
			}
		};
		return link;
	}
	
	private Link<String> criarLinkJogadores(){
		Link<String> link = new Link<String>("linkJogadores") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaJogador.class);
			}
		};
		return link;
	}
	
	private Link<String> criarLinkNoticias(){
		Link<String> link = new Link<String>("linkNoticias") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaNoticia.class);
			}
		};
		return link;
	}
	
	private Link<String> criarLinkPistola(){
		Link<String> link = new Link<String>("linkPistola") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaRankingPistola.class);
			}
		};
		return link;
	}
	
	private Link<String> criarLinkFuzil(){
		Link<String> link = new Link<String>("linkFuzil") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaRankingFuzil.class);
			}
		};
		return link;
	}
	
	private Link<String> criarLinkArcoFlecha(){
		Link<String> link = new Link<String>("linkArcoFlecha") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ListaRankingArco.class);
			}
		};
		return link;
	}
	
	private Link<String> criarLinkSair(){
		Link<String> link = new Link<String>("sair") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				sair();
			}
		};
		
		return link;
 	}
	
	@SuppressWarnings("null")
	private void sair(){
		String usuarioLogado = (String) getSession().getAttribute("logado");
		
		if(usuarioLogado != null || usuarioLogado.equals("true")){
			getSession().setAttribute("logado", "false");
			setResponsePage(Login.class);
			return;
		}
	}
	
}