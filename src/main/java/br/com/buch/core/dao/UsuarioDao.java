package br.com.buch.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.buch.core.entity.Usuario;
import br.com.buch.core.util.Criptografia;

public class UsuarioDao extends GenericDao<Usuario>{

	public static final String BUSCAR_PELO_NOME = "select u from Usuario u left JOIN FETCH u.hoteis where u.nomeUsuario = ?1";
	
	public static final String BUSCAR_SETORES = "Select distinct u.setor From Usuario u";
	
	public static final String CARREGAR_USUARIO = "Select u From Usuario u left JOIN FETCH u.hoteis where u.idUsusario = ?1";
	
	public static final String BUSCAR_TODOS = "Select u From Usuario u order by u.ativo, u.nomeUsuario";
	
	public static final String FILTRAR_POR_CODIGO = "Select u From Usuario u where u.idUsusario = ?1";
	
	public static final String FILTRAR_POR_NOME_USER = "Select u From Usuario u where u.nomeUsuario like ?";
	
	
	public UsuarioDao() {
		super(Usuario.class);
	}
	
	

	public Usuario findByUserNamePassword(String login, String senha) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		TypedQuery<Usuario> query = em.createQuery(
						"select u from Usuario u where u.nomeUsuario = :pUsuario and u.senha = :pSenha",
						Usuario.class);

		query.setParameter("pUsuario", login.toUpperCase());
		query.setParameter("pSenha", Criptografia.criptografarSha256(senha));

		Usuario retorno = null;
		try {
			retorno = (Usuario) query.getSingleResult();
			em.getTransaction().commit();
			
		} catch (NoResultException ex) {
			doRollback(em);
		}finally {
			em.close();
		}

		return retorno;
	}
	
	
	public Usuario findByUserEmailOrFraseSecreta(String email, String fraseSecreta) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		TypedQuery<Usuario> query = em.createQuery(
						"select u from Usuario u where u.fraseSecreta = :pFraseSecreta or u.email = :pEmail",
						Usuario.class);

		query.setParameter("pFraseSecreta", fraseSecreta);
		query.setParameter("pEmail", email);

		Usuario retorno = null;
		try {
			retorno = (Usuario) query.getSingleResult();
			em.getTransaction().commit();
			
		} catch (NoResultException ex) {
			doRollback(em);
		}finally {
			em.close();
		}

		return retorno;
	}

	
	public List<String> findSectors(String jpql){
		
		EntityManager em = getEntityManager();
		TypedQuery<String> query = em.createQuery(jpql, String.class);
		
		List<String> lista;
		try {
			lista =  query.getResultList();
			
		} catch (Exception ex) {
			lista = null;
		}finally {
			em.close();
		}
		return lista;
	}

}
