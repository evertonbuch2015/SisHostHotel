package br.com.buch.core.util;

import java.util.ArrayList;
import java.util.List;

import br.com.buch.core.entity.Usuario;


public class Constantes {

	private static Constantes instance;
	private List<Usuario> usuariosLogados; 
	
	
	private Constantes() {
		refresh();
	}
	
	
	public static synchronized Constantes getInstance(){
		if(instance == null){
			instance = new Constantes(); 
		}
		return instance;		
	}
		
	
	
	public void refresh(){
		try {
			
			usuariosLogados = new ArrayList<Usuario>();			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void addUsuarioLogado(Usuario usuario){
		if(!this.usuariosLogados.contains(usuario)){
			this.usuariosLogados.add(usuario);
		}
	}
	
	
	public void removeUsuarioLogado(Usuario usuario){
		if(this.usuariosLogados.contains(usuario)){
			this.usuariosLogados.remove(usuario);
		}
	}
	
	
	public List<Usuario> getUsuariosLogados() {
		return usuariosLogados;
	}

}
