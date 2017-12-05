package br.com.buch.core.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


/**
 *
 * @author Everton de Souza
 * @param <T>
 */
public abstract class GenericDao<T extends Serializable> {
    
    private final Class<T> aClass;     
    
    
    /**
     * Cria o construtor da classe GenericDao identificando a Entidade que ser� usada.
     * @param aClass 
     */
    protected GenericDao(Class<T> aClass){
        this.aClass = aClass;
    }
        
    
    /**
     * Retorna um novo Entity Manager.
     * @return 
     */
    protected EntityManager getEntityManager(){
    	return JPAUtil.GetInstance().getEntityManager();
    }
    
    
    
    /**
     * Realiza o RoolBack da transa��o e fecha a mesma.
     * @param EntityManager em
     * @return 
     */
    public void doRollback(EntityManager em) {
    	if (em.getTransaction().isActive() == false) {
            em.getTransaction().begin();
        }
        em.getTransaction().rollback();
	}
    
    
    
    
    /**
     * Retornar apenas uma Entidade conforme os parametros recebidos.
     * Entidade estará com o estado DATACHED.
     * @param jpql
     * @param params
    */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T findOne(String jpql , Object...params)throws Exception{
        
    	EntityManager em = getEntityManager();
        em.getTransaction().begin();
        
        Query query = em.createQuery(jpql);
        for(int i=0 ; i < params.length ; i++){
            query.setParameter(i+1, params[i]);
        }
        
        T entity = null;
        try {
        	entity = (T) query.getSingleResult();
        	em.getTransaction().commit();
		
		}catch(Exception ex){
			ex.printStackTrace();
			doRollback(em);
			throw ex;
		}finally {
			em.close();
		}		
        
        return entity;
    }
    
    
   /**
    * Retornar apenas uma Entidade conforme os parametros recebidos.
    * Deve comitar e encerrar o EntityManager manualmente.
    * Entidades estar�o com o estado MANAGED.
    * @param EntityManager em
    * @param jpql
    * @param params
   */
   @SuppressWarnings("unchecked")
   public T findOne(EntityManager em,String jpql , Object...params)throws Exception{
       em.getTransaction().begin();
              		
       T entity = null;
       try {
    	   Query query = em.createQuery(jpql);
           
           for(int i=0 ; i < params.length ; i++){
               query.setParameter(i+1, params[i]);
           } 
           
           entity = (T) query.getSingleResult();
           em.getTransaction().commit();
           
		} catch (Exception e) {
			e.printStackTrace();
			doRollback(em);
			throw e;
		}finally{
			em.close();
		}
       return entity;
   }
    
   
   
   
   
    /**
     * Retornar uma Lista de Entidades conforme os parametros recebidos.
     * Entidades estarão com o estado DATACHED.
     * @param jpql
     * @param params
     * @return 
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> find(String jpql , Object...params)throws PersistenceException{
    	
    	EntityManager em = getEntityManager();
    	em.getTransaction().begin();
        em.clear();
        
        Query query = em.createQuery(jpql).setHint("org.hibernate.readOnly", true);
        for(int i=0 ; i < params.length ; i++){
            query.setParameter(i+1, params[i]);
        }
        
		List<T> entities = null;
        try {
        	entities = query.getResultList();
            em.getTransaction().commit();
            
		} catch (Exception e) {
			e.printStackTrace();
			doRollback(em);
			throw e;
		}finally{
			em.close();
		}
        return entities;
    }
    
    
    /**
     * Retornar uma Lista de Entidades conforme os parametros recebidos.
     * Deve comitar e encerrar o EntityManager manualmente.
     * Entidades estar�o com o estado MANAGED.
     * @param EntityManager em
     * @param jpql
     * @param params
     * @return 
     */
    @SuppressWarnings("unchecked")
	public List<T> find(EntityManager em,String jpql , Object...params)throws Exception{
    	em.getTransaction().begin();
        em.clear();
        
        Query query = em.createQuery(jpql).setHint("org.hibernate.readOnly", true);
        
        for(int i=0 ; i < params.length ; i++){
            query.setParameter(i+1, params[i]);
        }
        
		List<T> entities = null;        
        entities = query.getResultList();

        return entities;
    }
    
    
    
    
    /**
     * Recupera uma Entidade apenas.
     * Entidade estar� com o estado DATACHED.
     * @param EntityManager em
     * @param id
     * @return 
     */
    public T findById(Integer id)throws Exception{        
        
    	EntityManager em = getEntityManager();
        em.getTransaction().begin();
        
        T entity = null;
        try {           
            entity = (T) em.find(aClass, id);
            em.getTransaction().commit();
            
        } catch (Exception e) {
        	e.printStackTrace();
        	doRollback(em);
        	throw e;
        }finally{
			em.close();
		}            
        return entity;
    }
    
    
    /**
    * Recupera uma Entidade apenas.
    * Entidade estar� com o estado MANAGED.
    * @param EntityManager em
    * @param id
    * @return 
    */
   public T findById(EntityManager em,Integer id)throws Exception{       
       em.getTransaction().begin();
       
       T entity = null;
       entity = (T) em.find(aClass, id);           
       return entity;
   }
    
    
    
   
    /**
     * Metodo para retornar todas as Entidades do banco de dados.
     * Entidades estar�o com o estado DATACHED.
     * @return 
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> findAll()throws Exception{
    	
    	EntityManager em = getEntityManager();
        em.getTransaction().begin();        
        Query query = em.createQuery("From " + aClass.getSimpleName()).setHint("org.hibernate.readOnly", true);
        
        List<T> entities = null;
        try {
        	entities = query.getResultList();        	
        	em.getTransaction().commit();
        	
		} catch (Exception e) {
			e.printStackTrace();
			doRollback(em);  
			throw e;
		}finally{
			em.close();
		}
	            
        return entities; 
    }

    
    
    /**
    *Metodo para realizar o save do Objeto Entidade
     * @param entity
    */
    public void save(T entity)throws Exception{
    	EntityManager em = getEntityManager();
    	
    	try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

        } catch (Exception e) {
        	e.printStackTrace();
        	doRollback(em);
        	throw e;
        }finally{
			em.close();
		}
    }
    
    
    
    /**
    *Metodo para realizar o update do Objeto Entidade
     * @param entity
    */
    public void update(T entity)throws Exception{
    	EntityManager em = getEntityManager();
    	
    	try{
            em.getTransaction().begin();            
            em.merge(entity);
            em.getTransaction().commit();
            
        }catch(Exception e){
        	e.printStackTrace();
        	doRollback(em);
        	throw e;
        }finally{
			em.close();
		}
    }
    
    
    
    /**
    *Remover um objeto do banco recebendo o id da entidade.
     * @param id
    */
    public void delete(Long id)throws Exception{
    	EntityManager em = getEntityManager();
    	
    	try {
            em.getTransaction().begin();
            em.remove(em.find(aClass, id));
            em.getTransaction().commit();                                    
        }catch (Exception e) {            
        	doRollback(em);     		
    		throw e;     	
        }finally{
			em.close();
		}
    }
    

    
    /**
    *Remover um objeto do banco recebendo uma Entidade
     * @param entity
    */
    public void delete(T entity)throws Exception{        
    	EntityManager em = getEntityManager();
    	
    	try {
            em.getTransaction().begin();
            T c = em.merge(entity);
            em.remove(c);
            em.getTransaction().commit();            
        }catch (Exception e) {            
        	doRollback(em);     		
        	throw e;  
        }finally{
			em.close();
		}
    }
    
    
    
    /**
     * Recupera a quantidade de registros da Entidade no banco de dados 
     * @return 
     */
    public Long count() throws Exception{
    	EntityManager em = getEntityManager();
    	em.getTransaction().begin();
        
        Query query = em.createQuery("select count(c) from " + aClass.getSimpleName()+" c ");
        Long count = (Long) query.getSingleResult();
        
        em.getTransaction().commit();       
        em.close();
        
        return count;
    }
    
    
    /**
     * Recupera o mair valor de um registro Integer da Entidade.
     * @param campo 
     * @return 
     */
    public Integer getMaxField(String campo) throws Exception{
    	EntityManager em = getEntityManager();
    	try {    	
            Query query = em.createQuery("Select max("+campo+") From " + aClass.getSimpleName()+" c ");
            Integer num = (Integer) query.getSingleResult();
            
            return num == null ? 1 : num;
		}catch(java.lang.ClassCastException ex){			
			new PersistenceException("Campo passado por parametro deve ser um Integer.", ex);	
			return null;
		}finally {
			em.close();			
		}       
    }
}

