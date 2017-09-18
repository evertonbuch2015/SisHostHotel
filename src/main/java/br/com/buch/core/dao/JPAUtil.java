package br.com.buch.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

//Classe responsavel por instanciar uma conexão com o banco.
public class JPAUtil {
	
	private static JPAUtil instanceJPAUtil;
	private EntityManagerFactory emf = null;
	private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();
	
	private JPAUtil() {
        this.emf = Persistence.createEntityManagerFactory("SisHostHotel");
    }

	
	public static synchronized JPAUtil GetInstance() {
		if (instanceJPAUtil == null) {
			instanceJPAUtil = new JPAUtil();
		}
		return instanceJPAUtil;
	}

	
	public EntityManager getEntityManager() {
		EntityManager em = (EntityManager) threadLocal.get();

        if (em == null || !em.isOpen()) {
        		
        	if(em != null){
        		EntityTransaction transaction = em.getTransaction();
        		if(transaction.isActive()){
        			transaction.commit();
            		em.close();
        		}

        		threadLocal.set(null);
        	}        	

            em = emf.createEntityManager();
            threadLocal.set(em);
        }

        return em;
	}	
}
