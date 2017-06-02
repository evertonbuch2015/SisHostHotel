package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.TarifarioDao;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.view.managedBean.TarifarioBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceTarifario implements GenericService<Tarifario> {

	private TarifarioDao tarifarioDao;

	
	public ServiceTarifario() {
		this.tarifarioDao = new TarifarioDao();
	}

	
	
	@Override
	public boolean salvar(Tarifario entidate) {
		
		if (entidate.getIdTarifario() == null) {

			try {
				if(!validaAntesSalvar(entidate))
					throw new Exception("Já existe um Tarifário para esta categoria e Tipo de tarifa no intervalo de data informada!");
				
				tarifarioDao.save(entidate);

				UtilMensagens.mensagemInformacao("Tarifário Cadastrado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens
						.mensagemErro("Erro ao Inserir o Tarifário" + "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}
		} else {

			try {
				tarifarioDao.update(entidate);
				UtilMensagens.mensagemInformacao("Tarifário Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens
						.mensagemErro("Erro ao Atualizar o Tarifário" + "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}
		}

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



	@Override
	public void excluir(Tarifario entidade) {
		try {
			tarifarioDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Erro ao Excluir o tarifário" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}	
	}

	
	@Override
	public Tarifario carregarEntidade(Tarifario entidade) {
		try{
			String jpql = "Select t From Tarifario t LEFT JOIN FETCH t.categoria "
					+ "	LEFT JOIN FETCH t.tipoTarifa where t.idTarifario = ?1";
			return tarifarioDao.findOne(jpql, entidade.getIdTarifario());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu uma excessão ao buscar os dados do Tarifário!");
			return null;
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

	
	
	public List<Tarifario> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Tarifario> lista = null;
		
		if(tipoFiltro.equals(TipoFiltro.CATEGORIA)){			
			
			try {
				String jpql = "Select t From Tarifario t LEFT JOIN FETCH t.categoria "
						+ "	LEFT JOIN FETCH t.tipoTarifa where t.categoria.nome  like ?";
				lista = tarifarioDao.find(jpql,valorFiltro);
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}
		else if(tipoFiltro.equals(TipoFiltro.TIPO_TARIFA)){			
			
			try{
				String jpql = "Select t From Tarifario t LEFT JOIN FETCH t.categoria "
						+ "	LEFT JOIN FETCH t.tipoTarifa where t.tipoTarifa.nome  like ?";
				lista = tarifarioDao.find(jpql,valorFiltro);
			}catch (Exception e) {
				e.printStackTrace();
			}			
		}
		else if(tipoFiltro.equals(TipoFiltro.DATA)){			
			
			try{
				String jpql = " Select t From Tarifario t LEFT JOIN FETCH t.categoria "
							+ "	LEFT JOIN FETCH t.tipoTarifa "
							+ " where t.dataInicial <= ?1 and t.dataFinal >= ?2";
				
				lista = tarifarioDao.find(jpql,valorFiltro, valorFiltro);
			}catch (Exception e) {
				e.printStackTrace();
			}								
		}
		
		return lista;
	}
}
