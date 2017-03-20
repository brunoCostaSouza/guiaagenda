package com.hunters;

import intefaces.ICrudController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.AbstractBean;
import model.NoticiaBean;

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


public class ListaNoticia extends BasePage{
	
	private static final long serialVersionUID = 1L;
	
	private FeedbackPanel feedbackPanel = (FeedbackPanel) new CustomFeedbackPanel("feedback").setOutputMarkupId(true);
	
	private ListView<NoticiaBean> listViewNoticia;
	private WebMarkupContainer divListaNoticia;
	private List<NoticiaBean> listaNoticia;
	
	private ICrudController controller = getController();
	
	private AjaxSubmitLink linkFiltro;
	
	/*O formFiltro foi implementado nas duas
	 * classes de listagem pois não dava para coloca-lo
	 * no BasePage pois existe outras classes que herdam de 
	 * BasePage que não necessita de filtros
	 * */
	private Form<?> formFiltro = new Form<>("formFiltro");
	
	public ListaNoticia() {
		super();
		
		listaNoticia = GenericDAO.getInstance().getTodasNoticias();
		add(formFiltro);
		add(feedbackPanel);
		
		add(criarLinkCriarNoticia());
		addOrReplace(criarDivLinkViewNoticia());
		
		formFiltro.add(criarTextFieldPesquisa());
		formFiltro.add(criarBotaoPesquisar());
	}
	
	
	private Link<String> criarLinkCriarNoticia(){
		
		Link<String> link = new Link<String>("criarNoticia") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				NoticiaBean bean = new NoticiaBean();
				bean.setDataNoticia(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				NoticiaEditForm NoticiaEditForm = new NoticiaEditForm(bean, CASO_USO_INCLUIR);
				setResponsePage(NoticiaEditForm);
			}
		};
		
		return link;
	}
	
	private WebMarkupContainer criarDivLinkViewNoticia(){
		
		divListaNoticia = new WebMarkupContainer("divListaNoticia");
		divListaNoticia.setOutputMarkupId(true);
		
		listViewNoticia = new ListView<NoticiaBean>("listaNoticia", listaNoticia) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<NoticiaBean> item) {
				NoticiaBean noticiaBean = item.getModelObject();
				item.add(new Label("titulo", noticiaBean.getTitulo()).setOutputMarkupId(true));
				item.add(new Label("dataNoticia", noticiaBean.getDataNoticia()).setOutputMarkupId(true));
				item.add(criarLinkEditar(noticiaBean));
				item.add(criarLinkExcluir(noticiaBean));
			}
			
		};
		
		listViewNoticia.setOutputMarkupId(true);
		divListaNoticia.addOrReplace(listViewNoticia);
		return divListaNoticia;
	}
	
	@Override
	public Link<String> criarLinkEditar(final AbstractBean<?> table){
		
		Link<String> link = new Link<String>("editarNoticia") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				NoticiaBean NoticiaBean = (NoticiaBean) table;
				NoticiaEditForm NoticiaEditForm = new NoticiaEditForm(NoticiaBean, CASO_USO_EDITAR);
				setResponsePage(NoticiaEditForm);
			}
			
		};
		
		return link;
	}
	
	@Override
	public AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table) {
		AjaxLink<String> link = new AjaxLink<String>("excluirNoticia") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				NoticiaBean NoticiaBean = (NoticiaBean) table;
				NoticiaEditForm NoticiaEditForm = new NoticiaEditForm(NoticiaBean, CASO_USO_EXCLUIR);
				setResponsePage(NoticiaEditForm);
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
	
	@SuppressWarnings("unchecked")
	private TextField<String> criarTextFieldPesquisa(){
		textFieldPesquisa = (TextField<String>) new TextField<String>("filtrarNoticia", new Model<String>()).add(new AjaxEventBehavior("keydown"){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				target.appendJavaScript("filtro();");
			}
			
		});
		textFieldPesquisa.setOutputMarkupId(true);
		return (TextField<String>) textFieldPesquisa;
	}
	
	@Override
	public void filtrar(AjaxRequestTarget target){
		
		String nomePesquisa = (String) textFieldPesquisa.getModelObject();
		
		if(nomePesquisa != null && !nomePesquisa.replaceAll(" ", "").equals("")){
			listaNoticia = controller.search(new NoticiaBean(), "titulo", nomePesquisa);
		}else{
			listaNoticia = controller.listarTudo(new NoticiaBean());
		}
		
		listViewNoticia.setModelObject(listaNoticia);
		target.add(divListaNoticia);
	}
	
	private ICrudController getController(){
		if(controller == null){
			controller = new CrudController();
		}
		return controller;
	}
}
