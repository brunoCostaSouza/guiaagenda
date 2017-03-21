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
import model.JogadorBean;
import model.JogadorRankingArcoBean;
import model.JogadorRankingFuzilBean;
import model.JogadorRankingPistolaBean;
import model.LoginBean;
import model.NoticiaBean;
import util.Reflexao;
import util.Result;

public class GenericDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static String SERVIDOR = "localhost";
	private final static String BANCO_DADOS = "huntersdb";
	private final static String PORTA = "3306";
	private final static String USUARIO = "root";
	private final static String SENHA = "admin";

	public Connection conn = null;

	private static GenericDAO dao = null;

	public String url;
	public String user;
	public String sen;

	private GenericDAO() {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			// conn = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PORTA + "/" + BANCO_DADOS, USUARIO, SENHA);

			Class.forName("org.postgresql.Driver");

			url = System.getenv("JDBC_DATABASE_URL");
			conn = DriverManager.getConnection(url);
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
	public <T extends AbstractBean<?>> List<T> listarTudo(T tabela) {

		if(tabela.getNomeTabela().equals("jogador")){
			return (List<T>) getTodosJogadores();
		}
		
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
						objeto.setValorAtributo(nomeColuna.substring(0, 1).toLowerCase() + nomeColuna.substring(1), valorColuna);
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

		if(tabela.getClass().equals(JogadorBean.class)){
			return (T) getJogadorPorId(Integer.parseInt(tabela.getPK().toString()));
		}
		
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
	
	
	
	
	
	
	

	public Boolean verificarSeExisteTabela(Result r) {

		String sql = "SELECT * from login";

		try {
			ResultSet rset = conn.prepareStatement(sql).executeQuery();
			int i = 0;
			while(rset.next()){
				i++;
			}
			r.setMsg("qtdUsers:"+i);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			r.setMsg(e.getMessage());
		}
		return false;
	}
	
	public void criarTabelaJogador() throws SQLException{
		String sql = "CREATE TABLE jogador ("
				+ "Id bigserial primary key,"
				+ "nome varchar(255) DEFAULT NULL,"
				+ "cpf varchar(255) DEFAULT NULL);";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ps.close();
		}
	}
	
	public void criarTabelaLogin(Result r) throws SQLException{
		String sql = "CREATE TABLE login ("
				+ "Id bigserial primary key,"
				+ "login varchar(255) DEFAULT NULL,"
				+ "senha varchar(255) DEFAULT NULL);";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
			r.setMsg("Tabela Login criada com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			r.setMsg(e.getMessage());
		}finally{
			ps.close();
		}
	}
	
	public void criarTabelaNoticia() throws SQLException{
		String sql = "CREATE TABLE noticia ("
				+ "Id bigserial primary key,"
				+ "titulo varchar(255) DEFAULT NULL,"
				+ "conteudo text,"
				+ "dataNoticia varchar(10) DEFAULT NULL);";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ps.close();
		}
	}
	
	public void criarTabelaRankingArco() throws SQLException{
		String sql = "CREATE TABLE rankingarcoflecha ("
				+ "Id bigserial primary key,"
				+ "nome varchar(255) DEFAULT NULL,"
				+ "pontos double precision DEFAULT NULL,"
				+ "posicao integer DEFAULT NULL,"
				+ "jogador integer DEFAULT NULL);";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ps.close();
		}
	}
	
	public void criarTabelaRankingPistola()throws SQLException{
		String sql = "CREATE TABLE rankingpistola ("
				+ "Id bigserial primary key,"
				+ "nome varchar(255) DEFAULT NULL,"
				+ "pontos double precision DEFAULT NULL,"
				+ "posicao integer DEFAULT NULL,"
				+ "jogador integer DEFAULT NULL);";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ps.close();
		}
	}
	
	public void criarTabelaRankingFuzil()throws SQLException{
		String sql = "CREATE TABLE rankingfuzil ("
				+ "Id bigserial primary key,"
				+ "nome varchar(255) DEFAULT NULL,"
				+ "pontos double precision DEFAULT NULL,"
				+ "posicao integer DEFAULT NULL,"
				+ "jogador integer DEFAULT NULL);";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ps.close();
		}
	}
	
	public Result inserirUsuarioAdmin(){
		if(listarTudo(new LoginBean())!=null && listarTudo(new LoginBean()).size()==0){
			LoginBean usuario = new LoginBean();
			usuario.setLogin("bradmin");
			usuario.setSenha("123456");
			return persist(usuario);
		}
		return null;
	}
	
	/*
	 * LOGIN
	 * */
	public List<LoginBean> getTodosUsuario() {
		
		String sql = "SELECT * FROM login";
		
		PreparedStatement ps = null;
		List<LoginBean> list = new ArrayList<LoginBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				LoginBean bean = new LoginBean();
				bean.setId(rs.getInt("Id"));
				bean.setSenha(rs.getString("senha"));
				bean.setLogin(rs.getString("login"));
				list.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	public List<JogadorBean> getTodosJogadores() {
		
		String sql = "SELECT * FROM jogador";
		
		PreparedStatement ps = null;
		List<JogadorBean> list = new ArrayList<JogadorBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				JogadorBean bean = new JogadorBean();
				bean.setCpf(rs.getString("cpf"));
				bean.setNome(rs.getString("nome"));
				bean.setId(rs.getInt("Id"));
				list.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	public JogadorBean getJogadorPorId(Integer id) {
		
		String sql = "SELECT * FROM jogador WHERE Id = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				JogadorBean bean = new JogadorBean();
				bean.setCpf(rs.getString("cpf"));
				bean.setNome(rs.getString("nome"));
				bean.setId(rs.getInt("Id"));
				return bean;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	/*
	 * NOTICIA
	 * 
	 * */
	public List<NoticiaBean> getTodasNoticias() {
		
		String sql = "SELECT * FROM noticia";
		
		PreparedStatement ps = null;
		List<NoticiaBean> list = new ArrayList<NoticiaBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				NoticiaBean bean = new NoticiaBean();
				bean.setId(rs.getInt("Id"));
				bean.setConteudo(rs.getString("conteudo"));
				bean.setDataNoticia(rs.getString("dataNoticia"));
				bean.setTitulo(rs.getString("titulo"));
				list.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	/*
	 * RANKING PISTOLA
	 * */
	public List<JogadorRankingPistolaBean> getRankingPistola() {
		
		String sql = "SELECT * FROM rankingpistola";
		
		PreparedStatement ps = null;
		List<JogadorRankingPistolaBean> list = new ArrayList<JogadorRankingPistolaBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				JogadorRankingPistolaBean bean = new JogadorRankingPistolaBean();
				bean.setId(rs.getInt("Id"));
				bean.setJogador(getJogadorPorId(rs.getInt("jogador")));
				bean.setNome(rs.getString("nome"));
				bean.setPontos(rs.getDouble("pontos"));
				bean.setPosicao(rs.getInt("posicao"));
				list.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	public List<JogadorRankingFuzilBean> getRankingFuzil() {
		
		String sql = "SELECT * FROM rankingfuzil";
		
		PreparedStatement ps = null;
		List<JogadorRankingFuzilBean> list = new ArrayList<JogadorRankingFuzilBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				JogadorRankingFuzilBean bean = new JogadorRankingFuzilBean();
				bean.setId(rs.getInt("Id"));
				bean.setJogador(getJogadorPorId(rs.getInt("jogador")));
				bean.setNome(rs.getString("nome"));
				bean.setPontos(rs.getDouble("pontos"));
				bean.setPosicao(rs.getInt("posicao"));
				list.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}

	public List<JogadorRankingArcoBean> getRankingArcoFlecha() {
	
	String sql = "SELECT * FROM rankingarcoflecha";
	
	PreparedStatement ps = null;
	List<JogadorRankingArcoBean> list = new ArrayList<JogadorRankingArcoBean>();
	
	try {
		ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			JogadorRankingArcoBean bean = new JogadorRankingArcoBean();
			bean.setId(rs.getInt("Id"));
			bean.setJogador(getJogadorPorId(rs.getInt("jogador")));
			bean.setNome(rs.getString("nome"));
			bean.setPontos(rs.getDouble("pontos"));
			bean.setPosicao(rs.getInt("posicao"));
			list.add(bean);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {
			ps.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	return list;
}
	
}
