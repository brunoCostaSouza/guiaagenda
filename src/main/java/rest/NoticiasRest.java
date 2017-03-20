package rest;

import intefaces.ICrudController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NoticiaBean;

import com.google.gson.Gson;

import controller.CrudController;
import dao.GenericDAO;

/**
 * Servlet implementation class RankingPistola
 */
//@WebServlet("/rankingPistola")
public class NoticiasRest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ICrudController controller = new CrudController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticiasRest() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String tokken =  request.getHeader("tokken");
		
		if(tokken!=null && tokken.equals("apphunters")){
			PrintWriter print = response.getWriter();
			Gson gson = new Gson();

			List<NoticiaBean> noticias =  GenericDAO.getInstance().getTodasNoticias();
			
			Collections.reverse(noticias);
			print.print(gson.toJson(noticias));
			print.flush();
			print.close();
		}
	}

}
