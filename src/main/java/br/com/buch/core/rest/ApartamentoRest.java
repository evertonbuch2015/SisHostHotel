package br.com.buch.core.rest;

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

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.core.util.PersistenciaException;

@Path("/apartamento")
public class ApartamentoRest {

	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private ServiceApartamento service;
	
	@PostConstruct
	private void init(){
		this.service = new ServiceApartamento();
	}	
	

	@GET
	@Path("/buscarTodos")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Apartamento> buscarTodos(){
		
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
	public Apartamento buscarPorId(@PathParam("id") Integer id){
		
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
	public Apartamento salvar(Apartamento entidade){
		
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
			Apartamento entidade = new Apartamento();
			entidade.setIdApartamento(id);
			return service.excluir(entidade);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}

}
