package com.hunters;

import model.AbstractBean;
import model.JogadorBean;
import model.JogadorRankingFuzilBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;


public class RankingFuzilEditForm extends EditForm{
	private static final long serialVersionUID = 1L;
	
	private TextField<Double> textPontos;
	private TextField<Integer> textPosicao;
	private TextField<Integer> textId;
	
	public RankingFuzilEditForm(AbstractBean<?> bean, int userCase) {
		super(bean, userCase);
		adicionarCampos();
	}
	
	@Override
	public void adicionarCampos() {
		getForm().add(criarCampoId());
		getForm().add(criarCampoPontos());
		getForm().add(criarDropDown("jogador", new JogadorBean()));
		getForm().add(criarCampoPosicao());
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
	
	private TextField<Double> criarCampoPontos(){
		textPontos = new TextField<>("pontos");
		textPontos.setRequired(true);
		textPontos.setOutputMarkupId(true);
		textPontos.setEnabled(isComponentEnable());
		return textPontos;
	}
	
	private TextField<Integer> criarCampoPosicao(){
		textPosicao = new TextField<>("posicao");
		textPosicao.setOutputMarkupId(true);
		textPosicao.setEnabled(false);
		return textPosicao;
	}
	
	
	
	@Override
	public boolean afterPersistOrSave(AjaxRequestTarget target) {
		JogadorRankingFuzilBean jogAux = (JogadorRankingFuzilBean) getForm().getModelObject();
		if(jogAux.getId()==null && controller.search(bean, "nome", (Object)jogAux.getNome()) != null){
			error("Já existe esse Jogador no Ranking");
			target.add(feedbackPanel);
			return false;
		}
		return true;
	}

	@Override
	public void beforePersistOrSave(AjaxRequestTarget target) {}
	
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
		// TODO Auto-generated method stub
		
	}
}
