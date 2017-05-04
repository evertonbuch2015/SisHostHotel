package br.com.buch.view.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UtilMensagens {

	
	public static void mensagemErro(String mensagem){
		FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,"");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	public static void mensagemInformacao(String mensagem){
		FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,"");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	public static void mensagemAtencao(String mensagem){
		FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_WARN,mensagem,"");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
