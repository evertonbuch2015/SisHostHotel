package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.ApartamentoDao;
import br.com.buch.core.entity.Apartamento;
import br.com.buch.view.managedBean.ApartamentoBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceApartamento implements GenericService<Apartamento> {

	private ApartamentoDao apartamentoDao;
	
	
	public ServiceApartamento() {
		this.apartamentoDao = new ApartamentoDao();
	}	
	
	
	@Override
	public boolean salvar(Apartamento entidate) {
		if(entidate.getIdApartamento() == null){
			
			try {
				apartamentoDao.save(entidate);
				UtilMensagens.mensagemInformacao("Apartamento Cadastrado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Inserir o Apartamento!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}				
		}else{
			
			try {
				apartamentoDao.update(entidate);
				UtilMensagens.mensagemInformacao("Apartamento Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Alterar o Apartamento!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;	
			}
		}
	}

	
	@Override
	public void excluir(Apartamento entidade) {
		try {
			apartamentoDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Ocorreu algum excessão ao Excluir o Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public Apartamento carregarEntidade(Apartamento entidade) {
		try{
			String jpql = "Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.idApartamento = ?1";
			return apartamentoDao.findOne(jpql, entidade.getIdApartamento());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu algum excessão ao buscar os dados do Apartamento!");
			return null;
		}
	}

	
	@Override
	public List<Apartamento> buscarTodos() {
		try {
			return apartamentoDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	
	public List<Apartamento> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Apartamento> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){
				String jpql = "Select a From Apartamento a where a.codigo in (" + valorFiltro + ")";
				lista = apartamentoDao.find(jpql);
				
			}else if(tipoFiltro.equals(TipoFiltro.SITUACAO)){
				lista = apartamentoDao.find("Select a From Apartamento a where a.situacao like ?1", valorFiltro);
				
			}else if(tipoFiltro.equals(TipoFiltro.CATEGORIA)){
				lista = apartamentoDao.find("Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.categoria.nome like ?1", valorFiltro);
				
			}
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Ocorreu algum excessão ao Filtrar os dados do Apartamento!");
			return null;
		}					
	}
	
}
