package com.hunters;

import intefaces.ICrudController;

import java.util.List;

import model.LoginBean;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import util.CreateDataBase;
import util.CustomFeedbackPanel;
import util.Result;
import controller.CrudController;
import dao.GenericDAO;

public class Login extends WebPage{
	private static final long serialVersionUID = 1L;
	private TextField<String> textLogin;
	private PasswordTextField textSenha;
	private FeedbackPanel feedbackPanel;
	private Form<String> form;
	private Result resultteste = new Result();
	private ICrudController controller = new CrudController();
	
	public Login() {
		super();
		CreateDataBase.createDataBaseSeNecessario(resultteste);
		
		add(criarForm());
		form.add(criarTextFieldLogin());
		form.add(criarTextFieldSenha());
		form.add(criarBotaoEntrar());
		form.add(criarFeedbackPanel());
	}
	
	private Form<String> criarForm(){
		form = new Form<String>("form");
		form.setOutputMarkupId(true);
		return form;
	}
	
	private TextField<String> criarTextFieldLogin(){
		textLogin = new TextField<String>("login", new Model<String>());
		textLogin.setOutputMarkupId(true);
		textLogin.setRequired(true);
		return textLogin;
	}
	
	private PasswordTextField criarTextFieldSenha(){
		textSenha = new PasswordTextField("senha", new Model<String>());
		textSenha.setOutputMarkupId(true);
		textSenha.setRequired(true);
		return textSenha;
	}
	
	private FeedbackPanel criarFeedbackPanel(){
		feedbackPanel = new CustomFeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		return feedbackPanel;
	}
	
	private AjaxSubmitLink criarBotaoEntrar(){
		AjaxSubmitLink link = new AjaxSubmitLink("entrar") {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				super.onSubmit(target);
				
				String login = textLogin.getModelObject();
				String senha = textSenha.getModelObject();
				
				List<LoginBean> listLogin = GenericDAO.getInstance().getTodosUsuario();
				boolean logou = false;
				
				if(listLogin!=null && listLogin.size() > 0){
					for(LoginBean lb : listLogin){
						if((login != null && senha != null) && (!login.equals("") && !senha.equals("")) && (login.equals(lb.getLogin()) && senha.equals(lb.getSenha()))){
							getSession().setAttribute("logado", "true");
							setResponsePage(Index.class);
							logou = true;
						}
					}
					
					if(!logou){
						error("Login ou senha estão incorretos");
						target.add(feedbackPanel);
					}
					
				}else{
					error("Nenhum usuário cadastrado no banco de dados");
					target.add(feedbackPanel);
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target) {
				target.add(feedbackPanel);
			}
		};
		
		link.setOutputMarkupId(true);
		return link;
	}
}
