package br.com.buch.core.service;

import java.util.List;

import br.com.buch.core.dao.UsuarioDao;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.util.Criptografia;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.SendEmail;
import br.com.buch.core.util.SendEmailException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.managedBean.UsuarioBean.TipoFiltro;

public class ServiceUsuario implements GenericService<Usuario> {

	private static final String BUSCAR_PELO_NOME = "select u from Usuario u left JOIN FETCH u.hoteis where u.nomeUsuario = ?1";
	private static final String BUSCAR_SETORES = "Select distinct u.setor From Usuario u";
	private static final String CARREGAR_USUARIO = "Select u From Usuario u left JOIN FETCH u.hoteis where u.idUsusario = ?1";
	private static final String BUSCAR_TODOS = "Select u From Usuario u order by u.ativo, u.nomeUsuario";
	
	
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
	public String excluir(Usuario entidade) throws Exception{
		
		try {
			usuarioDao.delete(entidade);
			return "";
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new PersistenciaException("Ocorreu uma exceção ao excluir o Usuário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}		
	}

	
	public Usuario carregarEntidade(Usuario usuario) throws PersistenciaException{		
		try{
			return usuarioDao.findOne(CARREGAR_USUARIO, usuario.getIdUsusario());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Usuário!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	public List<Usuario> buscarTodos()throws PersistenciaException{
		try {
			
			return usuarioDao.find(BUSCAR_TODOS);
			/*Collections.sort(lista, new Comparator<Usuario>() {

				public int compare(Usuario o1, Usuario o2) {				
					return o1.getIdUsusario().compareTo(o2.getIdUsusario());
				}
			});
*/			
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
	
	
	public boolean fazerLogin(String login, String senha){
		Usuario usuario = null;
		
		if(login != null && senha != null){
			usuario = usuarioDao.findByUserNamePassword(login, senha);
		}	    
		
		return usuario !=null;		
	}

	
	public Usuario fazerLoginWS(String login, String senha){
		Usuario usuario = null;
		
		if(login != null && senha != null){
			usuario = usuarioDao.findByUserNamePassword(login, senha);
		}	     
		
		return usuario;		
	}
	
	
	public void recuperarSenha(String email, String fraseSecreta)throws NegocioException{
		Usuario usuario = usuarioDao.findByUserEmailOrFraseSecreta(email, fraseSecreta);
		
		if(usuario == null){
			throw new NegocioException("Nenhum Usuário que corresponda às suas informações foi encontrado!");
		}
		
		
		try {
			usuario.setSenha("123");
			salvar(usuario);
			
			
			SendEmail sendEmail = new SendEmail("smtp.gmail.com", true, "everton.buch@gmail.com", "ev019966");
			sendEmail.setAssunto("Recuperacão de Senha");
			sendEmail.setDestinatarios(email);
			sendEmail.setRemetente("everton.buch@gmail.com");
			
			StringBuilder builder = new StringBuilder();
			builder.append("Atenção, não responder este e-mail.<br/>");
			builder.append("Recebemos uma solicitação sua de recuperação de senha.<br/>");
			builder.append("Portanto estamos lhe enviando uma nova senha para acesso ao sistema.<br/>");
			builder.append("Nova Senha: 000<br/>");
			
			sendEmail.enviaEmailHtml(builder);
			
		} catch (SendEmailException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Usuario buscarPeloNome(Usuario usuario) {		
		try {
			return usuarioDao.findOne(BUSCAR_PELO_NOME, usuario.getNomeUsuario().toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<String> buscarSetores(){		
		return usuarioDao.findSectors(BUSCAR_SETORES);
	}


	public Usuario buscarPorId(Integer id) {
		try {
			return usuarioDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
}
