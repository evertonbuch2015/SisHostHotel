package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.CategoriaDao;
import br.com.buch.core.entity.Categoria;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.Constantes.ConstantesLista;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.CategoriaBean.TipoFiltro;

public class ServiceCategoria implements GenericService<Categoria> {
	
	private CategoriaDao categoriaDao; 
	
	public ServiceCategoria() {
		this.categoriaDao = new CategoriaDao();
	}
	
	
	
	@Override
	public String salvar(Categoria entidate)throws Exception {
		
		if(entidate.getIdCategoria() == null){
			try {
				categoriaDao.save(entidate);
				Constantes.getInstance().refresh(ConstantesLista.CATEGORIAS);
				return "Categoria de Apartamento Cadastrada com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Categoria!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				categoriaDao.update(entidate);
				Constantes.getInstance().refresh(ConstantesLista.CATEGORIAS);
				return "Categoria de Apartamento Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Categoria!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}	
	}

	
	@Override
	public String excluir(Categoria entidade) throws Exception{
		try {
			categoriaDao.delete(entidade);
			Constantes.getInstance().refresh(ConstantesLista.CATEGORIAS);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir a Categoria!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
		
	}

	
	@Override
	public Categoria carregarEntidade(Categoria entidade)throws PersistenciaException {
		try{
			String jpql = "Select c From Categoria c where c.idCategoria = ?1";
			return categoriaDao.findOne(jpql, entidade.getIdCategoria());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Categoria!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Categoria> buscarTodos()throws PersistenciaException {
		try {
			return Constantes.getInstance().getListaCategorias();
			//return categoriaDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

		
	public List<Categoria> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Categoria> lista = null;
		
		try {			
			if(tipoFiltro.equals(TipoFiltro.NOME)){
				lista = categoriaDao.find("Select c From Categoria c where c.nome like ?",valorFiltro);
			}											
			return lista;			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu algum exceção ao Filtrar os dados da Categoria!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	@Override
	public void consisteAntesEditar(Categoria entidade) {

	}
	
	
	public Categoria buscarPorId(Integer id)throws Exception{
		try {
			return categoriaDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
