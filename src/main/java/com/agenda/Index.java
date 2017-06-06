package com.agenda;

import model.AbstractBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.Link;

public class Index extends BasePage{
	private static final long serialVersionUID = 1L;
	
	public Index() {
		super();
//		String logado = (String) getSession().getAttribute("logado");
		
//		if(logado == null || logado.equals("false")){
//			setResponsePage(Login.class);
//		}
		
	}

	@Override
	public AjaxLink<String> criarLinkExcluir(AbstractBean<?> table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Link<String> criarLinkEditar(AbstractBean<?> table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filtrar(AjaxRequestTarget target) {
		// TODO Auto-generated method stub
		
	}
	
}