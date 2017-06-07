package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.CrpAdiantamentoDao;
import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.enumerated.TipoFiltroAdiantamento;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class ServiceAdiantamento implements GenericService<Adiantamento> {

	private CrpAdiantamentoDao adiantamentoDao;
	
	public ServiceAdiantamento() {
		adiantamentoDao = new CrpAdiantamentoDao();
	}

	
	
	
	@Override
	public boolean salvar(Adiantamento entidate) {
		if(entidate.getIdAdiantamento() == null){
			
			try {
				adiantamentoDao.save(entidate);
				UtilMensagens.mensagemInformacao("Adiantamento a Cliente Cadastrado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Inserir o Adiantamento a Cliente!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}				
		}else{
			
			try {
				adiantamentoDao.update(entidate);
				UtilMensagens.mensagemInformacao("Adiantamento a Cliente Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				UtilMensagens.mensagemErro("Erro ao Alterar o Adiantamento a Cliente!"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;	
			}
		}	
	}
	
	

	@Override
	public void excluir(Adiantamento entidade) {
		try {
			adiantamentoDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (Exception ex) {
        	ex.printStackTrace();
            UtilMensagens.mensagemErro("Ocorreu uma excessão ao Excluir o Adiantamento a Cliente!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}
	
	

	@Override
	public Adiantamento carregarEntidade(Adiantamento entidade) {
		try{
			String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.idAdiantamento = ?1";
			return adiantamentoDao.findOne(jpql, entidade.getIdAdiantamento());
			
		}catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemErro("Ocorreu uma excessão ao buscar os dados do Adiantamento a Cliente!");
			return null;
		}
	}
	
	

	@Override
	public List<Adiantamento> buscarTodos() {
		try {
			return adiantamentoDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Adiantamento> filtrarTabela(TipoFiltroAdiantamento tipoFiltro , String valorFiltro){
		List<Adiantamento> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltroAdiantamento.CODIGO)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.codigo in (" + valorFiltro + ")";
				lista = adiantamentoDao.find(jpql);
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_CODIGO)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.hospede.codigo = ?";
				lista = adiantamentoDao.find(jpql, valorFiltro);
			}	
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_NOME)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.hospede.nome like ?";
				lista = adiantamentoDao.find(jpql,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.HOSPEDE_CPF)){
				String jpql = "Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.hospede.cpf = ?";
				lista = adiantamentoDao.find(jpql,valorFiltro.replace(".", "").replace("-", ""));
			}
			
			else if(tipoFiltro.equals(TipoFiltroAdiantamento.DATA_EMISSAO)){			
				
				try{
					String jpql = " Select a From Adiantamento a LEFT JOIN FETCH a.hospede "
								+ " where a.dtEmissao = ?1 ";
					
					lista = adiantamentoDao.find(jpql,valorFiltro);
				}catch (Exception e) {
					e.printStackTrace();
				}								
			}
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Ocorreu algum excessão ao Filtrar os dados do Hóspede!");
			return null;
		}					
	}

}
