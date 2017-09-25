package br.com.buch.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.buch.core.dao.HospedagemDao;
import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Recebimento;
import br.com.buch.core.entity.Recebimento.OrigemRecebimento;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.enumerated.TipoFiltroHospedagem;
import br.com.buch.core.util.CodeUtils;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;


public class ServiceHospedagem implements GenericService<Hospedagem> {

	private static final String CARREGAR_ENTIDADE= "Select h From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.reserva"
					+ " LEFT JOIN FETCH h.apartamento LEFT JOIN FETCH h.lancamentos where h.idHospedagem = ?";
	
	private static final String BUSCAR_TODOS = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.dataEntrada Between ? and ? order by h.dataEntrada";

	private static final String BUSCAR_TODOS_ATIVAS = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.situacao = ? order by h.dataEntrada";
	
	private static final String FILTRO_POR_CODIGO = "From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.codigo = ?";

	private static final String FILTRO_POR_SITUACAO = "From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.situacao = ?";

	private static final String FILTRO_POR_NOME_HOSPEDE = "From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where lower(h.hospede.nome) like ?";

	private static final String FILTRO_POR_CPF_HOSPEDE = "From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.hospede.cpf = ?";
	
	private static final String FILTRO_POR_DATA_ENTRADA = "From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.dataEntrada = ?";
			
	private static final String FILTRO_POR_DATA_ENTRADA_BEETWEN = "From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.dataEntrada Between ? and ?";
	
	
	
	private HospedagemDao dao;
		
	public ServiceHospedagem() {
		dao = new HospedagemDao();
	}
	
	
	@Override
	public String salvar(Hospedagem entidade) throws Exception {
		if(entidade.getIdHospedagem() == null){			
			try {
				
				String data = CodeUtils.getDataFormatada("yyyyMM", new Date());
				
				String codigo = String.valueOf(dao.getCodigoHospedagem());				
				entidade.setCodigo(data+codigo);
				
				dao.save(entidade);
				
				if(entidade.getReserva()!= null){
					Reserva reserva = entidade.getReserva();
					reserva.setSituacao(SituacaoHospedagem.UTILIZADA);
					new ServiceReserva().salvar(reserva);
				}
				
				return "Hospedagem inserida com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Hospedagem!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}				
		}else{			
			try {
				dao.update(entidade);
				return "Hospedagem Alterada com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar a Hospedagem!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
		}	
	}
	
	
	@Override
	public String excluir(Hospedagem entidade) throws Exception {
		try {
			dao.delete(entidade);	
			return "Hospedagem Excluida com Sucesso!";
		}		
		catch (Exception ex) {
        	ex.printStackTrace();
        	if(ex.getCause().toString().contains("ConstraintViolationException")){
        		throw new PersistenciaException("Hospedagem não pode ser excluida pois existem registros vinculados a ela!");
        	}else{
        		throw new PersistenciaException("Ocorreu uma exceção ao Excluir a Hospedagem!" + 
                		" \nErro: " + UtilErros.getMensagemErro(ex));
        	}
		}
	}
	
	
	@Override
	public Hospedagem carregarEntidade(Hospedagem entidade) throws PersistenciaException {
		try{
			return dao.findOne(CARREGAR_ENTIDADE, entidade.getIdHospedagem());			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Hospedagem!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}
	
	
	public Hospedagem carregarEntidade(Integer idHospedagem) throws PersistenciaException {
		try{
			return dao.findOne(CARREGAR_ENTIDADE, idHospedagem);			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Hospedagem!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public List<Hospedagem> buscarTodos() throws PersistenciaException {
		Calendar c1 = Calendar.getInstance();
	    Calendar c2 = Calendar.getInstance();
	    
	    //Dentro do mês Corrente
  		//c1.set(Calendar.DAY_OF_MONTH, c1.getMinimum(Calendar.DAY_OF_MONTH));
        //c2.set(Calendar.DAY_OF_MONTH, c1.getMaximum(Calendar.DAY_OF_MONTH));
  	    
  	    //30 dias para trás e 30 dias para frente 
  	    c1.add(Calendar.DAY_OF_MONTH, -30);
  	    c2.add(Calendar.DAY_OF_MONTH, 30);
        
        Date d1 = c1.getTime();
        Date d2 = c2.getTime();
        
		try {
			return dao.find(BUSCAR_TODOS, d1, d2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Hospedagem!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public void consisteAntesEditar(Hospedagem entidade) throws NegocioException {
	}
	
	
	public List<Hospedagem> filtrarTabela(TipoFiltroHospedagem tipoFiltro , Object...valorFiltro)throws Exception{
		List<Hospedagem> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltroHospedagem.CODIGO)){	
				if(valorFiltro[0] == null || valorFiltro[0].equals("")){ throw new NegocioException("Informe um código para Filtrar!");}
				lista = dao.find(FILTRO_POR_CODIGO, valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroHospedagem.CPF_HOSPEDE)){
				lista = dao.find(FILTRO_POR_CPF_HOSPEDE,String.valueOf(valorFiltro[0]).replace("-","").replace(".", ""));
			}
			
			else if(tipoFiltro.equals(TipoFiltroHospedagem.NOME_HOSPEDE)){				
				lista = dao.find(FILTRO_POR_NOME_HOSPEDE,
						valorFiltro[0].equals("") ? valorFiltro[0] : String.valueOf(valorFiltro[0]).toLowerCase());
			}
			
			else if(tipoFiltro.equals(TipoFiltroHospedagem.SITUACAO)){
				if(valorFiltro[0] == null){throw new NegocioException("Informe uma situação para Filtrar!");}
				lista = dao.find(FILTRO_POR_SITUACAO,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltroHospedagem.DATA_ENTRADA)){
				lista = dao.find(valorFiltro.length == 1 ? FILTRO_POR_DATA_ENTRADA : FILTRO_POR_DATA_ENTRADA_BEETWEN, valorFiltro);		
			}
			
			return lista;			
		} catch (PersistenceException e) {
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados da Hospedagem!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	public void confirmarCheckOut(Hospedagem hospedagem, Recebimento recebimento, List<Adiantamento> adiantamentos)throws Exception{
		recebimento.setDescricao("Hospedagem Nº "+hospedagem.getCodigo()+" / " + hospedagem.getHospede().getNome());
		recebimento.setDtEmissao(new Date());
		recebimento.setOrigemRecebimento(OrigemRecebimento.HOSPEDAGEM);
		recebimento.setValor(hospedagem.getTotalLancamentos());
		
		hospedagem.setSituacao(SituacaoHospedagem.CHECKOUT);
		
		dao.confirmarCheckOut(hospedagem, recebimento, adiantamentos);
	}

	
	public List<Hospedagem> getHospedagensAtivas()throws PersistenciaException{
		try {
			return dao.find(BUSCAR_TODOS_ATIVAS, SituacaoHospedagem.CHECKIN);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Hospedagem!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}
}
