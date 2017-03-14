package com.hunters;

import model.AbstractBean;
import model.JogadorBean;
import model.JogadorRankingArcoBean;
import model.JogadorRankingFuzilBean;
import model.JogadorRankingPistolaBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;


public class JogadorEditForm extends EditForm{
	private static final long serialVersionUID = 1L;
	
	private TextField<String> textNome;
	private TextField<String> textCpf;
	private TextField<Integer> textId;
	
	public JogadorEditForm(AbstractBean<?> bean, int userCase) {
		super(bean, userCase);
		adicionarCampos();
	}
	
	@Override
	public void adicionarCampos() {
		getForm().add(criarCampoId());
		getForm().add(criarCampoNome());
		getForm().add(criarCampoCpf());
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
	
	private TextField<String> criarCampoNome(){
		textNome = new TextField<>("nome");
		textNome.setRequired(true);
		textNome.setOutputMarkupId(true);
		textNome.setEnabled(isComponentEnable());
		return textNome;
	}
	
	private TextField<String> criarCampoCpf(){
		textCpf = new TextField<>("cpf");
		textCpf.setRequired(true);
		textCpf.setOutputMarkupId(true);
		textCpf.setEnabled(isComponentEnable());
		return textCpf;
	}
	
	@Override
	public boolean afterPersistOrSave(AjaxRequestTarget target) {return true;}

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
		JogadorBean bean = (JogadorBean) getForm().getModelObject();
		
		JogadorRankingPistolaBean jpb = controller.searchSemAspas(new JogadorRankingPistolaBean(), "jogador", bean.getId());
		JogadorRankingFuzilBean jfb = controller.searchSemAspas(new JogadorRankingFuzilBean(), "jogador", bean.getId());
		JogadorRankingArcoBean jab = controller.searchSemAspas(new JogadorRankingArcoBean(), "jogador", bean.getId());
		
		if(jpb!=null){
			controller.remove(jpb);
		}
		
		if(jfb!=null){
			controller.remove(jfb);
		}
		if(jab!=null){
			controller.remove(jab);
		}
	}

	@Override
	public void beforeDeleteItem() {
		
	}
	
}
