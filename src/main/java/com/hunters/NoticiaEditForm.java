package com.hunters;


import model.AbstractBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;


public class NoticiaEditForm extends EditForm{
	private static final long serialVersionUID = 1L;
	
	private TextField<Integer> textId;
	private TextField<String> textTitulo;
	private TextArea<String> textConteudo;
	private TextField<String> textDataNoticia;
	
	public NoticiaEditForm(AbstractBean<?> bean, int userCase) {
		super(bean, userCase);
		adicionarCampos();
	}
	
	@Override
	public void adicionarCampos() {
		getForm().add(criarCampoId());
		getForm().add(criarCampoTitulo());
		getForm().add(criarCampoConteudo());
		getForm().add(criarCampoDataNoticia());
	}

	@Override
	public CheckBox criarCheckBox(String id) {
		CheckBox checkBox = new CheckBox(id);
		checkBox.setOutputMarkupId(true);
		checkBox.setEnabled(!(getUserCase() == CASO_USO_EXCLUIR));
		return checkBox;
	}
	
	private TextField<Integer> criarCampoId(){
		textId = new TextField<>("id");
		textId.setVisible(false);
		textId.setOutputMarkupId(true);
		return textId;
	}
	
	private TextField<String> criarCampoTitulo(){
		textTitulo = new TextField<>("titulo");
		textTitulo.setRequired(true);
		textTitulo.setOutputMarkupId(true);
		textTitulo.setEnabled(isComponentEnable());
		return textTitulo;
	}
	
	private TextField<String> criarCampoDataNoticia(){
		textDataNoticia = new TextField<>("dataNoticia");
		textDataNoticia.setRequired(true);
		textDataNoticia.setOutputMarkupId(true);
		textDataNoticia.setEnabled(false);
		return textDataNoticia;
	}
	
	private TextArea<String> criarCampoConteudo(){
		textConteudo = new TextArea<String>("conteudo");
		textConteudo.setOutputMarkupId(true);
		textConteudo.setRequired(true);
		return textConteudo;
	}
	
	@Override
	public void beforePersistOrSave(AjaxRequestTarget target) {
		
		
	}
	
	@Override
	public AjaxLink<String> criarLinkExcluir(AbstractBean<?> table) {return null;}
	
	@Override
	public Link<String> criarLinkEditar(AbstractBean<?> table) {return null;}

	@Override
	public void filtrar(AjaxRequestTarget target) {
		
	}
	
	@Override
	public void afterDeleteItem() {
		
	}

	@Override
	public void beforeDeleteItem() {
	}
}
