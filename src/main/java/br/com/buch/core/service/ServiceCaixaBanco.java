package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.CaixaBancoDao;
import br.com.buch.core.entity.CaixaBanco;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceCaixaBanco implements GenericService<CaixaBanco> {

	private CaixaBancoDao caixaBancoDao;

	public ServiceCaixaBanco() {
		caixaBancoDao = new CaixaBancoDao();
	}

	@Override
	public boolean salvar(CaixaBanco entidate) {

		if (entidate.getIdCaixaBanco() == null) {

			try {
				caixaBancoDao.save(entidate);
				UtilMensagens.mensagemInformacao("Local de Recebimento Cadastrado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens
						.mensagemErro("Erro ao Inserir o Local de Recebimento!" + "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}
		} else {

			try {
				caixaBancoDao.update(entidate);
				UtilMensagens.mensagemInformacao("Local de Recebimento Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens
						.mensagemErro("Erro ao Alterar o Local de Recebimento!" + "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}
		}
	}

	@Override
	public void excluir(CaixaBanco entidade) {
		try {
			caixaBancoDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Ocorreu algum excessão ao Excluir o Local de Recebimento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	@Override
	public CaixaBanco carregarEntidade(CaixaBanco entidade) {
		try{			
			return caixaBancoDao.findById(entidade.getIdCaixaBanco());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu uma excessão ao buscar os dados do Local de Recebimento!");
			return null;
		}
	}

	@Override
	public List<CaixaBanco> buscarTodos() {
		try {
			return caixaBancoDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
