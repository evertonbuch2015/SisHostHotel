package br.com.buch.core.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.buch.core.dao.UsuarioDao;
import br.com.buch.core.entity.Usuario;
import br.com.buch.view.managedBean.UsuarioBean.TipoFiltro;
import br.com.buch.view.util.UtilMensagens;

public class UsuarioService implements GenericService<Usuario> {

	private UsuarioDao usuarioDao;
	
	
	public UsuarioService() {
		usuarioDao = new UsuarioDao();
	}
	
	
	@Override
	public boolean salvar(Usuario entidate) {
		if (entidate.getIdUsusario() == null) {
			entidate.setSenha(entidate.getSenhaCriptografada());
			if (usuarioDao.save(entidate)) {
				UtilMensagens.mensagemInformacao("Usu�rio Inserido com Sucesso");
				return true;
			}
		} else {			
			if(entidate.getSenha().length() <= 40){
				entidate.setSenha(entidate.getSenhaCriptografada());
			}
			
			if (usuarioDao.update(entidate)) {
				UtilMensagens.mensagemInformacao("Usu�rio Alterado com Sucesso");
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void excluir(Usuario entidade) {
		usuarioDao.delete(entidade);
	}

	
	@Override
	public Usuario carregarEntidade(Integer id) {		
		try{
			String jpql = "Select u From Usuario u left JOIN FETCH u.empresas where u.idUsusario = ?1";
			return usuarioDao.findOne(jpql, id);
		}
		catch(javax.persistence.NonUniqueResultException ex){
			ex.printStackTrace();
			UtilMensagens.mensagemAtencao("Existem mais de um Usu�rio para o c�digo "+id);
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Usu�rio n�o encontrado!");
			return null;
		}
	}

	
	public List<Usuario> buscarTodos(){
		List<Usuario> lista = usuarioDao.findAll();
		
		Collections.sort(lista, new Comparator<Usuario>() {

			@Override
			public int compare(Usuario o1, Usuario o2) {				
				return o1.getCodigoEstrutural().compareTo(o2.getCodigoEstrutural());
			}
		});
		
		return lista;
	}
	
	
	public List<Usuario> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
		List<Usuario> lista= null;
		
		if(tipoFiltro.equals(TipoFiltro.CODIGO)){
			String jpql = "Select u From Usuario u where u.idUsusario in (" + valorFiltro + ")";
			lista = usuarioDao.find(jpql);
		}
		else if(tipoFiltro.equals(TipoFiltro.NOME)){
			lista = usuarioDao.find("Select u From Usuario u where u.nomeUsuario like ?",valorFiltro);
		}
		
		return lista;
	}

	
	public boolean logar(String login, String senha){
		FacesContext context = FacesContext.getCurrentInstance();
		
		Usuario usuario = null;
		
		if(login != null && senha != null){
			usuario = usuarioDao.existe(login, senha);
		}	    
		
		if(usuario != null){
			context.getExternalContext().getFlash().setKeepMessages(true);
	    	context.addMessage(null, new FacesMessage("Selecione uma Empresa"));
			
	    	return true;
		}    
	    
	    context.getExternalContext().getFlash().setKeepMessages(true);
	    context.addMessage(null, new FacesMessage("Usu�rio n�o encontrado"));
	    
		return false;
	}


	public Usuario buscarPeloNome(Usuario usuario) {
		try{
			String jpql = "select u from Usuario u where u.nomeUsuario = ?1";
			return usuarioDao.findOne(jpql, usuario.getNomeUsuario().toUpperCase());
		}
		catch(javax.persistence.NonUniqueResultException ex){
			ex.printStackTrace();
			UtilMensagens.mensagemAtencao("Existem mais de um Usu�rio para o nome "+usuario.getNomeUsuario());
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Usu�rio n�o encontrado!");
			return null;
		}
	}


	public List<String> buscarSetores(){
		String jpql = "Select distinct u.setor From Usuario u";		
		return usuarioDao.findSectors(jpql);
	}
}
