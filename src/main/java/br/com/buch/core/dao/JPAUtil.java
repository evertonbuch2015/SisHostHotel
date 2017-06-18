package br.com.buch.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Classe responsavel por instanciar uma conexão com o banco.
public class JPAUtil {
	
	private static JPAUtil instanceJPAUtil;
	private EntityManagerFactory emf = null;
	
	
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
		return emf.createEntityManager();
	}	
}
