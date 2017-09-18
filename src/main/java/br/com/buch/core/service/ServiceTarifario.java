package br.com.buch.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.buch.core.dao.TarifarioDao;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.TarifarioBean.TipoFiltro;

public class ServiceTarifario implements GenericService<Tarifario> {

	private static final String BUSCAR_TODOS = 
			"Select t From Tarifario t left Join FETCH t.categoria left Join FETCH t.tipoTarifa order by t.categoria, t.tipoTarifa, t.dataInicial";
	
	private static final String CARREGAR_ENTIDADE = "Select t From Tarifario t left Join FETCH t.categoria left Join FETCH t.tipoTarifa "
			+ " where t.idTarifario = ?1 order by t.categoria, t.tipoTarifa, t.dataInicial";
	
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
			return tarifarioDao.findOne(CARREGAR_ENTIDADE, entidade.getIdTarifario());			
		}catch (Exception e) {
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Tarifário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Tarifario> buscarTodos() {
		try {
			return tarifarioDao.find(BUSCAR_TODOS);								
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

		
	public List<Tarifario> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Tarifario> lista = null;
		
		try{			
			if(tipoFiltro.equals(TipoFiltro.CATEGORIA)){			

				String jpql = "Select t From Tarifario t LEFT JOIN FETCH t.categoria "
						+ "	LEFT JOIN FETCH t.tipoTarifa where t.categoria.nome  like ?";
				lista = tarifarioDao.find(jpql,valorFiltro);
	
			}
			else if(tipoFiltro.equals(TipoFiltro.TIPO_TARIFA)){			
				
				String jpql = "Select t From Tarifario t LEFT JOIN FETCH t.categoria "
						+ "	LEFT JOIN FETCH t.tipoTarifa where t.tipoTarifa.nome  like ?";
				lista = tarifarioDao.find(jpql,valorFiltro);
		
			}
			else if(tipoFiltro.equals(TipoFiltro.DATA)){			
				
				
				String jpql = " Select t From Tarifario t LEFT JOIN FETCH t.categoria "
							+ "	LEFT JOIN FETCH t.tipoTarifa "
							+ " where t.dataInicial <= ?1 and t.dataFinal >= ?2";
				
				lista = tarifarioDao.find(jpql,valorFiltro, valorFiltro);
											
			}
			
			return lista;
		}catch (Exception e) {
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
				String jpql = " Select t From Tarifario t where t.categoria = ?1 and t.tipoTarifa = ?2"
						+ " and ((t.dataInicial >= ?3 and t.dataInicial <= ?4) or (t.dataFinal >= ?3 and t.dataFinal <= ?4))";
				lista = tarifarioDao.find(jpql, entidate.getCategoria(),entidate.getTipoTarifa(),entidate.getDataInicial(),entidate.getDataFinal());
			}else{
				String jpql = " Select t From Tarifario t where t.categoria = ?1 and t.tipoTarifa = ?2 and t.idTarifario != ?3"
						+ " and ((t.dataInicial >= ?4 and t.dataInicial <= ?5) or (t.dataFinal >= ?4 and t.dataFinal <= ?5))";
				lista = tarifarioDao.find(jpql, entidate.getCategoria(),entidate.getTipoTarifa(),
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
