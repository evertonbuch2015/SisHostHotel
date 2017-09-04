package br.com.buch.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Recebimento;

public class HospedagemDao extends GenericDao<Hospedagem> {

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


	
    
    public void confirmarCheckOut(Hospedagem hospedagem, Recebimento recebimento, List<Adiantamento> adiantamentos) throws Exception{
		EntityManager entityManager = getEntityManager();
    	
    	try{
    		entityManager.getTransaction().begin();
    		    		
        	for (Adiantamento adiantamento : adiantamentos) {
        		entityManager.merge(adiantamento);
    		}
        	        	
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
