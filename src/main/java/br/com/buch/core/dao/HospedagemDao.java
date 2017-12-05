package br.com.buch.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Recebimento;

public class HospedagemDao extends GenericDao<Hospedagem> {


	public static final String CARREGAR_ENTIDADE= "Select h From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.reserva"
					+ " LEFT JOIN FETCH h.apartamento LEFT JOIN FETCH h.lancamentos where h.idHospedagem = ?";
	
	public static final String BUSCAR_TODOS = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.dataEntrada Between ? and ? order by h.dataEntrada";

	public static final String BUSCAR_TODOS_ATIVAS = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.situacao = ? order by h.dataEntrada";
	
	public static final String BUSCAR_TODOS_PARA_CHECKOUT = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.situacao = ? and h.dataSaida = ? order by h.dataEntrada";
	
	public static final String FILTRO_POR_CODIGO = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.codigo = ?";

	public static final String FILTRO_POR_SITUACAO = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.situacao = ?";

	public static final String FILTRO_POR_NOME_HOSPEDE = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where lower(h.hospede.nome) like ?";

	public static final String FILTRO_POR_CPF_HOSPEDE = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.hospede.cpf = ?";
	
	public static final String FILTRO_POR_DATA_ENTRADA = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.dataEntrada = ?";
			
	public static final String FILTRO_POR_DATA_ENTRADA_BEETWEN = 
			"From Hospedagem h LEFT JOIN FETCH h.hospede LEFT JOIN FETCH h.apartamento where h.dataEntrada Between ? and ?";
	
	
	
	public HospedagemDao() {
		super(Hospedagem.class);
	}

    
    public Integer getCodigoHospedagem() throws Exception{
    	EntityManager em = getEntityManager();
    	try {    	
    		Query query2 = getEntityManager().createNativeQuery("select gen_id(G_CODIGO_HOSPEDAGEM ,1 )from RDB$DATABASE");
            Integer num = (Integer) Integer.parseInt(query2.getSingleResult().toString());
            
            return num == null ? 1 : num;
		}catch(java.lang.ClassCastException ex){			
			new PersistenceException("Campo passado por parametro deve ser um Integer.", ex);	
			return null;
		}finally {
			em.close();			
		}       
    }

    
    public void confirmarCheckOut(Hospedagem hospedagem, Recebimento recebimento) throws Exception{
		EntityManager entityManager = getEntityManager();
    	
    	try{
    		entityManager.getTransaction().begin();        	        	
        	entityManager.merge(recebimento);
        	entityManager.merge(hospedagem);        	
        	entityManager.getTransaction().commit();            
        }catch(Exception e){
        	e.printStackTrace();
        	doRollback(entityManager);
        	throw e;
        }finally{
        	entityManager.close();
		}
	} 
     
}
