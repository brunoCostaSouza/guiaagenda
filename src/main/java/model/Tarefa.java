package model;

import java.io.Serializable;
import java.util.Date;

import anotations.Atributo;
import anotations.Bean;

<<<<<<< HEAD
import com.agenda.tarefa.ListarTarefas;
=======
import com.agenda.ListarTarefas;
>>>>>>> origin/master

@Bean(nome="tarefa")
public class Tarefa extends AbstractBean<Integer> implements Serializable{
	private static final long serialVersionUID = 1L;

	@Atributo(nome="id", nomeColuna="ID", pk=true)
	private Integer id;
	
	@Atributo(nome="titulo",nomeColuna="TITULO")
	private String titulo;
	
	@Atributo(nome="turma", nomeColuna="ID_TURMA")
	private Turma turma;
	
	@Atributo(nome="disciplina", nomeColuna="ID_DISCIPLINA")
	private Disciplina disciplina;
	
	@Atributo(nome="professor", nomeColuna="ID_PROFESSOR")
	private Professor professor;
	
	@Atributo(nome="descricao", nomeColuna="DESCRICAO")
	private String descricao;
	
	@Atributo(nome="dataCadastro", nomeColuna="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Atributo(nome="dataEntrega", nomeColuna="DATA_ENTREGA")
	private Date dataEntrega;
	
	@Override
	public String getNomeTitulo() {
		return "Tarefa";
	}
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public Turma getTurma() {
		return turma;
	}



	public void setTurma(Turma turma) {
		this.turma = turma;
	}



	public Disciplina getDisciplina() {
		return disciplina;
	}



	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}



	public Professor getProfessor() {
		return professor;
	}



	public void setProfessor(Professor professor) {
		this.professor = professor;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public Date getDataCadastro() {
		return dataCadastro;
	}



	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}


	@Override
	public void resetCampos() {
		this.id = null;
		this.titulo = null;
		this.descricao = null;
		this.professor = null;
		this.descricao = null;
		this.turma = null;
<<<<<<< HEAD
		this.dataCadastro = new Date();
=======
		this.dataCadastro = null;
>>>>>>> origin/master
		this.dataEntrega = null;
	}

	@Override
	public String getStringChoice() {
		return null;
	}

	@Override
	public Class<?> getClassList() {
		return ListarTarefas.class;
	}

}
