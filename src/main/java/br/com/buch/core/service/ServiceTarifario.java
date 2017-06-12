package br.com.buch.core.service;

import java.util.Date;
import java.util.List;

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
	public void excluir(Tarifario entidade) throws Exception{
		try {
			tarifarioDao.delete(entidade);			
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Tarifário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}	
	}

	
	@Override
	public Tarifario carregarEntidade(Tarifario entidade)throws PersistenciaException {
		try{
			String jpql = "Select t From Tarifario t LEFT JOIN FETCH t.categoria "
					+ "	LEFT JOIN FETCH t.tipoTarifa where t.idTarifario = ?1";
			return tarifarioDao.findOne(jpql, entidade.getIdTarifario());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Tarifário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Tarifario> buscarTodos() {
		try {
			return tarifarioDao.find("From Tarifario order by categoria, tipoTarifa, dataInicial");								
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
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
			e.printStackTrace();
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
			String jpql = " Select t From Tarifario t where t.categoria = ?1 and t.tipoTarifa = ?2"
						+ " and ((t.dataInicial BETWEEN ?3 and ?4) or (t.dataFinal BETWEEN ?3 and ?4))";
			
			lista = tarifarioDao.find(jpql, entidate.getCategoria(),entidate.getTipoTarifa(),entidate.getDataInicial(),entidate.getDataFinal());
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		
		return (lista.size() == 0) ?true:false;
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
		return tarifarioDao.findByCategoriaTipoTarifa(categoria, tipoTarifa, data);
	}
}
