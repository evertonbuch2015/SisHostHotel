package br.com.buch.view.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UtilMensagens {
	
	public static final String MSM_ERRO_INTERNO = "Ocorreu um Erro Interno ao buscar os dados.Entre em Contato com o Suporte!";
	
	
	public static void mensagemErro(String mensagem){
		FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"",mensagem);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	public static void mensagemInformacao(String mensagem){
		FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_INFO,"",mensagem);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	public static void mensagemAtencao(String mensagem){
		FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_WARN,"",mensagem);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
