package br.com.buch.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.ApartamentoDao;
import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.enumerated.SituacaoApartamento;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.Constantes.ConstantesLista;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.ApartamentoBean.TipoFiltro;

public class ServiceApartamento implements GenericService<Apartamento> {
	
	private ApartamentoDao apartamentoDao;
	
	
	public ServiceApartamento() {
		this.apartamentoDao = new ApartamentoDao();
	}	
	
	
	@Override
	public String salvar(Apartamento entidate)throws Exception {
		if(entidate.getIdApartamento() == null){
			
			try {
				apartamentoDao.save(entidate);
				Constantes.getInstance().refresh(ConstantesLista.APARTAMENTOS);
				return "Apartamento Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Apartamento!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{			
			try {
				apartamentoDao.update(entidate);
				Constantes.getInstance().refresh(ConstantesLista.APARTAMENTOS);
				return "Apartamento Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao Alterar o Apartamento!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}		
	}

	
	@Override
	public String excluir(Apartamento entidade)throws Exception {
		try {
			apartamentoDao.delete(entidade);
			Constantes.getInstance().refresh(ConstantesLista.APARTAMENTOS);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
            throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public Apartamento carregarEntidade(Apartamento entidade)throws PersistenciaException {
		try{
			return apartamentoDao.findOne(ApartamentoDao.CARREGAR_ENTIDADE, entidade.getIdApartamento());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Apartamento> buscarTodos()throws Exception{
		return Constantes.getInstance().getListaApartamentos();
	}

	
	public List<Apartamento> filtrarTabela(TipoFiltro tipoFiltro , Object valorFiltro)throws Exception{
		List<Apartamento> lista = null;
		
		try {			
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){
				String jpql = "Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.numero in (" + valorFiltro + ")";
				lista = apartamentoDao.find(jpql);				
			}else if(tipoFiltro.equals(TipoFiltro.SITUACAO)){
				lista = apartamentoDao.find(ApartamentoDao.BUSCAR_POR_SITUACAO, valorFiltro);
				
			}else if(tipoFiltro.equals(TipoFiltro.CATEGORIA)){
				lista = apartamentoDao.find(ApartamentoDao.BUSCAR_POR_CATEGORIA, valorFiltro);				
			}
			
			return lista;			
		} catch (PersistenceException e) {
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	@Override
	public void consisteAntesEditar(Apartamento entidade)throws NegocioException {
		
	}

	
	public Apartamento buscarPorId(Integer id)throws Exception{
		try {
			return apartamentoDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public List<Apartamento> buscarTodosLivres(String cemp) {
		try {
			return apartamentoDao.find(ApartamentoDao.BUSCAR_LIVRES, SituacaoApartamento.LIVRE, cemp);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

		
	/**
	 * Verifica a Disponibilidade do Apartamento no intervalo de Data Fornecida.
	 * @param Apartamento apartamento
	 * @param Data Entrada
	 * @param Data Saida
	 * 
	 */
	public void verificaDisponibilidade(Apartamento apartamento, Date dataEntrada, Date dataSaida)throws NegocioException{		
		SituacaoApartamento situacaoApartamento = 
				apartamentoDao.verificaDisponibilidade(apartamento.getIdApartamento(), dataEntrada, dataSaida);
		
		switch (situacaoApartamento) {
		case OCUPADO:
			throw new NegocioException("Apartamento não está disponivel para esta Data!");			
		default:
			break;
		}
	}

	
	public List<Apartamento> buscarPorNumero(Integer numero) {		
		try{			
			return apartamentoDao.find(ApartamentoDao.BUSCAR_POR_NUMERO, numero);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}	
	}
}
