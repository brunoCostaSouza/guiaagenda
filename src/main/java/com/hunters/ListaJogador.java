package com.hunters;

import intefaces.ICrudController;

import java.util.List;

import model.AbstractBean;
import model.JogadorBean;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import util.CustomFeedbackPanel;
import controller.CrudController;
import dao.GenericDAO;

public class ListaJogador extends BasePage{
	
	private static final long serialVersionUID = 1L;
	
	private FeedbackPanel feedbackPanel = (FeedbackPanel) new CustomFeedbackPanel("feedback").setOutputMarkupId(true);
	
	private ListView<JogadorBean> listViewJogador;
	private WebMarkupContainer divListaJogador;
	private List<JogadorBean> listaJogador;
	
	private ICrudController controller = getController();
	
	private AjaxSubmitLink linkFiltro;
	
	/*O formFiltro foi implementado nas duas
	 * classes de listagem pois não dava para coloca-lo
	 * no BasePage pois existe outras classes que herdam de 
	 * BasePage que não necessita de filtros
	 * */
	private Form<?> formFiltro = new Form<>("formFiltro");
	
	public ListaJogador() {
		super();
		
		listaJogador = GenericDAO.getInstance().getTodosJogadores();
		add(formFiltro);
		add(feedbackPanel);
		
		add(criarLinkCriarJogador());
		addOrReplace(criarDivLinkViewJogador());
		
		formFiltro.add(criarTextFieldPesquisa());
		formFiltro.add(criarBotaoPesquisar());
		
		formFiltro.add(criarTextFieldPesquisaCpf());
		formFiltro.add(criarBotaoPesquisarCpf());
	}
	
	
	private Link<String> criarLinkCriarJogador(){
		
		Link<String> link = new Link<String>("criarJogador") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				JogadorEditForm JogadorEditForm = new JogadorEditForm(new JogadorBean(), CASO_USO_INCLUIR);
				setResponsePage(JogadorEditForm);
			}
		};
		
		return link;
	}
	
	private WebMarkupContainer criarDivLinkViewJogador(){
		
		divListaJogador = new WebMarkupContainer("divListaJogador");
		divListaJogador.setOutputMarkupId(true);
		
		listViewJogador = new ListView<JogadorBean>("listaJogador", listaJogador) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<JogadorBean> item) {
				JogadorBean JogadorBean = item.getModelObject();
				item.add(new Label("nomeJogador", JogadorBean.getNome()).setOutputMarkupId(true));
				item.add(new Label("cpfJogador", JogadorBean.getCpf()).setOutputMarkupId(true));
				item.add(criarLinkEditar(JogadorBean));
				item.add(criarLinkExcluir(JogadorBean));
			}
			
		};
		
		listViewJogador.setOutputMarkupId(true);
		divListaJogador.addOrReplace(listViewJogador);
		return divListaJogador;
	}
	
	@Override
	public Link<String> criarLinkEditar(final AbstractBean<?> table){
		
		Link<String> link = new Link<String>("editarJogador") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				JogadorBean JogadorBean = (JogadorBean) table;
				JogadorEditForm JogadorEditForm = new JogadorEditForm(JogadorBean, CASO_USO_EDITAR);
				setResponsePage(JogadorEditForm);
			}
			
		};
		
		return link;
	}
	
	@Override
	public AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table) {
		AjaxLink<String> link = new AjaxLink<String>("excluirJogador") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				JogadorBean JogadorBean = (JogadorBean) table;
				JogadorEditForm JogadorEditForm = new JogadorEditForm(JogadorBean, CASO_USO_EXCLUIR);
				setResponsePage(JogadorEditForm);
			}
			
		};
		
		return link;
	}
	
	
	private AjaxSubmitLink criarBotaoPesquisar(){
		linkFiltro = new AjaxSubmitLink("botaoFiltrar", formFiltro) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);
				filtrar(target);
			}
		};
		return linkFiltro;
	}
	
	private AjaxSubmitLink criarBotaoPesquisarCpf(){
		AjaxSubmitLink linkFiltro = new AjaxSubmitLink("btnFiltrarCpf", formFiltro) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);
				filtrarCpf(target);
			}
		};
		return linkFiltro;
	}
	
	@SuppressWarnings("unchecked")
	private TextField<String> criarTextFieldPesquisa(){
		textFieldPesquisa = (TextField<String>) new TextField<String>("filtrarJogador", new Model<String>()).add(new AjaxEventBehavior("keydown"){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				target.appendJavaScript("filtro();");
			}
			
		});
		textFieldPesquisa.setOutputMarkupId(true);
		return (TextField<String>) textFieldPesquisa;
	}
	
	@SuppressWarnings("unchecked")
	private TextField<String> criarTextFieldPesquisaCpf(){
		textFieldPesquisaCpf = (TextField<String>) new TextField<String>("filtrarJogadorCpf", new Model<String>()).add(new AjaxEventBehavior("keydown"){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				target.appendJavaScript("$('#botaoFiltrarCpf').click();");
			}
			
		});
		
		textFieldPesquisaCpf.setOutputMarkupId(true);
		return (TextField<String>) textFieldPesquisaCpf;
	}
	
	@Override
	public void filtrar(AjaxRequestTarget target){
		
		String nomePesquisa = (String) textFieldPesquisa.getModelObject();
		
		if(nomePesquisa != null && !nomePesquisa.replaceAll(" ", "").equals("")){
			listaJogador = controller.search(new JogadorBean(), "nome", nomePesquisa);
		}else{
			listaJogador = controller.listarTudo(new JogadorBean());
		}
		
		listViewJogador.setModelObject(listaJogador);
		target.add(divListaJogador);
	}
	
	public void filtrarCpf(AjaxRequestTarget target){
		
		String cpfPesquisa = (String) textFieldPesquisaCpf.getModelObject();
		
		if(cpfPesquisa != null && !cpfPesquisa.replaceAll(" ", "").equals("")){
			listaJogador = controller.search(new JogadorBean(), "cpf", cpfPesquisa);
		}else{
			listaJogador = controller.listarTudo(new JogadorBean());
		}
		
		listViewJogador.setModelObject(listaJogador);
		target.add(divListaJogador);
	}
	
	private ICrudController getController(){
		if(controller == null){
			controller = new CrudController();
		}
		return controller;
	}
}
