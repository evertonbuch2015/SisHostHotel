package br.com.buch.webServices.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.buch.core.entity.Categoria;
import br.com.buch.core.service.ServiceCategoria;
import br.com.buch.core.util.PersistenciaException;

@Path("/categoria")
public class CategoriaRest {

	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private ServiceCategoria service;
	
	@PostConstruct
	private void init(){
		this.service = new ServiceCategoria();
	}	
	

	@GET
	@Path("/buscarTodos")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Categoria> buscarTodos(){
		
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
	public Categoria buscarPorId(@PathParam("id") Integer id){
		
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
	public Categoria salvar(Categoria entidade){
		
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
			Categoria entidade = new Categoria();
			entidade.setIdCategoria(id);
			return service.excluir(entidade);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}

}
