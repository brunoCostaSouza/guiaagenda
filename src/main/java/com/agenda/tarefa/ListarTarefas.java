package com.agenda.tarefa;

import intefaces.ICrudController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import model.AbstractBean;
import model.Tarefa;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.agenda.BasePage;

import controller.CrudController;

public class ListarTarefas extends BasePage  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private WebMarkupContainer divListaTarefa;
	private List<Tarefa> list = new ArrayList<Tarefa>();
	private ICrudController controller = getController();
	private TarefaEditPanel editPanel;
	
	public ListarTarefas() {
		list = controller.searchAll(new Tarefa());
		Collections.reverse(list);
		add(criarDivListaTarefa());
		add(criarLinkNovo());
//		add(criarEditPanel());
	}
	
	private WebMarkupContainer criarDivListaTarefa(){
		divListaTarefa = new WebMarkupContainer("divListaTarefa");
		divListaTarefa.setOutputMarkupId(true);
		
		ListView<Tarefa> listView = new ListView<Tarefa>("listaTarefa", list) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Tarefa> item) {
				Tarefa tarefa = item.getModelObject();
				
				item.add(new Label("titulo", tarefa.getTitulo()));
				item.add(new Label("turma", tarefa.getTurma().getDescricao()).setOutputMarkupId(true));
				item.add(new Label("disciplina", tarefa.getDisciplina().getDescricao()).setOutputMarkupId(true));
				item.add(new Label("professor", tarefa.getProfessor().getNome()).setOutputMarkupId(true));
				item.add(criarLinkEditar(tarefa));
				item.add(criarLinkExcluir(tarefa));
			}
		};
		listView.setOutputMarkupId(true);
		divListaTarefa.add(listView);
		return divListaTarefa;
	}
	
	private AjaxLink<Void> criarLinkNovo(){
		AjaxLink<Void> linkNovo = new AjaxLink<Void>("linkNovo") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				
//				if(!getModalPadrao().isShown()){
//					TarefaEditForm tarefaEditForm = new TarefaEditForm(new Tarefa(), CASO_USO_INCLUIR){
//						private static final long serialVersionUID = 1L;
//
//						@Override
//						public void beforePersistOrSave(AjaxRequestTarget target) {
//							super.beforePersistOrSave(target);
//						}
//					};
//					
//					editPanel.addOrReplace(tarefaEditForm);
//					getModalPadrao().show(target);
//					
//				}
				Tarefa tarefa = new Tarefa();
				tarefa.setDataCadastro(new Date());
				TarefaEditForm tarefaEditForm = new TarefaEditForm(tarefa, CASO_USO_INCLUIR);
				setResponsePage(tarefaEditForm);
			}
		};
		linkNovo.setOutputMarkupId(true);
		return linkNovo;
	}
	
	@Override
	public AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table) {
		AjaxLink<String> linkNovo = new AjaxLink<String>("linkExcluir") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Tarefa tarefa = (Tarefa) table;
				TarefaEditForm tarefaEditForm = new TarefaEditForm(tarefa, CASO_USO_EXCLUIR);
				setResponsePage(tarefaEditForm);
			}
		};
		linkNovo.setOutputMarkupId(true);
		return linkNovo;
	}

	@Override
	public Link<String> criarLinkEditar(final AbstractBean<?> table) {
		Link<String> linkNovo = new Link<String>("linkEditar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				Tarefa tarefa = (Tarefa) table;
				TarefaEditForm tarefaEditForm = new TarefaEditForm(tarefa, CASO_USO_EDITAR);
				setResponsePage(tarefaEditForm);
			}
		};
		linkNovo.setOutputMarkupId(true);
		return linkNovo;
	}

	@Override
	public void filtrar(AjaxRequestTarget target) {
		
	}
	
	private ICrudController getController(){
		if(controller == null){
			controller = new CrudController();
		}
		return controller;
	}

//	private TarefaEditPanel criarEditPanel(){
//		editPanel = new TarefaEditPanel(getModalPadrao().getContentId());
//		getModalPadrao().setCloseButtonCallback(new ResfreshCloseButtonCallback() {
//			private static final long serialVersionUID = 1L;
//
//			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
//				super.onCloseButtonClicked(target);
//				getModalPadrao().setContent(editPanel);
//				target.appendJavaScript("$('body').css('overflow', 'scroll');");
//				return true;
//			}
//		});
//		getModalPadrao().setMinimalHeight(500);
//		getModalPadrao().setMinimalWidth(500);
//		editPanel.setOutputMarkupId(true);
//		add(editPanel);
//		getModalPadrao().setContent(editPanel);
//		return editPanel;
//	}
}
