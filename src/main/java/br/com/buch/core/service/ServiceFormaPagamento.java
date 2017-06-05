package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.FormaPagamentoDao;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.view.managedBean.FormaPagamentoBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceFormaPagamento implements GenericService<FormaPagamento> {

	private FormaPagamentoDao formaPagamentoDao;

	
	public ServiceFormaPagamento() {
		this.formaPagamentoDao = new FormaPagamentoDao();
	}

	
	@Override
	public boolean salvar(FormaPagamento entidate) {
		if (entidate.getIdFormaPag() == null) {

			try {
				formaPagamentoDao.save(entidate);
				UtilMensagens.mensagemInformacao("Forma de Pagamento Cadastrada com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro(
						"Erro ao Inserir a Forma de Pagamento!" + "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}
		} else {

			try {
				formaPagamentoDao.update(entidate);
				UtilMensagens.mensagemInformacao("Forma de Pagamento Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro(
						"Erro ao Alterar a Forma de Pagamento!" + "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}
		}
	}

	
	@Override
	public void excluir(FormaPagamento entidade) {
		try {
			formaPagamentoDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Ocorreu algum excessão ao Excluir a Forma de Pagamento!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public FormaPagamento carregarEntidade(FormaPagamento entidade) {
		try{
			String jpql = "Select f From FormaPagamento f where c.idFormaCategoria = ?1";
			return formaPagamentoDao.findOne(jpql, entidade.getIdFormaPag());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu uma excessão ao buscar os dados da Forma de Pagamento!");
			return null;
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

	
	public List<FormaPagamento> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
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
			UtilMensagens.mensagemAtencao("Ocorreu algum excessão ao Filtrar os dados da Forma de Pagamento!");
			return null;
		}					
	}
}
