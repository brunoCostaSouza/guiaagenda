package util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.AbstractBean;
import anotations.Atributo;
import anotations.Bean;
import dao.GenericDAO;

public class Reflexao implements Serializable{
	private static final long serialVersionUID = 1L;

	public static String getPKNome(AbstractBean<?> object){
		
		Field[] atributos = getAtributosClass(object.getClass());
		
		for(Field attr : atributos){
			Atributo anot = attr.getAnnotation(Atributo.class);
			
			if(anot != null && anot.pk()){
				return anot.nome();
			}
		}
		
		return null;
	}
	
	public static Object getPkValor(AbstractBean<?> object){
		String nomePk = object.getPKName();
		Object valor = getValorAtributo(object, nomePk);
		if(valor == null)throw new RuntimeException("A classe:"+object.getClass().getSimpleName()+" não foi anotada para obter as informações da PK");
		return valor;
	}
	
	public static void setPk(AbstractBean<?> object, Object pk){
		try {
			
			Field[] atributos = getAtributosClass(object.getClass());
			String nomePk = getPKNome(object);
			
			for(Field attr : atributos){
				Atributo anot = attr.getAnnotation(Atributo.class);
				if(anot != null && anot.pk() && anot.nome().equals(nomePk)){
					String nomeMetodo = "set"+nomePk.substring(0, 1)+nomePk.substring(1);
					Method metodo = getMetodoClass(object.getClass(), AbstractBean.class, nomeMetodo, attr.getType());
					metodo.invoke(object, pk);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getNomeTabela(AbstractBean<?> object){
		try {
			Class<?> metaClass = object.getClass();
			
			Bean anot = metaClass.getAnnotation(Bean.class);
			
			if(anot != null){
				return anot.nome();
			}else{
				throw new RuntimeException("A classe: "+metaClass.getSimpleName()+" não foi anotada com a anotação 'Bean'");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getNomeColunasTabelas(AbstractBean<?> object, boolean incluirPk){
		Field[] atributos = getAtributosClass(object.getClass());
		
		String nomeColunas = "";
		
		for(Field attr : atributos){
			Atributo anot = attr.getAnnotation(Atributo.class);
			if(anot != null){
				if(incluirPk){
					nomeColunas += ","+anot.nome();
				}else if(!incluirPk && !anot.pk()){
					nomeColunas += ","+anot.nome();
				}
			}
		}
		
		if(nomeColunas != null && nomeColunas.length() > 0){
			return nomeColunas.substring(1, nomeColunas.length());
		}else{
			throw new RuntimeException("Nenhum atributo da classe:"+object.getClass().getSimpleName()+" foi anotado");
		}
		
	}
	
	public static ArrayList<String> getValoresColunaTabela(AbstractBean<?> object, boolean incluirPk){
		String columns = "";
		String values = "";
		Class<?> mTClass = object.getClass();
		ArrayList<String> retorno = new ArrayList<String>();
		
		Field[] fields = mTClass.getDeclaredFields();
		
		for(int i = 0; i < fields.length; i++){
			Atributo attr = fields[i].getAnnotation(Atributo.class);
			if(attr != null){
				if(incluirPk){
					columns += "," + attr.nome();
					values += "," + "?";
				}else if(!incluirPk && !attr.pk()){
					columns += "," + attr.nome();
					values += "," + "?";
				}
			}
		}
		
		retorno.add(columns.substring(1));
		retorno.add(values.substring(1));
		
		return retorno;
	}
	
	public static PreparedStatement completeStatement(PreparedStatement stmt, AbstractBean<?> object, boolean incluirPk) throws Exception{
		Class<?> metaClass = object.getClass();
		Field[] fields = metaClass.getDeclaredFields();
		int fieldN = 1;
		
		for(int i =0; i < fields.length; i++){
			Atributo field = fields[i].getAnnotation(Atributo.class);
			
			if(field != null){
				System.out.println(fieldN);
				if(incluirPk){
					Object value = getValorAtributo(object, fields[i].getName());
					
					if(value instanceof  AbstractBean<?>){
						stmt.setObject(fieldN, (( AbstractBean<?>)value).getPK());
						fieldN++;
					}else{
						stmt.setObject(fieldN, value);
						fieldN++;
					}
				}else if(!incluirPk && !field.pk()){
					Object value = getValorAtributo(object, fields[i].getName());
					
					if(value instanceof  AbstractBean<?>){
						stmt.setObject(fieldN, (( AbstractBean<?>)value).getPK());
						fieldN++;
					}else{
						stmt.setObject(fieldN, value);
						fieldN++;
					}
				}
			}
		}
		return stmt;
		
	}
	
	public static String getNomeAtributosParaSqlUpdate(AbstractBean<?> object){
		
		Class<?> metaClass = object.getClass();
		Field[] fields = metaClass.getDeclaredFields();
		String string = "";
		
		for(int i =0; i<fields.length; i++){
			Atributo column = fields[i].getAnnotation(Atributo.class);
			
			if(column != null){
				if(column.nome() != null && !column.pk()){
					string += "," + column.nome() + "=" + "?";
				}
			}
		}
		
		return string.substring(1);
	}
	
	public static Object getValorAtributo(AbstractBean<?> object, String nomeAtributo){
		
		try {
			
			Class<?> metaClass = object.getClass();
			Field[] atributos = metaClass.getDeclaredFields();
			
			for(Field attr : atributos){
				
				Atributo anot = attr.getAnnotation(Atributo.class);
				if(anot != null && attr.getName().equalsIgnoreCase(nomeAtributo)){
					
					String nomeMetodo = "get"+nomeAtributo.substring(0, 1).toUpperCase() + nomeAtributo.substring(1);
					Method metodo = getMetodoClass(metaClass, AbstractBean.class, nomeMetodo, null);
					Object valor = metodo.invoke(object, null);
					return valor;
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void setAtributoMetodo(AbstractBean<?> object, String nomeAtributo, Object valor ){
		
		try {
			
			Class<?> metaClass = object.getClass();
			Field[] atributos = metaClass.getDeclaredFields();
			
			for(Field attr : atributos){
				
				Atributo anot = attr.getAnnotation(Atributo.class);
				if(anot != null && attr.getName().equalsIgnoreCase(nomeAtributo)){
					
					String nomeMetodo = "set"+nomeAtributo.substring(0, 1).toUpperCase() + nomeAtributo.substring(1);
					Method metodo = getMetodoClass(metaClass, AbstractBean.class, nomeMetodo, attr.getType());
					metodo.invoke(object, valor);
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void setValorAtributo(AbstractBean<?> object, String nomeAtributo, Object valor){
		
		Class<?> metaClass = object.getClass();
		Field[] atributos = getAtributosClass(metaClass);
		
		for(Field attr : atributos){
			Atributo anot = attr.getAnnotation(Atributo.class);
			if(anot!=null && anot.nome().equals(nomeAtributo)){
				
				if(AbstractBean.class.isAssignableFrom(attr.getType())){
					try {
						Object fkClass = attr.getType().newInstance();
						
						@SuppressWarnings("rawtypes")
						AbstractBean modelFk = (AbstractBean) fkClass;
						
						modelFk.setPK(valor);
						modelFk = GenericDAO.getInstance().getObjeto(modelFk);
						setAtributoMetodo(object, nomeAtributo, modelFk);
					} catch (Exception e) {
						throw new RuntimeException("<SETOBJECTBYTABLENAME>FAil to instance a class");
					}
				}else{
					try {
						Class<?> converter = Class.forName(attr.getType().getName());
						setAtributoMetodo(object, nomeAtributo, converter.cast(valor));
						break;
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("Não foi encontrado a class:"+attr.getType().getName());
					}
				}
			}
		}
	}
	
	public static Method getMetodoClass(Class<?> metaClass, Class<?> superClass, String nomeMetodo, Class<?>... tiposParametros){
		try {
			return metaClass.getDeclaredMethod(nomeMetodo, tiposParametros);
		} catch (NoSuchMethodException e) {
			if (superClass.isAssignableFrom(metaClass.getSuperclass())) {
				return getMetodoClass(metaClass.getSuperclass(), superClass, nomeMetodo, tiposParametros);
			} else {
				throw new RuntimeException("Não foi possível encontrar o método: "+nomeMetodo, e);
			}
		}
	}
	
	private static Field[] getAtributosClass(Class<?> metaClass){
		return metaClass.getDeclaredFields();
	}
	
	
}
