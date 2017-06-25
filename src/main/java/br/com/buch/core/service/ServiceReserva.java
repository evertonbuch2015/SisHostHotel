package br.com.buch.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.buch.core.dao.ReservaDao;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;

public class ServiceReserva implements GenericService<Reserva> {

	private ReservaDao reservaDao;
	
	public ServiceReserva() {
		reservaDao = new ReservaDao();
	}

	
	@Override
	public String salvar(Reserva entidade) throws Exception {
		if(entidade.getIdReserva() == null){
			
			try {
				reservaDao.save(entidade);
				return "Reserva inserida com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Reserva!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				reservaDao.update(entidade);
				return "Reserva Alterado com Sucesso!";
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
			reservaDao.delete(entidade);	
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
			return reservaDao.findOne(jpql, entidade.getIdReserva());
			
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
		c1.set(Calendar.DAY_OF_MONTH, c1.getMinimum(Calendar.DAY_OF_MONTH));
        c2.set(Calendar.DAY_OF_MONTH, c1.getMaximum(Calendar.DAY_OF_MONTH));
        
        Date d1 = c1.getTime();
        Date d2 = c2.getTime();
        
		try {
			return reservaDao.find("From Reserva r where r.dataEntrada Between ?1 and ?2",d1,d2);
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
			return reservaDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	
	
	public void cancelarReserva(Reserva reserva) throws Exception{
		
		if(reserva.getSituacao() == SituacaoHospedagem.CANCELADA ){
			throw new NegocioException("Reserva já esta Cancelada no Sistema!");
		}
				
		reserva.setSituacao(SituacaoHospedagem.CANCELADA);
		
		reservaDao.update(reserva);
	}
}
