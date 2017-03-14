package util;

import dao.GenericDAO;

public class CreateDataBase {
	
	public static void createDataBaseSeNecessario(){
		if(GenericDAO.getInstance().conn != null){
			
			if(!GenericDAO.getInstance().verificarSeExisteTabela()){
				
				try {
					GenericDAO.getInstance().criarTabelaLogin();
					GenericDAO.getInstance().criarTabelaJogador();
					GenericDAO.getInstance().criarTabelaNoticia();
					GenericDAO.getInstance().criarTabelaRankingArco();
					GenericDAO.getInstance().criarTabelaRankingFuzil();
					GenericDAO.getInstance().criarTabelaRankingPistola();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				GenericDAO.getInstance().inserirUsuarioAdmin();
			}
			
		}
	}
	
}
