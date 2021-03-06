package br.com.buch.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.RecebimentoDao;
import br.com.buch.core.entity.Recebimento;
import br.com.buch.core.entity.Recebimento.OrigemRecebimento;
import br.com.buch.core.enumerated.TipoFiltroRecebimento;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;

public class ServiceRecebimento implements GenericService<Recebimento> {
	
	private RecebimentoDao dao; 
	
	
	public ServiceRecebimento() {
		this.dao = new RecebimentoDao();
	}
	
	
	@Override
	public String salvar(Recebimento entidate)throws Exception {		
		if(entidate.getIdRecebimento() == null){
			try {
				dao.save(entidate);
				return "Recebimento inserido com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException(
						"Ocorreu uma exceção ao inserir o Recebimento! \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{			
			try {
				dao.update(entidate);
				return "Recebimento Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException(
						"Ocorreu uma exceção ao inserir o Recebimento! \nErro: " + UtilErros.getMensagemErro(e));
			}
		}	
	}

	
	@Override
	public String excluir(Recebimento entidade) throws Exception{
		if (! entidade.getOrigemRecebimento().equals(OrigemRecebimento.ENTRADA_MANUAL)){
			throw new NegocioException("Exclusão permitida apenas para lançamentos do tipo Entrada Manual!");
		}
		
		try {
			dao.delete(entidade);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException(
        			"Ocorreu uma exceção ao Excluir o Recebimento!\nErro: " + UtilErros.getMensagemErro(ex));
		}		
	}

	
	@Override
	public Recebimento carregarEntidade(Recebimento entidade)throws PersistenciaException {
		try{			
			return dao.findOne(RecebimentoDao.CARREGAR_ENTIDADE, entidade.getIdRecebimento());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException(
					"Ocorreu uma exceção ao buscar os dados do Recebimento!\nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Recebimento> buscarTodos()throws PersistenciaException {		
		Calendar c1 = Calendar.getInstance();
	    Calendar c2 = Calendar.getInstance();
	    
	    //Dentro do mês Corrente
  		//c1.set(Calendar.DAY_OF_MONTH, c1.getMinimum(Calendar.DAY_OF_MONTH));
        //c2.set(Calendar.DAY_OF_MONTH, c1.getMaximum(Calendar.DAY_OF_MONTH));
  	    
  	    //30 dias para trás e 30 dias para frente 
  	    c1.add(Calendar.DAY_OF_MONTH, -30);
  	    c2.add(Calendar.DAY_OF_MONTH, 30);
        
        Date d1 = c1.getTime();
        Date d2 = c2.getTime();
        
		try {
			return dao.find(RecebimentoDao.BUSCAR_TODOS, d1, d2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException(
					"Ocorreu uma exceção ao buscar os dados da Hospedagem!\nErro: " + UtilErros.getMensagemErro(e));
		}
	}

		
	public List<Recebimento> filtrarTabela(TipoFiltroRecebimento tipoFiltro , Object...valorFiltro)throws Exception{
		List<Recebimento> lista = null;
		
		if(!(valorFiltro.length > 0)){
			throw new NegocioException("Informe o Valor do Filtro!");
		}
		
		try {			
			if(tipoFiltro.equals(TipoFiltroRecebimento.CODIGO)){	
				try {
					Integer.parseInt((String) valorFiltro[0]);
					lista = dao.find(RecebimentoDao.FILTRAR_POR_CODIGO, valorFiltro);
				} catch (NumberFormatException e) {
					throw new NegocioException("Informe um valor numérico para o filtro por Número de Recebimento!");
				}				
			}		
			
			else if(tipoFiltro.equals(TipoFiltroRecebimento.FORMA_PAGAMENTO)){
				if(valorFiltro[0] == null){
					lista = dao.find(RecebimentoDao.FILTRAR_POR_FORMA_PAGAMENTO_NULL);
				}else{
					lista = dao.find(RecebimentoDao.FILTRAR_POR_FORMA_PAGAMENTO,valorFiltro);
				}				
			}			
			
			else if(tipoFiltro.equals(TipoFiltroRecebimento.LOCAL_RECEBIMENTO)){
				if(valorFiltro[0] == null){
					throw new NegocioException("Informe um Local de Recebimento para Filtrar!");
				}
				lista = dao.find(RecebimentoDao.FILTRAR_POR_BANCO, valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroRecebimento.DATA_EMISSAO)){
				lista = dao.find(valorFiltro.length == 1 ? 
							RecebimentoDao.FILTRO_POR_DATA_ENTRADA : RecebimentoDao.FILTRO_POR_DATA_ENTRADA_BEETWEN, valorFiltro);							
			}
			
			return lista;			
			
		} catch (PersistenceException e) {
			throw new PersistenciaException(
					"Ocorreu algum exceção ao Filtrar os dados do Recebimento!\nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	@Override
	public void consisteAntesEditar(Recebimento entidade)throws NegocioException {
		if (! entidade.getOrigemRecebimento().equals(OrigemRecebimento.ENTRADA_MANUAL)){
			throw new NegocioException("Edição permitida apenas para lançamentos do tipo Entrada Manual!");
		}
	}
	
	
	public Recebimento buscarPorId(Integer id)throws Exception{
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
