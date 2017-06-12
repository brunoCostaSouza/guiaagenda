package rest;

import intefaces.ICrudController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Responsavel;

import com.google.gson.Gson;

import controller.CrudController;

/**
 * Servlet implementation class RankingPistola
 */
//@WebServlet("/rankingPistola")
public class ResponsaveisRest extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;
	private static ICrudController controller = new CrudController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponsaveisRest() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String tokken =  request.getHeader("tokken");
		
		if(tokken!=null && tokken.equals("appguiaagenda")){
			
			String cpf = request.getHeader("cpf");
			String senha = request.getHeader("senha");
			
			PrintWriter print = response.getWriter();
			Gson gson = new Gson();
			
			if(cpf != null && senha != null){
				Responsavel resp =  controller.search(new Responsavel(), new String[]{"CPF","SENHA"}, new String[]{cpf, senha});
				if(resp != null){
					print.print(gson.toJson(resp));
				}
			}
			
			print.flush();
			print.close();
		}
	}

}
