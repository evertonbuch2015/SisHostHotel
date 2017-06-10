package br.com.buch.core.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.buch.core.dao.UsuarioDao;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.util.Criptografia;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.UsuarioBean.TipoFiltro;

public class ServiceUsuario implements GenericService<Usuario> {

	private UsuarioDao usuarioDao;
	
	
	public ServiceUsuario() {
		usuarioDao = new UsuarioDao();
	}
	
	
	@Override
	public String salvar(Usuario entidate) throws Exception{
		if (entidate.getIdUsusario() == null) {
			
			try {
				entidate.setSenha(Criptografia.criptografarSha256(entidate.getSenha()));
				usuarioDao.save(entidate);
				return "Usuário Cadastrado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao Alterar o Usuário!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}else{			
			
			if(entidate.getSenha().length() <= 40){
				entidate.setSenha(Criptografia.criptografarSha256(entidate.getSenha()));
			}
			
			try {				
				usuarioDao.update(entidate);
				return "Usuário Alterado com Sucesso!";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao Alterar o Usuário!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
	}

	
	@Override
	public void excluir(Usuario entidade) throws Exception{
		
		try {
			usuarioDao.delete(entidade);
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao excluir o Usuário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}		
	}

	
	public Usuario carregarEntidade(Usuario usuario) throws PersistenciaException{		
		try{
			String jpql = "Select u From Usuario u left JOIN FETCH u.hoteis where u.idUsusario = ?1";
			return usuarioDao.findOne(jpql, usuario.getIdUsusario());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Usuário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	public List<Usuario> buscarTodos(){
		List<Usuario> lista = null;
		try {
			
			lista = usuarioDao.findAll();			
			Collections.sort(lista, new Comparator<Usuario>() {

				@Override
				public int compare(Usuario o1, Usuario o2) {				
					return o1.getIdUsusario().compareTo(o2.getIdUsusario());
				}
			});
			
			return lista;
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Usuario> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Usuario> lista = null;
		
		try {
		
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){			
				String jpql = "Select u From Usuario u where u.idUsusario in (" + valorFiltro + ")";
				lista = usuarioDao.find(jpql);				
			}
			else if(tipoFiltro.equals(TipoFiltro.NOME)){							
				lista = usuarioDao.find("Select u From Usuario u where u.nomeUsuario like ?",valorFiltro);						
			}		
			return lista;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Usuário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	@Override
	public void consisteAntesEditar(Usuario entidade) throws NegocioException{	}
	
	

	
	public boolean logar(String login, String senha){
		Usuario usuario = null;
		
		if(login != null && senha != null){
			usuario = usuarioDao.existe(login, senha);
		}	    
		
		return usuario !=null;		
	}

	
	public void recuperarSenha(String email, String fraseSecreta){
		
	}

	
	public Usuario buscarPeloNome(Usuario usuario) {		
		try {
			String jpql = "select u from Usuario u where u.nomeUsuario = ?1";
			return usuarioDao.findOne(jpql, usuario.getNomeUsuario().toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<String> buscarSetores(){
		String jpql = "Select distinct u.setor From Usuario u";		
		return usuarioDao.findSectors(jpql);
	}
}
