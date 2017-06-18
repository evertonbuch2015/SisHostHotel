package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.FormaPagamentoDao;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.FormaPagamentoBean.TipoFiltro;

public class ServiceFormaPagamento implements GenericService<FormaPagamento> {

	private FormaPagamentoDao formaPagamentoDao;

	
	public ServiceFormaPagamento() {
		this.formaPagamentoDao = new FormaPagamentoDao();
	}

	
	@Override
	public String salvar(FormaPagamento entidate)throws Exception {
		if (entidate.getIdFormaPag() == null) {

			try {
				formaPagamentoDao.save(entidate);
				return "Forma de Pagamento Cadastrada com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Forma de Pagamento!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		} else {

			try {
				formaPagamentoDao.update(entidate);
				return "Forma de Pagamento Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar a Forma de Pagamento!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}
	}

	
	@Override
	public String excluir(FormaPagamento entidade) throws Exception{
		try {
			formaPagamentoDao.delete(entidade);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao excluir a Forma de Pagamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public FormaPagamento carregarEntidade(FormaPagamento entidade) throws PersistenciaException {
		try{
			String jpql = "Select f From FormaPagamento f where f.idFormaPag = ?1";
			return formaPagamentoDao.findOne(jpql, entidade.getIdFormaPag());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Forma de Pagamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<FormaPagamento> buscarTodos() {
		try {
			return formaPagamentoDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<FormaPagamento> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<FormaPagamento> lista = null;
		
		try {			
			if(tipoFiltro.equals(TipoFiltro.NOME)){
				lista = formaPagamentoDao.find("Select f From FormaPagamento f where f.descricao like ?",valorFiltro);
			}
			else if(tipoFiltro.equals(TipoFiltro.CODIGO)){
				String jpql = "Select f From FormaPagamento f where f.codigo in (" + valorFiltro + ")";
				lista = formaPagamentoDao.find(jpql);			
			}											
			return lista;			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados da Forma de Pagamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}


	
	
	@Override
	public void consisteAntesEditar(FormaPagamento entidade) {

	}
}
