package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AbstractBean;
import util.Reflexao;
import util.Result;

public class GenericDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static String SERVIDOR = "localhost";
	private final static String BANCO_DADOS = "agenda";
	private final static String PORTA = "3306";
	private final static String USUARIO = "root";
	private final static String SENHA = "admin";

	public Connection conn = null;

	private static GenericDAO dao = null;

	private GenericDAO() {
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 conn = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PORTA + "/" + BANCO_DADOS, USUARIO, SENHA);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static GenericDAO getInstance() {
		if (dao == null) {
			dao = new GenericDAO();
		}
		return dao;
	}

	public Result persist(AbstractBean<?> tabela) {
		Result r = new Result();
		r.setResult(false);

		ArrayList<String> nomesValores = Reflexao.getValoresColunaTabela(tabela, false);

		String comandoSql = "INSERT INTO " + Reflexao.getNomeTabela(tabela);
		comandoSql += "(" + nomesValores.get(0) + ") ";
		comandoSql += "values(" + nomesValores.get(1) + ")";

		if (executaSql(comandoSql, tabela, false, true, r)) {

			r.setResult(true);
			r.setAcao("execute");
			r.setMsg("Adicionado com Sucesso");

		} else {
			r.setMsg("Falha ao Adicionar " + tabela.getNomeTabela());
		}

		return r;
	}

	public Result save(AbstractBean<?> object) {

		Result r = new Result();
		r.setResult(false);

		String comandoSql = "UPDATE " + object.getNomeTabela() + " SET "
				+ object.getNomeAtributosParaSqlUpdate() + " WHERE "
				+ object.getPKName() + "=" + object.getPK();

		if (executaSql(comandoSql, object, false, true, r)) {

			r.setResult(true);
			r.setAcao("execute");
			r.setMsg("Alterado com Sucesso");

		} else {
			r.setMsg("Falha ao Alterar " + object.getNomeTabela());
		}

		return r;
	}

	public Result remove(AbstractBean<?> object) {

		Result r = new Result();
		r.setResult(false);

		String comandoSql = "DELETE FROM " + object.getNomeTabela() + " WHERE "
				+ object.getPKName() + "=" + object.getPK();

		if (executaSql(comandoSql, object, false, false, r)) {

			r.setResult(true);
			r.setAcao("execute");
			r.setMsg("Excluido com Sucesso");

		} else {
			r.setMsg("Falha ao Excluir " + object.getNomeTabela());
		}

		return r;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractBean<?>> List<T> searchAll(T tabela) {

		List<T> listObjects = null;
		String sql = "SELECT * FROM " + tabela.getNomeTabela();

		try {

			ResultSet rs = executeSql(sql);

			if (rs != null) {
				listObjects  = new ArrayList<T>();
				while (rs.next()) {
					AbstractBean<?> objeto = tabela.getClass().newInstance();

					int qtdColunas = rs.getMetaData().getColumnCount();

					for (int i = 1; i <= qtdColunas; i++) {
						Object valorColuna = rs.getObject(i);
						String nomeColuna = rs.getMetaData().getColumnName(i);
						objeto.setValorAtributo(nomeColuna, valorColuna);
					}

					listObjects.add((T) objeto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listObjects;
	}

	public <T extends AbstractBean<?>> List<T> search(T tabela,
			String nomeColuna, String stringPesquisa) {

		List<T> listObjects = new ArrayList<T>();

		String sql = "SELECT " + tabela.getNomeColunasTabela(true) + " FROM "
				+ tabela.getNomeTabela() + " WHERE " + nomeColuna + " LIKE "
				+ "'%" + stringPesquisa + "%'";

		try {

			ResultSet rs = executeSql(sql);

			if (rs != null) {
				while (rs.next()) {

					@SuppressWarnings("unchecked")
					T objeto = (T) tabela.getClass().newInstance();

					int qtdColunas = rs.getMetaData().getColumnCount();
					String[] attributos = objeto.getNomeColunasTabela(true)
							.split(",");

					for (int i = 0; i < qtdColunas; i++) {
						Object valorColuna = rs.getObject(i + 1);
						String attr = attributos[i];
						Reflexao.setValorAtributo(objeto, attr, valorColuna);
					}

					listObjects.add(objeto);
				}
			}

			return listObjects;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listObjects;
	}

	public <T extends AbstractBean<?>> T search(T tabela, String nomeColuna,
			Object valorPesquisa) {

		String valores = "";

		String sql = "SELECT " + tabela.getNomeColunasTabela(true) + " FROM "
				+ tabela.getNomeTabela() + " WHERE " + nomeColuna + " = '"
				+ valorPesquisa + "'";

		try {

			ResultSet rs = executeSql(sql);

			if (rs.next()) {

				@SuppressWarnings("unchecked")
				T objeto = (T) tabela.getClass().newInstance();

				int qtdColunas = rs.getMetaData().getColumnCount();

				for (int i = 1; i <= qtdColunas; i++) {
					Object valorColuna = rs.getObject(i);
					valores += "," + valorColuna;
				}

				if (valores != null && valores.length() > 0) {
					valores = valores.substring(1, valores.length());
				}

				return objeto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public <T extends AbstractBean<?>> T searchSemAspas(T tabela,
			String nomeColuna, Object valorPesquisa) {

		String sql = "SELECT " + tabela.getNomeColunasTabela(true) + " FROM "
				+ tabela.getNomeTabela() + " WHERE " + nomeColuna + " = "
				+ valorPesquisa;

		try {

			ResultSet rs = executeSql(sql);

			if (rs.next()) {

				@SuppressWarnings("unchecked")
				T objeto = (T) tabela.getClass().newInstance();

				int qtdColunas = rs.getMetaData().getColumnCount();

				for (int i = 1; i <= qtdColunas; i++) {
					String columnName = rs.getMetaData().getColumnName(i)
							.toLowerCase();
					Object valorColuna = rs.getObject(i);
					objeto.setValorAtributo(columnName, valorColuna);
				}

				return objeto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractBean<?>> T getObjeto(T tabela) {

		String comandoSql = "SELECT " + tabela.getNomeColunasTabela(true)
				+ " FROM " + tabela.getNomeTabela() + " WHERE "
				+ tabela.getPKName() + " = " + "'" + tabela.getPK().toString()
				+ "'";

		try {

			ResultSet rs = executeSql(comandoSql);

			if (rs != null) {

				int qtdeColunas = rs.getMetaData().getColumnCount();

				if (rs.next()) {
					for (int i = 1; i <= qtdeColunas; i++) {
						Object val = rs.getObject(i) != null ? rs.getObject(i)
								: "";
						tabela.setValorAtributo(
								rs.getMetaData().getColumnName(i), val);
					}
				}

				return tabela;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private boolean executaSql(String sql, AbstractBean<?> tabela,
			boolean incluirPk, boolean completarStetement, Result r) {
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			if (completarStetement) {
				Reflexao.completeStatement(statement, tabela, incluirPk);
			}
			if (statement.executeUpdate() == 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setMsg(e.getMessage());
		}
		return false;
	}

	private ResultSet executeSql(String sql) {

		ResultSet resultSet;
		PreparedStatement st;

		try {
			st = conn.prepareStatement(sql);
			resultSet = st.executeQuery();
			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
