package br.com.buch.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.TarifarioDao;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.TarifarioBean.TipoFiltro;

public class ServiceTarifario implements GenericService<Tarifario> {

	private TarifarioDao tarifarioDao;

	
	public ServiceTarifario() {
		this.tarifarioDao = new TarifarioDao();
	}

	
	@Override
	public String salvar(Tarifario entidate) throws Exception{		
		if (entidate.getIdTarifario() == null) {

			try {
				if(!validaAntesSalvar(entidate))
					throw new Exception("Já existe um Tarifário para esta categoria e Tipo de tarifa no intervalo de data informada!");
				
				tarifarioDao.save(entidate);
				return "Tarifário Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Tarifário!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		} else {
			if(!validaAntesSalvar(entidate))
				throw new Exception("Já existe um Tarifário para esta categoria e Tipo de tarifa no intervalo de data informada!");
			
			try {
				tarifarioDao.update(entidate);
				return "Tarifário Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar o Tarifário!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}
	}


	@Override
	public String excluir(Tarifario entidade) throws Exception{
		try {
			tarifarioDao.delete(entidade);
			return "Tarifário Excluido com Sucesso";
		}catch (Exception ex) {
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Tarifário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}	
	}

	
	@Override
	public Tarifario carregarEntidade(Tarifario entidade)throws PersistenciaException {
		try{
			return tarifarioDao.findOne(TarifarioDao.CARREGAR_ENTIDADE, entidade.getIdTarifario());			
		}catch (Exception e) {
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Tarifário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Tarifario> buscarTodos() {
		try {
			return tarifarioDao.find(TarifarioDao.BUSCAR_TODOS);								
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

		
	public List<Tarifario> filtrarTabela(TipoFiltro tipoFiltro , Object...valorFiltro)throws Exception{
		List<Tarifario> lista = null;
		
		try{			
			if(tipoFiltro.equals(TipoFiltro.CATEGORIA)){
				lista = tarifarioDao.find(TarifarioDao.FILTRAR_POR_CATEGORIA, valorFiltro);
	
			}
			
			else if(tipoFiltro.equals(TipoFiltro.TIPO_TARIFA)){			 
				lista = tarifarioDao.find(TarifarioDao.FILTRAR_POR_TIPO_TARIFA, valorFiltro);
		
			}
			else if(tipoFiltro.equals(TipoFiltro.DATA)){		
				lista = tarifarioDao.find(valorFiltro.length == 1 ? 
						TarifarioDao.FILTRAR_POR_DATA : TarifarioDao.FILTRO_POR_DATA_BEETWEN, valorFiltro);											
			}
			
			return lista;
		}catch (PersistenceException e) {
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Tarifário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}	
	}
	
	
	@Override
	public void consisteAntesEditar(Tarifario entidade) throws NegocioException{
		
	}
	
		
	private boolean validaAntesSalvar(Tarifario entidate) {
		List<Tarifario> lista = null;
		
		try {			
			if(entidate.getIdTarifario() == null){
				lista = tarifarioDao.find(TarifarioDao.VALIDA_ANTES_SALVAR_01, 
							entidate.getCategoria(),entidate.getTipoTarifa(),entidate.getDataInicial(),entidate.getDataFinal());
			}else{
				lista = tarifarioDao.find(TarifarioDao.VALIDA_ANTES_SALVAR_02, entidate.getCategoria(),entidate.getTipoTarifa(),
							entidate.getIdTarifario(),entidate.getDataInicial(),entidate.getDataFinal());
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}				
		
		return ((lista == null) || ( lista.size()) == 0) ?true:false;
	}


	public Tarifario buscarPeloId(Integer id){
		try {
			return tarifarioDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Tarifario buscarPelaCategoriaTipoTarifa(Integer categoria, Integer tipoTarifa, Date data )throws Exception{
		try {
			return tarifarioDao.findByCategoriaTipoTarifa(categoria, tipoTarifa, data);
		} catch (Exception e) {
			if(e.toString().contains("NoResultException")){
				throw new NegocioException("Não existe Tarifário cadastrado para esta Categoria e Tipo de Tarifa!");
			}
			else{
				throw e;
			}
		}		
	}
}
