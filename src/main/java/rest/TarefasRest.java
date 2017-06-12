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
<<<<<<< HEAD:src/main/java/rest/TarefasRest.java
public class TarefasRest extends HttpServlet implements Serializable{
=======
public class TarefasRest extends HttpServlet {
>>>>>>> origin/master:src/main/java/rest/TarefasRest.java
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
		
<<<<<<< HEAD:src/main/java/rest/TarefasRest.java
		String tokken =  request.getHeader("tokken");
		String cpf = request.getHeader("cpf");
		
		if(tokken!=null && tokken.equals("appguiaagenda") && cpf != null){
=======
//		String tokken =  request.getHeader("tokken");
		
//		if(tokken!=null && tokken.equals("apphunters")){
>>>>>>> origin/master:src/main/java/rest/TarefasRest.java
			PrintWriter print = response.getWriter();
			Gson gson = new Gson();

			List<Tarefa> tarefas =  controller.searchAll(new Tarefa());
<<<<<<< HEAD:src/main/java/rest/TarefasRest.java
=======
			
>>>>>>> origin/master:src/main/java/rest/TarefasRest.java
			Collections.reverse(tarefas);
			print.print(gson.toJson(tarefas));
			print.flush();
			print.close();
<<<<<<< HEAD:src/main/java/rest/TarefasRest.java
			
			controller.atualizarNotificacoes(cpf);
		}
=======
//		}
>>>>>>> origin/master:src/main/java/rest/TarefasRest.java
	}

}
