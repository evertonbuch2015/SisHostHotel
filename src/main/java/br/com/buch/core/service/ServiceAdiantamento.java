package br.com.buch.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.buch.core.dao.CrpAdiantamentoDao;
import br.com.buch.core.entity.Adiantamento;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceAdiantamento implements GenericService<Adiantamento> {

	private CrpAdiantamentoDao adiantamentoDao;
	
	public ServiceAdiantamento() {
		adiantamentoDao = new CrpAdiantamentoDao();
	}

	
	
	
	@Override
	public boolean salvar(Adiantamento entidate) {
		if(entidate.getIdAdiantamento() == null){
			
			try {
				adiantamentoDao.save(entidate);
				UtilMensagens.mensagemInformacao("Adiantamento a Cliente Cadastrado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Inserir o Adiantamento a Cliente!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}				
		}else{
			
			try {
				adiantamentoDao.update(entidate);
				UtilMensagens.mensagemInformacao("Adiantamento a Cliente Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Alterar o Adiantamento a Cliente!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;	
			}
		}	
	}
	
	

	@Override
	public void excluir(Adiantamento entidade) {
		try {
			adiantamentoDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Ocorreu uma excessão ao Excluir o Adiantamento a Cliente!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}
	
	

	@Override
	public Adiantamento carregarEntidade(Adiantamento entidade) {
		try{
			String jpql = "Select a From CprAdiantamento a LEFT JOIN FETCH a.hospede where a.idCrpAdiantamento = ?1";
			return adiantamentoDao.findOne(jpql, entidade.getIdAdiantamento());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu uma excessão ao buscar os dados do Adiantamento a Cliente!");
			return null;
		}
	}
	
	

	@Override
	public List<Adiantamento> buscarTodos() {
		return new ArrayList<Adiantamento>();
	}

}
