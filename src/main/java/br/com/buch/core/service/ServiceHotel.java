package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.HotelDao;
import br.com.buch.core.entity.Hotel;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.HotelBean.TipoFiltro;

public class ServiceHotel implements GenericService<Hotel> {

	private HotelDao empresaDao;
	
	
	public ServiceHotel() {
		empresaDao = new HotelDao();
	}
	
	
	@Override
	public String salvar(Hotel entidate)throws Exception {
		if (entidate.getIdHotel() == null) {
			
			try {
				empresaDao.save(entidate);
				return "Cadastro de Hotel Realizado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Hotel!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
			
		} else {
			
			try {
				empresaDao.update(entidate);
				return "Cadastro de Hotel Alterado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar o Hotel!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
		
	}

	
	@Override
	public String excluir(Hotel entidade)throws Exception {
		try {
			empresaDao.delete(entidade);
			return "";
        }catch (Exception ex) {
        	ex.printStackTrace();            
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Hotel!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
		
	}

	
	@Override
	public Hotel carregarEntidade(Hotel hotel) throws PersistenciaException{
		String jpql = "Select e From Hotel e where e.id = ?1";
		try {
			return empresaDao.findOne(jpql, hotel.getIdHotel());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Hotel!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	@Override
	public List<Hotel> buscarTodos() {
		try {
			return empresaDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<Hotel> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Hotel> lista = null;
		
		try {

			if(tipoFiltro.equals(TipoFiltro.CODIGO)){						
				String jpql = "Select e From Hotel e where e.codigo in (" + valorFiltro + ")";
				lista = empresaDao.find(jpql);
					
			}
			else if(tipoFiltro.equals(TipoFiltro.NOME)){	
				
					lista = empresaDao.find("Select e From Hotel e where e.nomeRazao like ?",valorFiltro);
				
			}
			
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Hotel!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}


	
	
	@Override
	public void consisteAntesEditar(Hotel entidade) throws NegocioException{

	}

}
