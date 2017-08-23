package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.EmpresaDao;
import br.com.buch.core.entity.Empresa;
import br.com.buch.core.entity.Endereco;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.core.util.WebServiceCep;
import br.com.buch.view.managedBean.EmpresaBean.TipoFiltro;

public class ServiceEmpresa implements GenericService<Empresa> {

	private EmpresaDao dao;
	
	private static final String BUSCAR_TODAS = "Select e From Empresa e order by e.nomeFantasia";
	
	public ServiceEmpresa() {
		dao = new EmpresaDao();
	}
	
	
	@Override
	public String salvar(Empresa entidate)throws Exception {
		if (entidate.getIdEmpresa() == null) {
			
			try {
				
				entidate.setCodigo(dao.getMaxField("codigo"));
				dao.save(entidate);
				return "Cadastro de Empresa Realizado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Empresa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
			
		} else {
			
			try {
				dao.update(entidate);
				return "Cadastro de Empresa Alterado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar a Empresa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
		
	}

	
	@Override
	public String excluir(Empresa entidade)throws Exception {
		try {
			dao.delete(entidade);
			return "";
        }catch (Exception ex) {
        	ex.printStackTrace();            
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir a Empresa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
		
	}

	
	@Override
	public Empresa carregarEntidade(Empresa Empresa) throws PersistenciaException{
		String jpql = "Select e From Empresa e left JOIN FETCH e.endereco where e.idEmpresa = ?1";
		try {
			return dao.findOne(jpql, Empresa.getIdEmpresa());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Empresa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	@Override
	public List<Empresa> buscarTodos() {
		try {
			return dao.find(BUSCAR_TODAS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<Empresa> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Empresa> lista = null;
		
		try {

			if(tipoFiltro.equals(TipoFiltro.CODIGO)){						
				String jpql = "Select e From Empresa e where e.codigo in (" + valorFiltro + ")";
				lista = dao.find(jpql);					
			}
			else if(tipoFiltro.equals(TipoFiltro.NOME)){					
				lista = dao.find("Select e From Empresa e where e.nomeRazao like ?",valorFiltro);				
			}
			
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Empresa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}


	
	
	@Override
	public void consisteAntesEditar(Empresa entidade) throws NegocioException{

	}
	
	
	public Endereco consultaCepWebService(String cep){
		Endereco endereco = null;
		
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);       
        if (webServiceCep.wasSuccessful()) {
            endereco = new Endereco();
        	endereco.setLogradouro(webServiceCep.getLogradouroType());
        	endereco.setRua(webServiceCep.getLogradouro());
        	endereco.setCidade(webServiceCep.getCidade());
        	endereco.setBairro(webServiceCep.getBairro());
        	endereco.setEstado(webServiceCep.getUf());
        	endereco.setPais("Brasil");
        }
        
        return endereco;
	}

}
