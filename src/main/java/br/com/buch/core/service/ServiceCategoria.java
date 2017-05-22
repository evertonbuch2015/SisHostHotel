package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.CategoriaDao;
import br.com.buch.core.entity.Categoria;
import br.com.buch.view.managedBean.CategoriaBean.TipoFiltro;
import br.com.buch.view.util.UtilMensagens;

public class ServiceCategoria implements GenericService<Categoria> {

	
	private CategoriaDao categoriaDao; 
	
	
	public ServiceCategoria() {
		this.categoriaDao = new CategoriaDao();
	}
	
	@Override
	public boolean salvar(Categoria entidate) {

		return false;
	}

	@Override
	public void excluir(Categoria entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Categoria carregarEntidade(Categoria entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Categoria> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public List<Categoria> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Categoria> lista = null;
		
		try {
			
			 if(tipoFiltro.equals(TipoFiltro.NOME)){
				lista = categoriaDao.find("Select c From Categoria c where c.nome like ?",valorFiltro);
			}								
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Ocorreu algum excessão ao Filtrar os dados da Categoria!");
			return null;
		}					
	}
}
