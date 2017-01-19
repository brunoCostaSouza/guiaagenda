package model;

import anotations.Atributo;
import anotations.Bean;

@Bean(nome="login")
public class LoginBean extends AbstractBean<Integer>{

	@Atributo(nome="id", pk=true)
	private Integer id;
	
	@Atributo(nome="login")
	private String login;
	
	@Atributo(nome="senha")
	private String senha;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String getNomeTitulo() {
		return null;
	}

	@Override
	public void resetCampos() {
		this.id=null;
		this.login=null;
		this.senha=null;
	}

	@Override
	public String getStringChoice() {
		return null;
	}

	@Override
	public Class<?> getClassList() {
		return null;
	}

	@Override
	public String getNomeTabela() {
		return "login";
	}
}
