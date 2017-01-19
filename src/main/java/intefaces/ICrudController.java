package intefaces;

import java.util.List;

import model.AbstractBean;
import util.Result;

public interface ICrudController {
	
	public Result persist(AbstractBean<?> tabela);
	public Result save(AbstractBean<?> tabela);
	public Result remove(AbstractBean<?> tabela);
	public <T extends AbstractBean<?>> List<T> listarTudo(T tabela);
	public <T extends AbstractBean<?>> List<T> search(T tabela, String nomeColuna, String stringPesquisa);
	public <T extends AbstractBean<?>> T search(T tabela, String nomeColuna, Object valorPesquisa);
	public <T extends AbstractBean<?>> T searchSemAspas(T tabela, String nomeColuna, Object valorPesquisa);
	public AbstractBean<?> getObject(AbstractBean<?> table);
	
}
