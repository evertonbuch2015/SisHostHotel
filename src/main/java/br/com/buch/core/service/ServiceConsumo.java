package br.com.buch.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.ConsumoDao;
import br.com.buch.core.entity.Consumo;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.ConsumoBean.TipoFiltro;


public class ServiceConsumo implements GenericService<Consumo> {

	private static final String BUSCAR_TODAS = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.dataConsumo Between ? and ? order by c.dataConsumo";

	private static final String BUSCAR_POR_HOSPEDAGEM = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.hospedagem.codigo = ?";

	private static final String CARREGAR_ENTIDADE = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.idConsumo = ?1";
	
	private ConsumoDao dao;
	
	
	public ServiceConsumo() {
		this.dao = new ConsumoDao();
	}	
	
	
	@Override
	public String salvar(Consumo entidate)throws Exception {
		if(entidate.getIdConsumo() == null){
			
			try {
				dao.save(entidate);
				return "Consumo Inserido com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Consumo!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				dao.update(entidate);
				return "Consumo Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao Alterar o Consumo!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}
	}

	
	@Override
	public String excluir(Consumo entidade)throws Exception {
		if((entidade.getHospedagem() != null) && 
				(! entidade.getHospedagem().getSituacao().equals(SituacaoHospedagem.CHECKIN))){
			throw new NegocioException("Consumo não pode ser Excluido, pois está vinculado a uma Hospedagem com Status de Check-Out");
		}
		
		try {
			dao.delete(entidade);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
            throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Consumo!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public Consumo carregarEntidade(Consumo entidade)throws PersistenciaException {
		try{
			return dao.findOne(CARREGAR_ENTIDADE, entidade.getIdConsumo());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Apartamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Consumo> buscarTodos()throws PersistenciaException {
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
			return dao.find(BUSCAR_TODAS, d1, d2);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	
	public List<Consumo> filtrarTabela(TipoFiltro tipoFiltro , Object...valorFiltro)throws Exception{
		List<Consumo> lista = null;
		
		try {			
			if(tipoFiltro.equals(TipoFiltro.HOSPEDAGEM)){				
				lista = dao.find(BUSCAR_POR_HOSPEDAGEM,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltro.DATA)){
				String jpql;
				if (valorFiltro.length == 1){
					jpql = "Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.dataConsumo = ?";
				}else{
					jpql = "Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.dataConsumo Between ? and ?";
				}	
				lista = dao.find(jpql, valorFiltro);
			}
			
			return lista;			
		} catch (PersistenceException e) {
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Consumo!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	@Override
	public void consisteAntesEditar(Consumo entidade)throws NegocioException {
		if((entidade.getHospedagem() != null) && 
				(! entidade.getHospedagem().getSituacao().equals(SituacaoHospedagem.CHECKIN))){
			throw new NegocioException("Consumo não pode ser alterado, pois está vinculado a uma Hospedagem com Status de Check-Out");
		}
	}

	
	public Consumo buscarPorId(Integer id)throws Exception{
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
