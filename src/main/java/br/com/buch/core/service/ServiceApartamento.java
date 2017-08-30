package br.com.buch.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.buch.core.dao.ApartamentoDao;
import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.enumerated.SituacaoApartamento;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.ApartamentoBean.TipoFiltro;

public class ServiceApartamento implements GenericService<Apartamento> {

	private static final String BUSCAR_TODAS = 
			"Select a From Apartamento a LEFT JOIN FETCH a.categoria ORDER by a.numero";

	private static final String BUSCAR_POR_SITUACAO = 
			"Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.situacao like ?1";

	private static final String BUSCAR_POR_CATEGORIA = 
			"Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.categoria.nome like ?1";

	private static final String BUSCAR_LIVRES = 
			"Select a From Apartamento a where a.situacao = ?1";

	private static final String CARREGAR_ENTIDADE = 
			"Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.idApartamento = ?1";
	
	private ApartamentoDao apartamentoDao;
	
	
	public ServiceApartamento() {
		this.apartamentoDao = new ApartamentoDao();
	}	
	
	
	@Override
	public String salvar(Apartamento entidate)throws Exception {
		if(entidate.getIdApartamento() == null){
			
			try {
				apartamentoDao.save(entidate);
				return "Apartamento Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Apartamento!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				apartamentoDao.update(entidate);
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
			return apartamentoDao.findOne(CARREGAR_ENTIDADE, entidade.getIdApartamento());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Apartamento> buscarTodos()throws PersistenciaException {
		try {
			return apartamentoDao.find(BUSCAR_TODAS);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	
	public List<Apartamento> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Apartamento> lista = null;
		
		try {			
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){
				String jpql = "Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.numero in (" + valorFiltro + ")";
				lista = apartamentoDao.find(jpql);				
			}else if(tipoFiltro.equals(TipoFiltro.SITUACAO)){
				lista = apartamentoDao.find(BUSCAR_POR_SITUACAO, valorFiltro);
				
			}else if(tipoFiltro.equals(TipoFiltro.CATEGORIA)){
				lista = apartamentoDao.find(BUSCAR_POR_CATEGORIA, valorFiltro);				
			}
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
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
	
	
	public List<Apartamento> buscarTodosLivres() {
		try {
			return apartamentoDao.find(BUSCAR_LIVRES, SituacaoApartamento.LIVRE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

		
	/**
	 * Verifica a Disponibilidade do Apartamento no intervalo de Data Fornecida.
	 * @param Id do Apartamento
	 * @param Data Entrada
	 * @param Data Saida
	 * 
	 */
	public void verificaDisponibilidade(Integer id, Date dataEntrada, Date dataSaida)throws NegocioException{
		
		SituacaoApartamento situacaoApartamento = apartamentoDao.verificaDisponibilidade(id, dataEntrada, dataSaida);
		
		switch (situacaoApartamento) {
		case OCUPADO:
			throw new NegocioException("Apartamento não está disponivel para esta Data!");
			
		default:
			break;
		}
	}


	
	
	
	public List<Apartamento> buscarPorNumero(Integer numero) {		
		try{
			String jpql = "Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.numero >= ?";
			return apartamentoDao.find(jpql, numero);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}	
	}
	
}
