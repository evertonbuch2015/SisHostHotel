package br.com.buch.core.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T extends Serializable>{

	
	public boolean salvar(T entidate);
		
	public void excluir(T entidade);
	
	public T carregarEntidade(T entidade);
	
	public List<T> buscarTodos();
}
