package br.com.buch.core.service;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.AdiantamentoDao;
import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.enumerated.TipoFiltroAdiantamento;
import br.com.buch.core.enumerated.TipoFiltroRecebimento;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;

public class ServiceAdiantamento implements GenericService<Adiantamento> {

	
	
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
			return adiantamentoDao.findOne(AdiantamentoDao.CARREGAR_ENTIDADE, entidade.getIdAdiantamento());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Adiantamento a Cliente!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}
	

	@Override
	public List<Adiantamento> buscarTodos() {
		try {
			return adiantamentoDao.find(AdiantamentoDao.BUSCAR_TODOS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Adiantamento> filtrarTabela(TipoFiltroAdiantamento tipoFiltro , Object...valorFiltro)throws Exception{
		List<Adiantamento> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltroAdiantamento.CODIGO)){
				if(valorFiltro[0] == null || valorFiltro[0].equals("")){ 
					throw new NegocioException("Informe um código para Filtrar!");
				}
				lista = adiantamentoDao.find(AdiantamentoDao.FILTRAR_POR_CODIGO, valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_NOME)){
				lista = adiantamentoDao.find(AdiantamentoDao.FILTRAR_POR_NOME_HOSPEDE, 
						valorFiltro[0].equals("") ? valorFiltro[0] : String.valueOf(valorFiltro[0]).toLowerCase());
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_CPF)){
				lista = adiantamentoDao.find(AdiantamentoDao.FILTRAR_POR_CPF_HOSPEDE, 
						String.valueOf(valorFiltro[0]).replace("-","").replace(".", ""));
			}
			
			else if(tipoFiltro.equals(TipoFiltroRecebimento.LOCAL_RECEBIMENTO)){
				if(valorFiltro[0] == null){
					throw new NegocioException("Informe um Local de Recebimento para Filtrar!");
				}
				lista = adiantamentoDao.find(AdiantamentoDao.FILTRAR_POR_BANCO,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.DATA_EMISSAO)){				
				lista = adiantamentoDao.find(valorFiltro.length == 1 ? AdiantamentoDao.FILTRO_POR_DATA_ENTRADA : AdiantamentoDao.FILTRO_POR_DATA_ENTRADA_BEETWEN, valorFiltro);
			}
			
			return lista;			
		} 
		
		catch (PersistenceException e) {			
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
			throw new NegocioException("Adiantamento não pode ser alterado pois não possui saldo disponivel!");
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

}