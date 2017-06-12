package rest;

import intefaces.ICrudController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Notificacao;

import com.google.gson.Gson;

import controller.CrudController;

/**
 * Servlet implementation class RankingPistola
 */
//@WebServlet("/rankingPistola")
public class NotificacoesRest extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;
	private static ICrudController controller = new CrudController();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificacoesRest() {
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
			PrintWriter print = response.getWriter();
			if(cpf!=null && !cpf.trim().equals("")){
				Long qtdNotificacoes = controller.notifications(cpf);
				
				if(qtdNotificacoes!=null && qtdNotificacoes > 0){
					Notificacao notificacao = new Notificacao();
					if(qtdNotificacoes > 1){
						notificacao.setTitulo("Novas Tarefas");
						notificacao.setMensagem("Existe "+qtdNotificacoes+" novas tarefas");
					}else{
						notificacao.setTitulo("Nova Tarefa");
						notificacao.setMensagem("Existe "+qtdNotificacoes+" nova tarefa");
					}
					Gson gson = new Gson();
					print.write(gson.toJson(notificacao));
					controller.atualizarNotificacoes(cpf);
				}
			}
			
			print.flush();
			print.close();
		}
	}

}
