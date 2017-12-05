package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.TipoTarifaDao;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.Constantes.ConstantesLista;
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
				Constantes.getInstance().refresh(ConstantesLista.TIPOS_TARIFA);
				return "Tipo de Tarifa Cadastrada com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Tipo de Tarifa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}else{			
			
			try {				
				tipoTarifaDao.update(entidate);
				Constantes.getInstance().refresh(ConstantesLista.TIPOS_TARIFA);
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
			Constantes.getInstance().refresh(ConstantesLista.TIPOS_TARIFA);
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
			return tipoTarifaDao.findOne(TipoTarifaDao.CARREGAR_ENTIDADE, entidade.getIdTipoTarifa());			
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
			lista = tipoFiltro.equals(TipoFiltro.CODIGO) ?
						tipoTarifaDao.find(TipoTarifaDao.FILTRAR_POR_CODIGO, valorFiltro):
						tipoTarifaDao.find(TipoTarifaDao.FILTRAR_POR_NOME, valorFiltro);
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
