package rest;

import intefaces.ICrudController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Tarefa;

import com.google.gson.Gson;

import controller.CrudController;

/**
 * Servlet implementation class RankingPistola
 */
//@WebServlet("/rankingPistola")
public class TarefasRest extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;
	private static ICrudController controller = new CrudController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TarefasRest() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String tokken =  request.getHeader("tokken");
		String cpf = request.getHeader("cpf");
		
		if(tokken!=null && tokken.equals("appguiaagenda") && cpf != null){
			PrintWriter print = response.getWriter();
			Gson gson = new Gson();

			List<Tarefa> tarefas =  controller.searchAll(new Tarefa());
			Collections.reverse(tarefas);
			print.print(gson.toJson(tarefas));
			print.flush();
			print.close();
			
			controller.atualizarNotificacoes(cpf);
		}
	}

}
