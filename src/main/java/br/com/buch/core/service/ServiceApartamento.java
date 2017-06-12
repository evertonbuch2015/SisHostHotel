package br.com.buch.core.service;

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
	public void excluir(Apartamento entidade)throws Exception {
		try {
			apartamentoDao.delete(entidade);			
		}catch (Exception ex) {
        	ex.printStackTrace();
            throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public Apartamento carregarEntidade(Apartamento entidade)throws PersistenciaException {
		try{
			String jpql = "Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.idApartamento = ?1";
			return apartamentoDao.findOne(jpql, entidade.getIdApartamento());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Apartamento> buscarTodos() {
		try {
			return apartamentoDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	
	public List<Apartamento> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Apartamento> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){
				String jpql = "Select a From Apartamento a where a.codigo in (" + valorFiltro + ")";
				lista = apartamentoDao.find(jpql);
				
			}else if(tipoFiltro.equals(TipoFiltro.SITUACAO)){
				lista = apartamentoDao.find("Select a From Apartamento a where a.situacao like ?1", valorFiltro);
				
			}else if(tipoFiltro.equals(TipoFiltro.CATEGORIA)){
				lista = apartamentoDao.find("Select a From Apartamento a LEFT JOIN FETCH a.categoria where a.categoria.nome like ?1", valorFiltro);
				
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

	
	public List<Apartamento> buscarTodosLivres() {
		try {
			return apartamentoDao.find("Select a From Apartamento a where a.situacao = ?1", SituacaoApartamento.LIVRE);
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
	 * @return Retorna 1 para Reservado e 0 para Disponivel.
	 * 
	 */
	public Integer verificaDisponibilidade(Integer id, Date dataEntrada, Date dataSaida){
		return apartamentoDao.verificaDisponibilidade(id, dataEntrada, dataSaida);
	}
}
