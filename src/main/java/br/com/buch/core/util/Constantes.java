package br.com.buch.core.util;

import java.util.ArrayList;
import java.util.List;

import br.com.buch.core.dao.ApartamentoDao;
import br.com.buch.core.dao.BancoDao;
import br.com.buch.core.dao.CategoriaDao;
import br.com.buch.core.dao.FormaPagamentoDao;
import br.com.buch.core.dao.TipoTarifaDao;
import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.Categoria;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.entity.Usuario;


public class Constantes {

	private static Constantes instance;
	
	private List<Usuario> usuariosLogados; 
	private List<Apartamento> listaApartamentos;
	private List<Categoria> listaCategorias;
	private List<Banco> listaBancos;
	private List<TipoTarifa> listaTiposTarifa;
	private List<FormaPagamento> listaFormasPagamento;
	
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
			listaApartamentos 	 = new ApartamentoDao().findAll();
			listaCategorias 	 = new CategoriaDao().findAll();
			listaBancos 		 = new BancoDao().findAll();
			listaTiposTarifa     = new TipoTarifaDao().findAll();
			listaFormasPagamento = new FormaPagamentoDao().findAll();			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public List<Usuario> getUsuariosLogados() {return usuariosLogados;}
	
	
	public List<Apartamento> getListaApartamentos() throws Exception {
		if (listaApartamentos == null) {
			listaApartamentos = new ApartamentoDao().findAll();
		}
		return listaApartamentos;
	}
	
	public List<Categoria> getListaCategorias() throws Exception {
		if (listaCategorias == null) {
			listaCategorias = new CategoriaDao().findAll();
		}
		return listaCategorias;
	}
	
	public List<Banco> getListaBancos() throws Exception {
		if (listaBancos == null) {
			listaBancos = new BancoDao().findAll();
		}
		return listaBancos; 
	}
	
	public List<TipoTarifa> getListaTiposTarifa()throws Exception{
		if (listaTiposTarifa == null) {
			listaTiposTarifa = new TipoTarifaDao().findAll();
		}
		return listaTiposTarifa;
	}
	
	public List<FormaPagamento> getListaFormasPagamento()throws Exception{
		if (listaFormasPagamento == null) {
			listaFormasPagamento = new FormaPagamentoDao().findAll();
		}
		return listaFormasPagamento;
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