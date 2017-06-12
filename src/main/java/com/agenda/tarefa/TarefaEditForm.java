package com.agenda.tarefa;

import java.io.Serializable;

import model.AbstractBean;
import model.Disciplina;
import model.Professor;
import model.Turma;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;

import util.DataJQueryAttributeModifier;

import com.agenda.EditForm;

public class TarefaEditForm extends EditForm implements Serializable{
	private static final long serialVersionUID = 1L;

	public TarefaEditForm(AbstractBean<?> bean, int userCase) {
		super(bean, userCase);
		adicionarCampos();
	}

	@Override
	public void adicionarCampos() {
		getForm().add(criarCampoTitulo());
		getForm().add(criarCampoDescricao());
//		getForm().add(criarCampoDataEntrega());
		getForm().add(criarDropDown("turma", new Turma()));
		getForm().add(criarDropDown("disciplina", new Disciplina()));
		getForm().add(criarDropDown("professor", new Professor()));
	}
	
	private TextField<String> criarCampoTitulo(){
		TextField<String> text = new TextField<>("titulo");
		text.setOutputMarkupId(true);
		return text;
	}
	
	private TextArea<String> criarCampoDescricao(){
		TextArea<String> text = new TextArea<>("descricao");
		text.setOutputMarkupId(true);
		return text;
	}
	
	
	@Override
	public CheckBox criarCheckBox(String id) { return null; }

	@Override
	public void afterDeleteItem() {}

	@Override
	public void beforeDeleteItem() {
	}

	@Override
	public void beforePersistOrSave(AjaxRequestTarget target) {
		
	}

	@Override
	public AjaxLink<String> criarLinkExcluir(AbstractBean<?> table) {return null;}

	@Override
	public Link<String> criarLinkEditar(AbstractBean<?> table) {return null;}

	@Override
	public void filtrar(AjaxRequestTarget target) {}

}
