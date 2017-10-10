package br.com.buch.view.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.entity.Usuario;

public class SessionContext {
	 private static SessionContext instance;
     
     public static SessionContext getInstance(){
        if (instance == null){
            instance = new SessionContext();
        }
        
        return instance;
     }
     
     
     private SessionContext(){
        
     }
     
     
     private ExternalContext currentExternalContext(){
        if (FacesContext.getCurrentInstance() == null){
            throw new RuntimeException("O FacesContext n�o pode ser chamado fora de uma requisi��o HTTP");
        }else{
            return FacesContext.getCurrentInstance().getExternalContext();
        }
     }
          
     
     private FacesContext currentFacesContext(){
         if (FacesContext.getCurrentInstance() == null){
             throw new RuntimeException("O FacesContext n�o pode ser chamado fora de uma requisi��o HTTP");
         }else{
             return FacesContext.getCurrentInstance();
         }
      }    
     
     
     public void encerrarSessao(){
        currentExternalContext().invalidateSession();
     }
     
     
     public void adicionarMensagem(String message){
    	 currentFacesContext().addMessage(null, new FacesMessage(message));
     }
     
     
     public Object getAttribute(String nome){
        return currentExternalContext().getSessionMap().get(nome);
     }
     
     
     public void setAttribute(String nome, Object valor){
        currentExternalContext().getSessionMap().put(nome, valor);
     }
     
     
     public void deleteAttribute(String key){
    	 currentExternalContext().getSessionMap().remove(key);
     }
     
     
     public Usuario getUsuarioLogado(){
    	 Usuario usuarioLogado = 
    			 (Usuario) currentExternalContext().getSessionMap().get("usuarioLogado");
    	 
    	 return usuarioLogado;
     }
     
     
     public Hotel getHotelUsuarioLogado(){
    	 Hotel hotel = 
    			 (Hotel) currentExternalContext().getSessionMap().get("hotel");
    	 
    	 return hotel;
     }
}
