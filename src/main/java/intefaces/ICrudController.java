package intefaces;

import java.util.List;

import model.AbstractBean;
import util.Result;

public interface ICrudController {
	
	public Result persist(AbstractBean<?> tabela);
	public Result save(AbstractBean<?> tabela);
	public Result remove(AbstractBean<?> tabela);
	public <T extends AbstractBean<?>> List<T> search(T tabela, String nomeColuna, String stringPesquisa);
	public <T extends AbstractBean<?>> List<T> searchAll(T tabela);
	public <T extends AbstractBean<?>> T search(T tabela, String nomeColuna, Object valorPesquisa);
	public <T extends AbstractBean<?>> T searchSemAspas(T tabela, String nomeColuna, Object valorPesquisa);
	public <T extends AbstractBean<?>> T search(T tabela,String[] colunas, String[] valores);
	public AbstractBean<?> getObject(AbstractBean<?> table);
	public Long notifications(String cpf);
	public void atualizarNotificacoes(String cpf);
}
