package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.CaixaBancoDao;
import br.com.buch.core.entity.CaixaBanco;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;

public class ServiceCaixaBanco implements GenericService<CaixaBanco> {

	private CaixaBancoDao caixaBancoDao;

	
	public ServiceCaixaBanco() {
		caixaBancoDao = new CaixaBancoDao();
	}

	
	@Override
	public String salvar(CaixaBanco entidate)throws Exception {

		if (entidate.getIdCaixaBanco() == null) {

			try {
				caixaBancoDao.save(entidate);
				return "Local de Recebimento Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Local de Recebimento!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		} else {

			try {
				caixaBancoDao.update(entidate);
				return "Local de Recebimento Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao Alterar o Local de Recebimento!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}
	}

	
	@Override
	public void excluir(CaixaBanco entidade)throws Exception{
		try {
			caixaBancoDao.delete(entidade);

		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Local de Recebimento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public CaixaBanco carregarEntidade(CaixaBanco entidade)throws PersistenciaException {
		try{			
			return caixaBancoDao.findById(entidade.getIdCaixaBanco());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Local de Recebimento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
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

	
	@Override
	public void consisteAntesEditar(CaixaBanco entidade) {

	}

}
