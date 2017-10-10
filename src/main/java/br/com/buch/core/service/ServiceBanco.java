package br.com.buch.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import br.com.buch.core.dao.BancoDao;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.Constantes.ConstantesLista;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;

public class ServiceBanco extends Observable implements GenericService<Banco> {

	private BancoDao dao;

	
	public ServiceBanco() {
		dao = new BancoDao();
		addObserver(Constantes.getInstance());
	}

	
	@Override
	public String salvar(Banco entidate)throws Exception {
		if (entidate.getIdBanco() == null) {

			try {
				dao.save(entidate);
				notificarOuvintes();
				return "Banco Cadastrado com Sucesso!";
			} catch (Exception e) {
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Banco!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		} else {

			try {
				dao.update(entidate);
				notificarOuvintes();
				return "Banco Alterado com Sucesso!";
			} catch (Exception e) {
				throw new PersistenciaException("Ocorreu uma exceção ao Alterar o Banco!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}
	}

	
	@Override
	public String excluir(Banco entidade)throws Exception{
		try {
			dao.delete(entidade);
			notificarOuvintes();
			return "Banco Excluido com Sucesso!";
		}catch (Exception ex) {
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Banco!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public Banco carregarEntidade(Banco entidade)throws PersistenciaException {
		try{			
			return dao.findById(entidade.getIdBanco());
		}catch (Exception e) {
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Banco!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Banco> buscarTodos() {
		try {
			return dao.findAll();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	
	@Override
	public void consisteAntesEditar(Banco entidade) {

	}

	
	private void notificarOuvintes(){
		setChanged();
		notifyObservers(ConstantesLista.BANCOS);
	}
}
