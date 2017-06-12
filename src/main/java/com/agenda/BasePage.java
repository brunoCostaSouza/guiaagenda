package com.agenda;

import java.io.Serializable;

import model.AbstractBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;

import com.agenda.tarefa.ListarTarefas;


public abstract class BasePage extends WebPage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int CASO_USO_INCLUIR 	= 1;
	public static final int CASO_USO_EDITAR 	= 2;
	public static final int CASO_USO_EXCLUIR 	= 3;
	public static final int CASO_USO_Listar 	= 4;
	
	public AjaxSubmitLink linkFiltro;
	public TextField<?> textFieldPesquisa;
	private ModalWindow modalForm;
	
	public abstract AjaxLink<String> criarLinkExcluir(final AbstractBean<?> table);
	public abstract Link<String> criarLinkEditar(final AbstractBean<?> table);
	public abstract void filtrar(AjaxRequestTarget target);
	
	public BasePage() {
		
//		String logado = (String) getSession().getAttribute("logado");
		
//		if(logado == null || logado.equals("false")){
//			setResponsePage(Login.class);
//			return;
//		}
		
		/**
		 * Serão adicionados os Links para as listagens
		 */
		add(criarLinkTarefa());
//		add(criarModalPadrao());
	}
	
	private AjaxLink<Void> criarLinkTarefa(){
		AjaxLink<Void> linkTarefa = new AjaxLink<Void>("linkTarefas") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ListarTarefas.class);
			}
		};
		linkTarefa.setOutputMarkupId(true);
		return linkTarefa;
	}
	
//	private ModalWindow criarModalPadrao(){
//		modalForm = new ModalWindow("modalPadrao");
//		modalForm.setOutputMarkupId(true);
//		modalForm.setResizable(true);
//		return modalForm;
//	}
//	
//	public ModalWindow getModalPadrao(){
//		return modalForm;
//	}
	
	
}