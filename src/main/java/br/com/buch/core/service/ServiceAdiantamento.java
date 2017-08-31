package br.com.buch.core.service;

import java.util.Date;
import java.util.List;

import br.com.buch.core.dao.AdiantamentoDao;
import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.TipoFiltroAdiantamento;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;

public class ServiceAdiantamento implements GenericService<Adiantamento> {

	private static final String CARREGAR_ENTIDADE = 
		"Select a From Adiantamento a LEFT JOIN FETCH a.hospede LEFT JOIN FETCH a.localRecebimento where a.idAdiantamento = ?";
	
	private static final String BUSCAR_TODOS = 
		"Select a From Adiantamento a LEFT JOIN FETCH a.hospede";
	
	private AdiantamentoDao adiantamentoDao;
	
	
	public ServiceAdiantamento() {
		adiantamentoDao = new AdiantamentoDao();
	}

	
	@Override
	public String salvar(Adiantamento entidate)throws Exception {
		if(entidate.getIdAdiantamento() == null){
			
			try {
				adiantamentoDao.save(entidate);				
				return "Adiantamento de Cliente Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();				
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Adiantamento de Cliente!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));				
			}				
		}else{
			
			try {
				adiantamentoDao.update(entidate);
				return "Adiantamento a Cliente Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao Alterar o Adiantamento de Cliente!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}	
	}
	
	
	@Override
	public String excluir(Adiantamento entidade) throws Exception{
		
		if (entidade.getSaldo() == 0.00) {
			throw new NegocioException("Adiantamento não pode ser excluido pois já foi utilizado!");
		}
		
		try {
			adiantamentoDao.delete(entidade);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();            
        	throw new PersistenciaException("Ocorreu uma exceção ao excluir o Adiantamento de Cliente!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}
		

	@Override
	public Adiantamento carregarEntidade(Adiantamento entidade)throws PersistenciaException {
		try{
			return adiantamentoDao.findOne(CARREGAR_ENTIDADE, entidade.getIdAdiantamento());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Adiantamento a Cliente!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}
	

	@Override
	public List<Adiantamento> buscarTodos() {
		try {
			return adiantamentoDao.find(BUSCAR_TODOS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Adiantamento> filtrarTabela(TipoFiltroAdiantamento tipoFiltro , String valorFiltro)throws Exception{
		List<Adiantamento> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltroAdiantamento.CODIGO)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.codigo in (" + valorFiltro + ")";
				lista = adiantamentoDao.find(jpql);
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_CODIGO)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.hospede.codigo = ?";
				lista = adiantamentoDao.find(jpql, valorFiltro);
			}	
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_NOME)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.hospede.nome like ?";
				lista = adiantamentoDao.find(jpql,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_CPF)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.hospede.cpf = ?";
				lista = adiantamentoDao.find(jpql,valorFiltro.replace(".", "").replace("-", ""));
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.DATA_EMISSAO)){			
				
				try{
					String jpql = " Select a From Adiantamento a LEFT JOIN FETCH a.hospede "
								+ " where a.dtEmissao = ?1 ";
					
					lista = adiantamentoDao.find(jpql,valorFiltro);
				}catch (Exception e) {
					e.printStackTrace();
				}								
			}
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Adiantamento de Cliente!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	public void realizarBaixa(Adiantamento entidade)throws Exception{
		if(entidade.getSaldo() == 0.00){
			throw new NegocioException("Adiantamento está sem saldo, não pode ser utilizado novamente!");
		}
		
		entidade.setDtBaixa(new Date());
		entidade.setSaldo(0.00);
		adiantamentoDao.update(entidade);
	}


	@Override
	public void consisteAntesEditar(Adiantamento entidade)throws NegocioException{
		if(entidade.getSaldo() == 0.0){
			throw new NegocioException("Adiantamento não pode ser Alterado pois não possui saldo disponivel!");
		}		
	}
	
	
	public Adiantamento buscarPorId(Integer id)throws Exception{
		try {
			return adiantamentoDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	public List<Adiantamento> buscarPorHospede(Hospede hospede) throws Exception{
		return adiantamentoDao.find("Select a From Adiantamento a LEFT JOIN FETCH a.hospede LEFT JOIN FETCH a.localRecebimento where a.hospede = ? and a.saldo > 0", hospede);
	}
}