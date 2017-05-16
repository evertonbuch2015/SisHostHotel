package br.com.buch.core.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import br.com.buch.core.dao.HotelDao;
import br.com.buch.core.entity.Hotel;
import br.com.buch.view.managedBean.HotelBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceHotel implements GenericService<Hotel> {

	private HotelDao empresaDao;
	
	
	public ServiceHotel() {
		empresaDao = new HotelDao();
	}
	
	
	@Override
	public boolean salvar(Hotel entidate) {
		if (entidate.getIdHotel() == null) {
			
			try {
				empresaDao.save(entidate);
				UtilMensagens.mensagemInformacao("Cadastro de Hotel Realizado com Sucesso");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Inserir a Hotel"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}
			
		} else {
			
			try {
				empresaDao.update(entidate);
				UtilMensagens.mensagemInformacao("Cadastro de Hotel Alterado com Sucesso");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Atualizar a Hotel"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}			
		}
		
	}

	
	@Override
	public void excluir(Hotel entidade) {
		try {
			empresaDao.delete(entidade);
			
		}catch (EntityNotFoundException enfe) { 
			enfe.printStackTrace();
            UtilMensagens.mensagemErro("The Item with id " + entidade.getIdHotel()+ " no longer exists." +
            		" \nErro: " + UtilErros.getMensagemErro(enfe));
        }catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Erro ao excluir a Hotel!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
		
	}

	
	@Override
	public Hotel carregarEntidade(Hotel hotel) {
		String jpql = "Select e From Hotel e where e.id = ?1";
		try {
			return empresaDao.findOne(jpql, hotel.getIdHotel());
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
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

	
	public List<Hotel> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Hotel> lista = null;
		
		if(tipoFiltro.equals(TipoFiltro.CODIGO)){			
			try {
				String jpql = "Select e From Hotel e where e.codigo in (" + valorFiltro + ")";
				lista = empresaDao.find(jpql);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		else if(tipoFiltro.equals(TipoFiltro.NOME)){	
			try {
				lista = empresaDao.find("Select e From Hotel e where e.nomeRazao like ?",valorFiltro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;	
	}

}
