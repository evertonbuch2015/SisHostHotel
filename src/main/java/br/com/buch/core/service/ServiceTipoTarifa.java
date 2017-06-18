package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.TipoTarifaDao;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.TipoTarifaBean.TipoFiltro;

public class ServiceTipoTarifa implements GenericService<TipoTarifa> {

	
	private TipoTarifaDao tipoTarifaDao;
	
	
	public ServiceTipoTarifa() {
		tipoTarifaDao =  new TipoTarifaDao();
	}
	
	
	@Override
	public String salvar(TipoTarifa entidate)throws Exception {
		if (entidate.getIdTipoTarifa() == null) {
			
			try {
				tipoTarifaDao.save(entidate);
				return "Tipo de Tarifa Cadastrada com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Tipo de Tarifa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}else{			
			
			try {				
				tipoTarifaDao.update(entidate);
				return "Tipo de Tarifa Alterada com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar o Tipo de Tarifa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
	}

	
	@Override
	public String excluir(TipoTarifa entidade)throws Exception {
		try {
			tipoTarifaDao.delete(entidade);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Tipo de Tarifa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}	
	}

	
	@Override
	public TipoTarifa carregarEntidade(TipoTarifa entidade)throws PersistenciaException {
		try{
			String jpql = "Select t From TipoTarifa t where t.idTipoTarifa = ?1";
			return tipoTarifaDao.findOne(jpql, entidade.getIdTipoTarifa());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Tipo de Tarifa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
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

	
	public List<TipoTarifa> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<TipoTarifa> lista = null;
		
		try{
			
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){							
				String jpql = "Select t From TipoTarifa t where t.idTipoTarifa in (" + valorFiltro + ")";
				lista = tipoTarifaDao.find(jpql);							
			}
			else if(tipoFiltro.equals(TipoFiltro.NOME)){							
				lista = tipoTarifaDao.find("Select t From TipoTarifa t where t.nome like ?",valorFiltro);						
			}		
			
			return lista;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Tipo de Tarifa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public void consisteAntesEditar(TipoTarifa entidade)throws NegocioException {}

	
}
