package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.HospedeDao;
import br.com.buch.core.entity.Hospede;
import br.com.buch.view.managedBean.HospedeBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;


public class ServiceHospede implements GenericService<Hospede> {

	private HospedeDao hospedeDao;
	
	
	public ServiceHospede() {
		this.hospedeDao = new HospedeDao();
	}
	
	
	
	@Override
	public boolean salvar(Hospede hospede) {
		if(hospede.getIdHospede() == null){
			
			try {
				hospedeDao.save(hospede);
				UtilMensagens.mensagemInformacao("Hóspede Cadastrado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Inserir o Hóspede!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}				
		}else{
			
			try {
				hospedeDao.update(hospede);
				UtilMensagens.mensagemInformacao("Hóspede Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Alterar o Hóspede!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;	
			}
		}			
	}

	
	@Override
	public void excluir(Hospede entidade) {
		try {
			hospedeDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Ocorreu algum excessão ao Excluir o Hóspede!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public Hospede carregarEntidade(Hospede entidade) {
		
		try{
			//String jpql = "Select h From Hospede h LEFT JOIN FETCH h.endereco LEFT JOIN FETCH h.empresa where h.idHospede = ?1";
			String jpql = "Select h From Hospede h LEFT JOIN FETCH h.endereco where h.idHospede = ?1";
			return hospedeDao.findOne(jpql, entidade.getIdHospede());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu algum excessão ao buscar os dados do Hóspede!");
			return null;
		}
	}

	
	@Override
	public List<Hospede> buscarTodos() {
		try {
			return hospedeDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<Hospede> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Hospede> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){
				String jpql = "Select h From Hospede h where h.codigo in (" + valorFiltro + ")";
				lista = hospedeDao.find(jpql);
			}
			else if(tipoFiltro.equals(TipoFiltro.NOME)){
				lista = hospedeDao.find("Select h From Hospede h where h.nome like ?",valorFiltro);
			}			
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Ocorreu algum excessão ao Filtrar os dados do Hóspede!");
			return null;
		}					
	}
}
