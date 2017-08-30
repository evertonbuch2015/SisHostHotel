package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.HospedeDao;
import br.com.buch.core.entity.Endereco;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.TipoFiltroHospede;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.core.util.WebServiceCep;


public class ServiceHospede implements GenericService<Hospede> {

	private static final String BUSCAR_TODAS = 
			"Select h From Hospede h LEFT JOIN FETCH h.endereco"; 
			
	private static final String CARREGAR_ENTIDADE =
			"Select h From Hospede h LEFT JOIN FETCH h.endereco LEFT JOIN FETCH h.empresa where h.idHospede = ?1"; 
	
	private HospedeDao hospedeDao;
	
	
	public ServiceHospede() {
		this.hospedeDao = new HospedeDao();
	}
	
	
	
	@Override
	public String salvar(Hospede hospede) throws Exception{
		if(hospede.getIdHospede() == null){
			
			try {
				hospedeDao.save(hospede);
				return "Hóspede Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Hospede!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{
			
			try {
				hospedeDao.update(hospede);
				return "Hóspede Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar o Hospede!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}			
	}

	
	@Override
	public String excluir(Hospede entidade) throws Exception{
		try {
			hospedeDao.delete(entidade);
			return "";
		}		
		catch (Exception ex) {
        	ex.printStackTrace();
        	if(ex.getCause().toString().contains("ConstraintViolationException")){
        		throw new PersistenciaException("Hóspede não pode ser excluido pois existem registros vinculados a ele!");
        	}else{
        		throw new PersistenciaException("Ocorreu uma exceção ao Excluir o Hóspede!" + 
                		" \nErro: " + UtilErros.getMensagemErro(ex));
        	}
		}
	}

	
	@Override
	public Hospede carregarEntidade(Hospede entidade)throws PersistenciaException {		
		try{
			return hospedeDao.findOne(CARREGAR_ENTIDADE, entidade.getIdHospede());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Hóspede!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Hospede> buscarTodos() {
		try {
			return hospedeDao.find(BUSCAR_TODAS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<Hospede> filtrarTabela(TipoFiltroHospede tipoFiltro , String valorFiltro)throws Exception{
		List<Hospede> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltroHospede.CODIGO)){
				String jpql = "Select h From Hospede h where h.codigo in (" + valorFiltro + ")";
				lista = hospedeDao.find(jpql);
			}
			else if(tipoFiltro.equals(TipoFiltroHospede.NOME)){
				lista = hospedeDao.find("Select h From Hospede h where h.nome like ?",valorFiltro);
			}	
			else if(tipoFiltro.equals(TipoFiltroHospede.CPF)){
				lista = hospedeDao.find("Select h From Hospede h where h.cpf like ?",valorFiltro);
			}
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Hóspede!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	@Override
	public void consisteAntesEditar(Hospede entidade)throws NegocioException {
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

	
	public List<Hospede> buscarPorNome(String nome) throws Exception{
		return hospedeDao.find("Select h From Hospede h where h.nome like ?", nome);
	}
}
