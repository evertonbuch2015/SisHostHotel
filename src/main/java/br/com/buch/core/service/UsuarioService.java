package br.com.buch.core.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityNotFoundException;

import br.com.buch.core.dao.UsuarioDao;
import br.com.buch.core.entity.Usuario;
import br.com.buch.view.managedBean.UsuarioBean.TipoFiltro;
import br.com.buch.view.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

public class UsuarioService implements GenericService<Usuario> {

	private UsuarioDao usuarioDao;
	
	
	public UsuarioService() {
		usuarioDao = new UsuarioDao();
	}
	
	
	@Override
	public boolean salvar(Usuario entidate) {
		if (entidate.getIdUsusario() == null) {
			
			try {
				entidate.setSenha(entidate.getSenhaCriptografada());
				usuarioDao.save(entidate);
				UtilMensagens.mensagemInformacao("Usuário Inserido com Sucesso!");
				return true;
			} catch (Exception e) {
				UtilMensagens.mensagemErro("Erro ao Inserir o Usuário"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}			
		}else{			
			
			if(entidate.getSenha().length() <= 40){
				entidate.setSenha(entidate.getSenhaCriptografada());
			}
			
			try {				
				usuarioDao.update(entidate);
				UtilMensagens.mensagemInformacao("Usuário Alterado com Sucesso!");
				return true;
			} catch (Exception e) {
				UtilMensagens.mensagemErro("Erro ao Atualizar os dados do Usuário"
						+ "\nErro: " + UtilErros.getMensagemErro(e));
				return false;
			}			
		}
	}

	
	@Override
	public void excluir(Usuario entidade) {
		
		try {
			usuarioDao.delete(entidade);
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
			
		}catch (EntityNotFoundException enfe) {                        
            UtilMensagens.mensagemErro("The Item with id " + entidade.getIdUsusario()+ " no longer exists." +
            		" \nErro: " + UtilErros.getMensagemErro(enfe));
        }catch (Exception ex) {
            UtilMensagens.mensagemErro("Erro ao Excluir os dados do Usuário" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}		
	}

	
	@Override
	public Usuario carregarEntidade(Integer id) {		
		try{
			String jpql = "Select u From Usuario u left JOIN FETCH u.empresas where u.idUsusario = ?1";
			return usuarioDao.findOne(jpql, id);
		}
		catch(javax.persistence.NonUniqueResultException ex){
			ex.printStackTrace();
			UtilMensagens.mensagemAtencao("Existem mais de um Usuário para o código "+id);
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			UtilMensagens.mensagemAtencao("Usuário não encontrado!");
			return null;
		}
	}

	
	public List<Usuario> buscarTodos(){
		List<Usuario> lista = null;
		try {
			
			lista = usuarioDao.findAll();			
			Collections.sort(lista, new Comparator<Usuario>() {

				@Override
				public int compare(Usuario o1, Usuario o2) {				
					return o1.getCodigoEstrutural().compareTo(o2.getCodigoEstrutural());
				}
			});
			
			return lista;
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Usuario> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro){
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
			return null;
		}		
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
	    context.addMessage(null, new FacesMessage("Usuário não encontrado"));
	    
		return false;
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
