package br.com.buch.webServices.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.buch.core.entity.Usuario;
import br.com.buch.core.service.ServiceUsuario;
import br.com.buch.core.util.PersistenciaException;

@Path("/usuario")
public class UsuarioRest {

	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private ServiceUsuario service;
	
	@PostConstruct
	private void init(){
		service = new ServiceUsuario();
	}
	
	
	@GET
	@Path("/buscarTodos")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Usuario> buscarTodos(){
		
		try {
			return service.buscarTodos();
		} catch (PersistenciaException e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GET
	@Path("/buscarPorId/{id}")	
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario buscarPorId(@PathParam("id") Integer id){
		
		try {
			return service.buscarPorId(id);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@POST
	@Path("/salvar")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario salvar(Usuario entidade){
		
		try {
			service.salvar(entidade);
			return entidade;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String excluir(@PathParam("id") Integer id){		
		
		try {
			Usuario entidade = new Usuario();
			entidade.setIdUsusario(id);
			return service.excluir(entidade);
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}		
	}
	
		
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario login(@FormParam("nomeUsuario")String nomeUsuario,
						 @FormParam("senha")String senha){
		
		try {
			return service.fazerLoginWS(nomeUsuario, senha);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
