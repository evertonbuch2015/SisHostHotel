package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.HospedeDao;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.TipoFiltroHospede;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;


public class ServiceHospede implements GenericService<Hospede> {

	private HospedeDao hospedeDao;
	
	
	public ServiceHospede() {
		this.hospedeDao = new HospedeDao();
	}
	
	
	
	@Override
	public String salvar(Hospede hospede) throws Exception{
		if(hospede.getIdHospede() == null){
			
			try {
				hospedeDao.save(hospede);
				return "Hóspede Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Hospede!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				hospedeDao.update(hospede);
				return "Hóspede Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar o Hospede!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}			
	}

	
	@Override
	public void excluir(Hospede entidade) throws Exception{
		try {
			hospedeDao.delete(entidade);			
		}		
		catch (Exception ex) {
        	ex.printStackTrace();
        	if(ex.getCause().toString().contains("ConstraintViolationException")){
        		throw new PersistenciaException("Hóspede não pode ser excluido pois existem registros vinculados a ele!");
        	}else{
        		throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Hóspede!" + 
                		" \nErro: " + UtilErros.getMensagemErro(ex));
        	}
		}
	}

	
	@Override
	public Hospede carregarEntidade(Hospede entidade)throws PersistenciaException {		
		try{
			String jpql = "Select h From Hospede h LEFT JOIN FETCH h.endereco where h.idHospede = ?1";
			return hospedeDao.findOne(jpql, entidade.getIdHospede());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Hóspede!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
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

	
	public List<Hospede> filtrarTabela(TipoFiltroHospede tipoFiltro , String valorFiltro)throws Exception{
		List<Hospede> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltroHospede.CODIGO)){
				String jpql = "Select h From Hospede h where h.codigo in (" + valorFiltro + ")";
				lista = hospedeDao.find(jpql);
			}
			else if(tipoFiltro.equals(TipoFiltroHospede.NOME)){
				lista = hospedeDao.find("Select h From Hospede h where h.nome like ?",valorFiltro);
			}	
			else if(tipoFiltro.equals(TipoFiltroHospede.CPF)){
				lista = hospedeDao.find("Select h From Hospede h where h.cpf like ?",valorFiltro);
			}
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Hóspede!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}



	
	
	@Override
	public void consisteAntesEditar(Hospede entidade)throws NegocioException {
	}
}
