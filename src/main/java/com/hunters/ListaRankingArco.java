package com.hunters;

import intefaces.ICrudController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.AbstractBean;
import model.JogadorRankingArcoBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import util.CustomFeedbackPanel;
import controller.CrudController;

public class ListaRankingArco extends BasePage{
	
	private static final long serialVersionUID = 1L;
	
	private FeedbackPanel feedbackPanel = (FeedbackPanel) new CustomFeedbackPanel("feedback").setOutputMarkupId(true);
	
	private ListView<JogadorRankingArcoBean> listViewJogadorRankingArco;
	private WebMarkupContainer divListaJogadorRankingArco;
	private List<JogadorRankingArcoBean> listaJogadorRankingArco;
	
	private ICrudController controller = getController();
	
	/*O formFiltro foi implementado nas duas
	 * classes de listagem pois não dava para coloca-lo
	 * no BasePage pois existe outras classes que herdam de 
	 * BasePage que não necessita de filtros
	 * */
	private Form<?> formFiltro = new Form<>("formFiltro");
	
	public ListaRankingArco() {
		super();
		
		listaJogadorRankingArco = controller.listarTudo(new JogadorRankingArcoBean());
		add(formFiltro);
		add(feedbackPanel);
		
		add(criarLinkCriarJogadorRankingArco());
		addOrReplace(criarDivLinkViewJogadorRankingArco());
		
//		formFiltro.add(criarTextFieldPesquisa());
//		formFiltro.add(criarBotaoPesquisar());
	}
	
	
	private Link<String> criarLinkCriarJogadorRankingArco(){
		
		Link<String> link = new Link<String>("inserirJogadorRanking") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				RankingArcoEditForm JogadorRankingArcoEditForm = new RankingArcoEditForm(new JogadorRankingArcoBean(), CASO_USO_INCLUIR);
				setResponsePage(JogadorRankingArcoEditForm);
			}
		};
		
		return link;
	}
	
	private WebMarkupContainer criarDivLinkViewJogadorRankingArco(){
		listaJogadorRankingArco = ordenarPosicoes();
		
		divListaJogadorRankingArco = new WebMarkupContainer("divListaJogadorRankingArco");
		divListaJogadorRankingArco.setOutputMarkupId(true);
		
		listViewJogadorRankingArco = new ListView<JogadorRankingArcoBean>("listaJogadorRankingArco", listaJogadorRankingArco) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<JogadorRankingArcoBean> item) {
				JogadorRankingArcoBean JogadorRankingArcoBean = item.getModelObject();
				item.add(new Label("posicao", JogadorRankingArcoBean.getPosicao()+"°").setOutputMarkupId(true));
				item.add(new Label("nome", JogadorRankingArcoBean.getNome()).setOutputMarkupId(true));
				item.add(new Label("pontos", JogadorRankingArcoBean.getPontos()).setOutputMarkupId(true));
				item.add(criarLinkEditar(JogadorRankingArcoBean));
				item.add(criarLinkExcluir(JogadorRankingArcoBean));
			}
			
		};
		
		listViewJogadorRankingArco.setOutputMarkupId(true);
		divListaJogadorRankingArco.addOrReplace(listViewJogadorRankingArco);
		return divListaJogadorRankingArco;
	}
	
	private List<JogadorRankingArcoBean> ordenarPosicoes(){
		
		List<JogadorRankingArcoBean> jogadores = new ArrayList<JogadorRankingArcoBean>();
        List<JogadorRankingArcoBean> listAux = new ArrayList<JogadorRankingArcoBean>();

	        jogadores = listaJogadorRankingArco;

	        listAux = jogadores;
	        JogadorRankingArcoBean jogadorAux;

	        for(int i = 0; i < jogadores.size(); i++){

	            for(int j = 0; j < jogadores.size(); j++){

	                if(jogadores.get(i).getPontos() < jogadores.get(j).getPontos()){
	                    jogadorAux = jogadores.get(i);
	                    listAux.set(i, jogadores.get(j));
	                    listAux.set(j, jogadorAux);
	                }

	            }

	        }

	        Collections.reverse(listAux);

	        for(int i = 0; i < listAux.size(); i++){
	            listAux.get(i).setPosicao(i+1);
	            controller.save(listAux.get(i));
	        }

	        return listAux;
	}
	
	@Override
	public Link<String> criarLinkEditar(final AbstractBean<?> table){
		
		Link<String> link = new Link<String>("editarJogadorRankingArco") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				JogadorRankingArcoBean JogadorRankingArcoBean = (JogadorRankingArcoBean) table;
				RankingArcoEditForm JogadorRankingArcoEditForm = new RankingArcoEditForm(JogadorRankingArcoBean, CASO_USO_EDITAR);
				setResponsePage(JogadorRankingArcoEditForm);
			}
			
		};
		
		return link;
	}
	
	@Override
	public AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table) {
		AjaxLink<String> link = new AjaxLink<String>("excluirJogadorRankingArco") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				JogadorRankingArcoBean JogadorRankingArcoBean = (JogadorRankingArcoBean) table;
				RankingArcoEditForm JogadorRankingArcoEditForm = new RankingArcoEditForm(JogadorRankingArcoBean, CASO_USO_EXCLUIR);
				setResponsePage(JogadorRankingArcoEditForm);
			}
			
		};
		
		return link;
	}
	
//	private AjaxSubmitLink criarBotaoPesquisar(){
//		linkFiltro = new AjaxSubmitLink("botaoFiltrar", formFiltro) {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			protected void onSubmit(AjaxRequestTarget target) {
//				super.onSubmit(target);
//				filtrar(target);
//			}
//		};
//		return linkFiltro;
//	}
	
//	@SuppressWarnings("unchecked")
//	private TextField<String> criarTextFieldPesquisa(){
//		textFieldPesquisa = (TextField<String>) new TextField<String>("filtrarJogadorRankingArco", new Model<String>()).add(new AjaxEventBehavior("keydown"){
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			protected void onEvent(AjaxRequestTarget target) {
//				target.appendJavaScript("filtro();");
//			}
//			
//		});
//		textFieldPesquisa.setOutputMarkupId(true);
//		return (TextField<String>) textFieldPesquisa;
//	}
	
	@Override
	public void filtrar(AjaxRequestTarget target){
		
		String nomePesquisa = (String) textFieldPesquisa.getModelObject();
		
		if(nomePesquisa != null && !nomePesquisa.replaceAll(" ", "").equals("")){
			listaJogadorRankingArco = controller.search(new JogadorRankingArcoBean(), "nome", nomePesquisa);
		}else{
			listaJogadorRankingArco = controller.listarTudo(new JogadorRankingArcoBean());
		}
		
		listViewJogadorRankingArco.setModelObject(listaJogadorRankingArco);
		target.add(divListaJogadorRankingArco);
	}
	
	private ICrudController getController(){
		if(controller == null){
			controller = new CrudController();
		}
		return controller;
	}
}
