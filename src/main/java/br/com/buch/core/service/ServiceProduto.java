package br.com.buch.core.service;

import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.ProdutoDao;
import br.com.buch.core.entity.Produto;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.ProdutoBean.TipoFiltro;

public class ServiceProduto implements GenericService<Produto> {
	
	private ProdutoDao dao; 
	
	
	public ServiceProduto() {
		this.dao = new ProdutoDao();
	}
	
	
	@Override
	public String salvar(Produto entidate)throws Exception {
		
		if(entidate.getIdProduto() == null){
			try {
				dao.save(entidate);
				return "Produto Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Produto!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				dao.update(entidate);
				return "Produto Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar o Produto!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}	
	}

	
	@Override
	public String excluir(Produto entidade) throws Exception{
		try {
			dao.delete(entidade);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Produto!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
		
	}

	
	@Override
	public Produto carregarEntidade(Produto entidade)throws PersistenciaException {
		try{			
			return dao.findOne(ProdutoDao.CARREGAR_ENTIDADE, entidade.getIdProduto());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Produto!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Produto> buscarTodos()throws PersistenciaException {
		try {
			return dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

		
	public List<Produto> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Produto> lista = null;		
		
		try {			
			lista = tipoFiltro.equals(TipoFiltro.CODIGO)?
						dao.find(ProdutoDao.BUSCAR_POR_CODIGO,valorFiltro):
						dao.find(ProdutoDao.BUSCAR_POR_NOME,valorFiltro);
						
			return lista;
		} catch (PersistenceException e) {
			throw new PersistenciaException("Ocorreu algum exceção ao Filtrar os dados do Produto!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	@Override
	public void consisteAntesEditar(Produto entidade) {

	}
	
	
	public Produto buscarPorId(Integer id)throws Exception{
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	public List<Produto> buscarPorNome(String string)throws PersistenciaException{
		List<Produto> lista = null;		
		try {
			lista = dao.find(ProdutoDao.BUSCAR_POR_NOME, string != null?string.toLowerCase():string);
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu algum exceção ao Filtrar os dados do Produto!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}	
	}
}
