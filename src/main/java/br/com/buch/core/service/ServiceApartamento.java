package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.ApartamentoDao;
import br.com.buch.core.entity.Apartamento;
import br.com.buch.view.managedBean.ApartamentoBean.TipoFiltro;
import br.com.buch.view.util.UtilMensagens;

public class ServiceApartamento implements GenericService<Apartamento> {

	private ApartamentoDao apartamentoDao;
	
	
	public ServiceApartamento() {
		this.apartamentoDao = new ApartamentoDao();
	}	
	
	
	@Override
	public boolean salvar(Apartamento entidate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void excluir(Apartamento entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Apartamento carregarEntidade(Apartamento entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Apartamento> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Apartamento> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Apartamento> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){
				String jpql = "Select a From Apartamento a where a.codigo in (" + valorFiltro + ")";
				lista = apartamentoDao.find(jpql);
			}			
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Ocorreu algum excessão ao Filtrar os dados do Apartamento!");
			return null;
		}					
	}
}
