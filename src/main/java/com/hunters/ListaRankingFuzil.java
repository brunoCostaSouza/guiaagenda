package com.hunters;

import intefaces.ICrudController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.AbstractBean;
import model.JogadorRankingFuzilBean;

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

public class ListaRankingFuzil extends BasePage{
	
	private static final long serialVersionUID = 1L;
	
	private FeedbackPanel feedbackPanel = (FeedbackPanel) new CustomFeedbackPanel("feedback").setOutputMarkupId(true);
	
	private ListView<JogadorRankingFuzilBean> listViewJogadorRankingFuzil;
	private WebMarkupContainer divListaJogadorRankingFuzil;
	private List<JogadorRankingFuzilBean> listaJogadorRankingFuzil;
	
	private ICrudController controller = getController();
	
	/*O formFiltro foi implementado nas duas
	 * classes de listagem pois não dava para coloca-lo
	 * no BasePage pois existe outras classes que herdam de 
	 * BasePage que não necessita de filtros
	 * */
	private Form<?> formFiltro = new Form<>("formFiltro");
	
	public ListaRankingFuzil() {
		super();
		
		listaJogadorRankingFuzil = controller.listarTudo(new JogadorRankingFuzilBean());
		add(formFiltro);
		add(feedbackPanel);
		
		add(criarLinkCriarJogadorRankingFuzil());
		addOrReplace(criarDivLinkViewJogadorRankingFuzil());
		
//		formFiltro.add(criarTextFieldPesquisa());
//		formFiltro.add(criarBotaoPesquisar());
	}
	
	
	private Link<String> criarLinkCriarJogadorRankingFuzil(){
		
		Link<String> link = new Link<String>("inserirJogadorRanking") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				RankingFuzilEditForm JogadorRankingFuzilEditForm = new RankingFuzilEditForm(new JogadorRankingFuzilBean(), CASO_USO_INCLUIR);
				setResponsePage(JogadorRankingFuzilEditForm);
			}
		};
		
		return link;
	}
	
	private WebMarkupContainer criarDivLinkViewJogadorRankingFuzil(){
		listaJogadorRankingFuzil = ordenarPosicoes();
		
		divListaJogadorRankingFuzil = new WebMarkupContainer("divListaJogadorRankingFuzil");
		divListaJogadorRankingFuzil.setOutputMarkupId(true);
		
		listViewJogadorRankingFuzil = new ListView<JogadorRankingFuzilBean>("listaJogadorRankingFuzil", listaJogadorRankingFuzil) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<JogadorRankingFuzilBean> item) {
				JogadorRankingFuzilBean JogadorRankingFuzilBean = item.getModelObject();
				item.add(new Label("posicao", JogadorRankingFuzilBean.getPosicao()+"°").setOutputMarkupId(true));
				item.add(new Label("nome", JogadorRankingFuzilBean.getNome()).setOutputMarkupId(true));
				item.add(new Label("pontos", JogadorRankingFuzilBean.getPontos()).setOutputMarkupId(true));
				item.add(criarLinkEditar(JogadorRankingFuzilBean));
				item.add(criarLinkExcluir(JogadorRankingFuzilBean));
			}
			
		};
		
		listViewJogadorRankingFuzil.setOutputMarkupId(true);
		divListaJogadorRankingFuzil.addOrReplace(listViewJogadorRankingFuzil);
		return divListaJogadorRankingFuzil;
	}
	
	private List<JogadorRankingFuzilBean> ordenarPosicoes(){
		
		List<JogadorRankingFuzilBean> jogadores = new ArrayList<JogadorRankingFuzilBean>();
        List<JogadorRankingFuzilBean> listAux = new ArrayList<JogadorRankingFuzilBean>();

	        jogadores = listaJogadorRankingFuzil;

	        listAux = jogadores;
	        JogadorRankingFuzilBean jogadorAux;

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
		
		Link<String> link = new Link<String>("editarJogadorRankingFuzil") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				JogadorRankingFuzilBean JogadorRankingFuzilBean = (JogadorRankingFuzilBean) table;
				RankingFuzilEditForm JogadorRankingFuzilEditForm = new RankingFuzilEditForm(JogadorRankingFuzilBean, CASO_USO_EDITAR);
				setResponsePage(JogadorRankingFuzilEditForm);
			}
			
		};
		
		return link;
	}
	
	@Override
	public AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table) {
		AjaxLink<String> link = new AjaxLink<String>("excluirJogadorRankingFuzil") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				JogadorRankingFuzilBean JogadorRankingFuzilBean = (JogadorRankingFuzilBean) table;
				RankingFuzilEditForm JogadorRankingFuzilEditForm = new RankingFuzilEditForm(JogadorRankingFuzilBean, CASO_USO_EXCLUIR);
				setResponsePage(JogadorRankingFuzilEditForm);
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
//		textFieldPesquisa = (TextField<String>) new TextField<String>("filtrarJogadorRankingFuzil", new Model<String>()).add(new AjaxEventBehavior("keydown"){
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
			listaJogadorRankingFuzil = controller.search(new JogadorRankingFuzilBean(), "nome", nomePesquisa);
		}else{
			listaJogadorRankingFuzil = controller.listarTudo(new JogadorRankingFuzilBean());
		}
		
		listViewJogadorRankingFuzil.setModelObject(listaJogadorRankingFuzil);
		target.add(divListaJogadorRankingFuzil);
	}
	
	private ICrudController getController(){
		if(controller == null){
			controller = new CrudController();
		}
		return controller;
	}
}
