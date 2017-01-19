package model;

import java.util.ArrayList;

import util.Reflexao;


public abstract class AbstractBean<T> {

	public String getPKName(){
		return Reflexao.getPKNome(this);
	}
	
	@SuppressWarnings("unchecked")
	public T getPK(){
		return (T) Reflexao.getPkValor(this);
	}
	
	public void setPK(T pk){
		Reflexao.setPk(this, pk);
	}
	
	public String getNomeTabela(){
		return Reflexao.getNomeTabela(this);
	}
	
	public String getNomeColunasTabela(boolean incluirPk) {
		return Reflexao.getNomeColunasTabelas(this, incluirPk);
	}
	
	public Object getValorAtributo(String nomeAtributo){
		return Reflexao.getValorAtributo(this, nomeAtributo);
	}
	
	public void setValorAtributo(String nomeAtributo, Object valor){
		Reflexao.setValorAtributo(this, nomeAtributo, valor);
	}
	
	public ArrayList<String> getValoresColunaTabela(boolean incluirPk){
		return Reflexao.getValoresColunaTabela(this, incluirPk);
	}
	
	public String getNomeAtributosParaSqlUpdate(){
		return Reflexao.getNomeAtributosParaSqlUpdate(this);
	}
	
	public abstract String getNomeTitulo();
	public abstract void resetCampos();
	public abstract String getStringChoice();
	public abstract Class<?> getClassList();
	

}
