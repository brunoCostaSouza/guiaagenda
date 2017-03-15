package rest;

import intefaces.ICrudController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.JogadorBean;

import com.google.gson.Gson;

import controller.CrudController;
import dao.GenericDAO;

/**
 * Servlet implementation class RankingPistola
 */
//@WebServlet("/rankingPistola")
public class JogadoresRest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ICrudController controller = new CrudController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JogadoresRest() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String tokken =  request.getHeader("tokken");
		
		if(tokken != null && tokken.equals("apphunters")){
			PrintWriter print = response.getWriter();
			Gson gson = new Gson();
			
//			List<JogadorBean> jogadores =  controller.listarTudo(new JogadorBean());
			List<JogadorBean> jogadores =  GenericDAO.getInstance().getTodosJogadores();
			
			print.print(gson.toJson(jogadores));
			print.flush();
			print.close();
		}
	}

}
