package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.HospedagemDao;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;

public class ServiceHospedagem implements GenericService<Hospedagem> {

	HospedagemDao dao;
	
	
	public ServiceHospedagem() {
		dao = new HospedagemDao();
	}

	
	
	@Override
	public String salvar(Hospedagem entidate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public String excluir(Hospedagem entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public Hospedagem carregarEntidade(Hospedagem entidade) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public List<Hospedagem> buscarTodos() throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public void consisteAntesEditar(Hospedagem entidade) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

}
