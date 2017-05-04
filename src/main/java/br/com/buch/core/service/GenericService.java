package br.com.buch.core.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T extends Serializable>{

	
	public boolean salvar(T entidate);
		
	public void excluir(T entidade);
	
	public T carregarEntidade(Integer id);
	
	public List<T> buscarTodos();
}
