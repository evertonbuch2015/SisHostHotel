package br.com.buch.core.util;

import java.util.ArrayList;
import java.util.List;

import br.com.buch.core.dao.ApartamentoDao;
import br.com.buch.core.dao.BancoDao;
import br.com.buch.core.dao.CategoriaDao;
import br.com.buch.core.dao.TipoTarifaDao;
import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.Categoria;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.entity.Usuario;


public class Constantes {

	private static Constantes instance;
	
	private List<Usuario> usuariosLogados; 
	private List<Apartamento> listaApartamentos;
	private List<Categoria> listaCategorias;
	private List<Banco> listaBancos;
	private List<TipoTarifa> listaTiposTarifa;
	
	private Constantes() {
		usuariosLogados = new ArrayList<Usuario>();
	}
	
	
	public static synchronized Constantes getInstance(){
		if(instance == null)
			instance = new Constantes();
		return instance;		
	}

	
	public void refresh(){
		try {			
			listaApartamentos 	= new ApartamentoDao().findAll();
			listaCategorias 	= new CategoriaDao().findAll();
			listaBancos 		= new BancoDao().findAll();
			listaTiposTarifa    = new TipoTarifaDao().findAll();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public List<Usuario> getUsuariosLogados() {return usuariosLogados;}
	
	
	public List<Apartamento> getListaApartamentos() throws Exception {
		return listaApartamentos != null ? listaApartamentos : new ApartamentoDao().findAll();
	}
	
	public List<Categoria> getListaCategorias() throws Exception {
		return listaCategorias != null ? listaCategorias :new CategoriaDao().findAll();
	}
	
	public List<Banco> getListaBancos() throws Exception {
		return listaBancos != null ? listaBancos : new BancoDao().findAll();
	}
	
	public List<TipoTarifa> getListaTiposTarifa() throws Exception {
		return listaTiposTarifa != null ? listaTiposTarifa : new TipoTarifaDao().findAll();
	}
	
	
	public void addUsuarioLogado(Usuario usuario){
		if(!this.usuariosLogados.contains(usuario))
			this.usuariosLogados.add(usuario);
	}	
	
	public void removeUsuarioLogado(Usuario usuario){
		if(this.usuariosLogados.contains(usuario))
			this.usuariosLogados.remove(usuario);
	}
}
