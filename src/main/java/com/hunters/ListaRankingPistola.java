package com.hunters;

import intefaces.ICrudController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.AbstractBean;
import model.JogadorRankingPistolaBean;

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
import dao.GenericDAO;

public class ListaRankingPistola extends BasePage{
	
	private static final long serialVersionUID = 1L;
	
	private FeedbackPanel feedbackPanel = (FeedbackPanel) new CustomFeedbackPanel("feedback").setOutputMarkupId(true);
	
	private ListView<JogadorRankingPistolaBean> listViewJogadorRankingPistola;
	private WebMarkupContainer divListaJogadorRankingPistola;
	private List<JogadorRankingPistolaBean> listaJogadorPistolaPistola;
	
	private ICrudController controller = getController();
	
	/*O formFiltro foi implementado nas duas
	 * classes de listagem pois não dava para coloca-lo
	 * no BasePage pois existe outras classes que herdam de 
	 * BasePage que não necessita de filtros
	 * */
	private Form<?> formFiltro = new Form<>("formFiltro");
	
	public ListaRankingPistola() {
		super();
		
		listaJogadorPistolaPistola = GenericDAO.getInstance().getRankingPistola();
		add(formFiltro);
		add(feedbackPanel);
		
		add(criarLinkCriarJogadorRankingPistola());
		addOrReplace(criarDivLinkViewJogadorRankingPistola());
		
//		formFiltro.add(criarTextFieldPesquisa());
//		formFiltro.add(criarBotaoPesquisar());
	}
	
	
	private Link<String> criarLinkCriarJogadorRankingPistola(){
		
		Link<String> link = new Link<String>("inserirJogadorRanking") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				RankingPistolaEditForm JogadorRankingPistolaEditForm = new RankingPistolaEditForm(new JogadorRankingPistolaBean(), CASO_USO_INCLUIR);
				setResponsePage(JogadorRankingPistolaEditForm);
			}
		};
		
		return link;
	}
	
	private WebMarkupContainer criarDivLinkViewJogadorRankingPistola(){
		listaJogadorPistolaPistola = ordenarPosicoes();
		
		divListaJogadorRankingPistola = new WebMarkupContainer("divListaJogadorRankingPistola");
		divListaJogadorRankingPistola.setOutputMarkupId(true);
		
		listViewJogadorRankingPistola = new ListView<JogadorRankingPistolaBean>("listaJogadorRankingPistola", listaJogadorPistolaPistola) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<JogadorRankingPistolaBean> item) {
				JogadorRankingPistolaBean JogadorRankingPistolaBean = item.getModelObject();
				item.add(new Label("posicao", JogadorRankingPistolaBean.getPosicao()+"°").setOutputMarkupId(true));
				item.add(new Label("nome", JogadorRankingPistolaBean.getNome()).setOutputMarkupId(true));
				item.add(new Label("pontos", JogadorRankingPistolaBean.getPontos()).setOutputMarkupId(true));
				item.add(criarLinkEditar(JogadorRankingPistolaBean));
				item.add(criarLinkExcluir(JogadorRankingPistolaBean));
			}
			
		};
		
		listViewJogadorRankingPistola.setOutputMarkupId(true);
		divListaJogadorRankingPistola.addOrReplace(listViewJogadorRankingPistola);
		return divListaJogadorRankingPistola;
	}
	
	private List<JogadorRankingPistolaBean> ordenarPosicoes(){
		
		List<JogadorRankingPistolaBean> jogadores = new ArrayList<JogadorRankingPistolaBean>();
        List<JogadorRankingPistolaBean> listAux = new ArrayList<JogadorRankingPistolaBean>();

	        jogadores = listaJogadorPistolaPistola;

	        listAux = jogadores;
	        JogadorRankingPistolaBean jogadorAux;

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
		
		Link<String> link = new Link<String>("editarJogadorRankingPistola") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				JogadorRankingPistolaBean JogadorRankingPistolaBean = (JogadorRankingPistolaBean) table;
				RankingPistolaEditForm JogadorRankingPistolaEditForm = new RankingPistolaEditForm(JogadorRankingPistolaBean, CASO_USO_EDITAR);
				setResponsePage(JogadorRankingPistolaEditForm);
			}
			
		};
		
		return link;
	}
	
	@Override
	public AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table) {
		AjaxLink<String> link = new AjaxLink<String>("excluirJogadorRankingPistola") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				JogadorRankingPistolaBean JogadorRankingPistolaBean = (JogadorRankingPistolaBean) table;
				RankingPistolaEditForm JogadorRankingPistolaEditForm = new RankingPistolaEditForm(JogadorRankingPistolaBean, CASO_USO_EXCLUIR);
				setResponsePage(JogadorRankingPistolaEditForm);
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
//		textFieldPesquisa = (TextField<String>) new TextField<String>("filtrarJogadorRankingPistola", new Model<String>()).add(new AjaxEventBehavior("keydown"){
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
			listaJogadorPistolaPistola = controller.search(new JogadorRankingPistolaBean(), "nome", nomePesquisa);
		}else{
			listaJogadorPistolaPistola = controller.listarTudo(new JogadorRankingPistolaBean());
		}
		
		listViewJogadorRankingPistola.setModelObject(listaJogadorPistolaPistola);
		target.add(divListaJogadorRankingPistola);
	}
	
	private ICrudController getController(){
		if(controller == null){
			controller = new CrudController();
		}
		return controller;
	}
}
