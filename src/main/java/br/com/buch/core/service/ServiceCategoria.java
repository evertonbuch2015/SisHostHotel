package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.CategoriaDao;
import br.com.buch.core.entity.Categoria;
import br.com.buch.view.managedBean.CategoriaBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceCategoria implements GenericService<Categoria> {
	
	private CategoriaDao categoriaDao; 
	
	public ServiceCategoria() {
		this.categoriaDao = new CategoriaDao();
	}
	
	
	
	@Override
	public boolean salvar(Categoria entidate) {
		
		if(entidate.getIdCategoria() == null){
			
			try {
				categoriaDao.save(entidate);
				UtilMensagens.mensagemInformacao("Categoria de Apartamento Cadastrada com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Inserir a Categoria de Apartamento!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}				
		}else{
			
			try {
				categoriaDao.update(entidate);
				UtilMensagens.mensagemInformacao("Categoria de Apartamento Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Alterar a Categoria de Apartamento!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;	
			}
		}	
	}

	
	@Override
	public void excluir(Categoria entidade) {
		try {
			categoriaDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Ocorreu algum excessão ao Excluir a Categoria!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
		
	}

	
	@Override
	public Categoria carregarEntidade(Categoria entidade) {
		try{
			String jpql = "Select c From Categoria c where c.idCategoria = ?1";
			return categoriaDao.findOne(jpql, entidade.getIdCategoria());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu algum excessão ao buscar os dados da Categoria!");
			return null;
		}
	}

	
	@Override
	public List<Categoria> buscarTodos() {
		try {
			return categoriaDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
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
