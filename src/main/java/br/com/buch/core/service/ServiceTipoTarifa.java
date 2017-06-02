package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.TipoTarifaDao;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.view.managedBean.TipoTarifaBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceTipoTarifa implements GenericService<TipoTarifa> {

	
	private TipoTarifaDao tipoTarifaDao;
	
	
	public ServiceTipoTarifa() {
		tipoTarifaDao =  new TipoTarifaDao();
	}
	
	
	@Override
	public boolean salvar(TipoTarifa entidate) {
		if (entidate.getIdTipoTarifa() == null) {
			
			try {
				tipoTarifaDao.save(entidate);
				
				UtilMensagens.mensagemInformacao("Tipo de Tarifa Cadastrada com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Inserir o Tipo de Tarifa"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}			
		}else{			
			
			try {				
				tipoTarifaDao.update(entidate);
				UtilMensagens.mensagemInformacao("Tipo de Tarifa Alterada com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Atualizar o Tipo de Tarifa"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}			
		}
	}

	
	@Override
	public void excluir(TipoTarifa entidade) {
		try {
			tipoTarifaDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Erro ao Excluir o Tipo de Tarifa" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}	
	}

	
	@Override
	public TipoTarifa carregarEntidade(TipoTarifa entidade) {
		try{
			String jpql = "Select t From TipoTarifa t where t.idTipoTarifa = ?1";
			return tipoTarifaDao.findOne(jpql, entidade.getIdTipoTarifa());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Tipo de Tarifa não encontrada!");
			return null;
		}
	}

	
	@Override
	public List<TipoTarifa> buscarTodos() {				
		try {
			return tipoTarifaDao.findAll();								
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}

	
	public List<TipoTarifa> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<TipoTarifa> lista = null;
		
		if(tipoFiltro.equals(TipoFiltro.CODIGO)){			
			try {
				String jpql = "Select t From TipoTarifa t where t.idTipoTarifa in (" + valorFiltro + ")";
				lista = tipoTarifaDao.find(jpql);
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}
		else if(tipoFiltro.equals(TipoFiltro.NOME)){			
			try{
				lista = tipoTarifaDao.find("Select t From TipoTarifa t where t.nome like ?",valorFiltro);
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}		
		return lista;			
	}

	
}
