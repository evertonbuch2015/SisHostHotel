package br.com.buch.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.ReservaDao;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.enumerated.TipoFiltroReserva;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;

public class ServiceReserva implements GenericService<Reserva> {

	private static final String BUSCAR_RESERVAS_VENCIDAS = 
			"Select r From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento"
			+ " where r.dataEntrada <= ?1 and r.situacao in (?2,?3) order by r.dataEntrada";

	private static final String FILTRO_POR_CODIGO = "From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.codigo = ?";

	private static final String FILTRO_POR_SITUACAO = "From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.situacao = ?";

	private static final String FILTRO_POR_NOME_HOSPEDE = "From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where lower(r.hospede.nome) like ?";

	private static final String FILTRO_POR_CPF_HOSPEDE = "From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.hospede.cpf = ?";
	
	private static final String FILTRO_POR_DATA_ENTRADA = "From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.dataEntrada = ?";
			
	private static final String FILTRO_POR_DATA_ENTRADA_BEETWEN = "From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.dataEntrada Between ? and ?";
	
	private ReservaDao dao;
	
	
	public ServiceReserva() {
		dao = new ReservaDao();
	}

			
	@Override
	public String salvar(Reserva entidade) throws Exception {
		if(entidade.getIdReserva() == null){
			
			try {
				dao.save(entidade);
				return "Reserva inserida com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Reserva!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				dao.update(entidade);
				return "Reserva Alterada com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar a Reserva!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}	
	}

	
	@Override
	public String excluir(Reserva entidade) throws Exception {
		if(entidade.getSituacao() == SituacaoHospedagem.CANCELADA){
			throw new NegocioException("Reserva já está Cancelada no Sistema, não pode ser Excluida!");
		}
		
		if(entidade.getSituacao() == SituacaoHospedagem.CONFIRMADA){
			throw new NegocioException("Reserva já está Confirmada no Sistema, não pode ser Excluida.Você deve Cancelar a mesma.");
		}
		
		try {
			dao.delete(entidade);	
			return "Reserva Excluida com Sucesso!";
		}		
		catch (Exception ex) {
        	ex.printStackTrace();
        	if(ex.getCause().toString().contains("ConstraintViolationException")){
        		throw new PersistenciaException("Reserva não pode ser excluida pois existem registros vinculados a ela!");
        	}else{
        		throw new PersistenciaException("Ocorreu uma exceção ao Excluir a Reserva!" + 
                		" \nErro: " + UtilErros.getMensagemErro(ex));
        	}
		}
	}

	
	@Override
	public Reserva carregarEntidade(Reserva entidade) throws PersistenciaException {
		try{
			String jpql = "Select r From Reserva r LEFT JOIN FETCH r.hospede"
						+ " LEFT JOIN FETCH r.apartamento where r.idReserva = ?1";
			return dao.findOne(jpql, entidade.getIdReserva());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Reserva!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}
	
	
	public Reserva carregarEntidade(Integer id) throws PersistenciaException {
		try{
			String jpql = "Select r From Reserva r LEFT JOIN FETCH r.hospede"
						+ " LEFT JOIN FETCH r.apartamento where r.idReserva = ?1";
			return dao.findOne(jpql, id);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Reserva!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Reserva> buscarTodos() throws PersistenciaException {
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
			return dao.find("From Reserva r where r.dataEntrada Between ?1 and ?2 order by r.dataEntrada",d1,d2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Reserva!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public void consisteAntesEditar(Reserva entidade) throws NegocioException {		
		if(entidade.getSituacao() == SituacaoHospedagem.CANCELADA ){
			throw new NegocioException("Reserva já está Cancelada no Sistema, não pode ser Alterada!");
		}
	}

	
	public Reserva buscarPorId(Integer id)throws Exception{
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public void cancelarReserva(Reserva reserva) throws Exception{
		
		if(reserva.getSituacao() == SituacaoHospedagem.CANCELADA ){
			throw new NegocioException("Reserva já esta Cancelada no Sistema!");
		}
		
		if(reserva.getSituacao() == SituacaoHospedagem.UTILIZADA){
			throw new NegocioException("Reserva já foi utilizada em uma Hospedagem!");
		}
				
		reserva.setSituacao(SituacaoHospedagem.CANCELADA);
		reserva.setDataCancelamento(new Date());
		
		dao.update(reserva);
	}
	
	
	public void confirmarReserva(Reserva reserva) throws Exception{
		
		if(reserva.getSituacao() == SituacaoHospedagem.CANCELADA ){
			throw new NegocioException("Reserva já esta cancelada no sistema!");
		}
		
		if(reserva.getSituacao() == SituacaoHospedagem.UTILIZADA){
			throw new NegocioException("Reserva já foi utilizada em uma Hospedagem!");
		}
		
		if(reserva.getSituacao() == SituacaoHospedagem.CONFIRMADA){
			throw new NegocioException("Reserva já foi confirmada!");
		}
		
		if (reserva.getDataEntrada().before(new Date())) {
			throw new NegocioException("Data de entrada da Reserva é menor que data atual!");
		}
				
		reserva.setSituacao(SituacaoHospedagem.CONFIRMADA);
		reserva.setDataConfirmacao(new Date());
		
		dao.update(reserva);
	}

	
	public List<Reserva> buscarReservasVencidas()throws Exception{        
		try {
			return dao.find(BUSCAR_RESERVAS_VENCIDAS,new Date(),SituacaoHospedagem.CONFIRMADA,SituacaoHospedagem.NAO_CONFIRMADA);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Reserva!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	public List<Reserva> filtrarTabela(TipoFiltroReserva tipoFiltro , Object...valorFiltro)throws Exception{
		List<Reserva> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltroReserva.CODIGO)){	
				if(valorFiltro[0] == null || valorFiltro[0].equals("")){ throw new NegocioException("Informe um código para Filtrar!");}
				lista = dao.find(FILTRO_POR_CODIGO, valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroReserva.CPF_HOSPEDE)){
				lista = dao.find(FILTRO_POR_CPF_HOSPEDE,String.valueOf(valorFiltro[0]).replace("-","").replace(".", ""));
			}
			
			else if(tipoFiltro.equals(TipoFiltroReserva.NOME_HOSPEDE)){
				lista = dao.find(FILTRO_POR_NOME_HOSPEDE,
						valorFiltro[0].equals("") ? valorFiltro[0] : String.valueOf(valorFiltro[0]).toLowerCase());
			}
			
			else if(tipoFiltro.equals(TipoFiltroReserva.SITUACAO)){
				if(valorFiltro[0] == null){throw new NegocioException("Informe uma situação para Filtrar!");}
				lista = dao.find(FILTRO_POR_SITUACAO,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroReserva.DATA_ENTRADA)){
				lista = dao.find(valorFiltro.length == 1 ? FILTRO_POR_DATA_ENTRADA : FILTRO_POR_DATA_ENTRADA_BEETWEN, valorFiltro);
			}
			
			return lista;			
		} catch (PersistenceException e) {
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados da Reserva!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}
}
