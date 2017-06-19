package br.com.buch.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class SendEmail {
	
	private String hostName;
    private boolean ssl;
    private String sslSmtpPort;
    private boolean StartTLSRequired;
    
    private String usuario;
    private String senha;
    
    private String remetente;
    private String assunto;
    private String mensagem;
    private String destinatarios;

    private List<String> listaDestinatarios;
    private List<File> listaAnexos;
	
    //____________________________________CONSTRUTORES______________________________________________//	
	
	/**
     * Cria o Objeto Email setando as seguintes informações. 
     * @param hostName
     * @param ssl
     * @param StartTLSRequired
     * @param sslSmtpPort
     * @param usuario
     * @param senha
     */
    public SendEmail(String hostName, boolean ssl, String usuario ,String senha){
        this.ssl = ssl;
        this.StartTLSRequired = false;
        this.hostName = hostName;
        this.usuario = usuario;
        this.senha = senha;
        
        this.listaDestinatarios = new ArrayList<String>();
    }

    //____________________________________METODOS DE ENVIO DE EMAIL_______________________________________//
    
    /**
     * 
     */
    public void enviaEmailSimples()throws SendEmailException {
    	validaCampos();
        SimpleEmail email = new SimpleEmail();
        
        email.setSSLOnConnect(ssl);
        email.setHostName(hostName);
        email.setSslSmtpPort(sslSmtpPort);
        email.setAuthenticator(new DefaultAuthenticator(usuario, senha));

        try {
            email.setFrom(remetente);
            email.setDebug(true);
            email.setSubject(assunto);
            email.setMsg(mensagem);
            
                        
            for (String string : listaDestinatarios) {
                email.addTo(string);
            }

            email.send();
        } catch (EmailException e) {
            throw new SendEmailException("Erro ao Enviar o Email!",e);
        }
    }

    
    public void enviaEmailHtml(StringBuilder builder) throws SendEmailException{
    	try {
	    	validaCampos();
	    	
	    	HtmlEmail email = new HtmlEmail();
	    	email.setSSLOnConnect(ssl);        	
	    	
	    	if(StartTLSRequired){
	        	email.setStartTLSRequired(StartTLSRequired);
	            email.setSslSmtpPort(sslSmtpPort);
	    	}
	    	
	        email.setHostName(hostName);
	        email.setAuthenticator(new DefaultAuthenticator(usuario, senha));                                 

	        email.setFrom(remetente);
            email.setSubject(assunto);
            email.setHtmlMsg(builder.toString());
            
            for (String string : listaDestinatarios) {
                email.addTo(string);
            }
            
            email.send();
            
        } catch (EmailException e) {
        	throw new SendEmailException("Erro ao Enviar o Email!",e);
        }
    }
    
    
    
    /**
     * Classe responsável por enviar email com anexos e Mensagem Simples.
     * 
     * @author Everton Buchkorn de Souza
	 *	everton.buch@gmail.com  -  18 de jun de 2017
	 *
     * @throws SendEmailException
     */
    public void enviaEmailAnexo()throws SendEmailException {
        try {
        	validaCampos();
        	validaListaAnexos();
        	
        	HtmlEmail email = new HtmlEmail();
        	email.setSSLOnConnect(ssl);        	
        	
        	if(StartTLSRequired){
	        	email.setStartTLSRequired(StartTLSRequired);
	            email.setSslSmtpPort(sslSmtpPort);
        	}
        	
            email.setHostName(hostName);
            email.setAuthenticator(new DefaultAuthenticator(usuario, senha));                                 

            email.setFrom(remetente);
            email.setSubject(assunto);
            email.setHtmlMsg(mensagem);

            for (String string : listaDestinatarios) {
                email.addTo(string);
            }

            
            for (File arquivo : listaAnexos) {
                email.attach(arquivo);
            }
            
            email.send();
        } catch (EmailException e) {
        	throw new SendEmailException("Erro ao Enviar o Email!",e);
        }
    }
    
    
    //____________________________________METODOS AUXILIARES_________________________________________________//
    
    
    private void validaCampos() throws SendEmailException{
        
    	if(assunto.equals("")){
            throw new SendEmailException("Assunto do e-mail deve ser preenchido!");            
        }
        
        if(listaDestinatarios.isEmpty() || remetente == null || remetente.equals("")){        
            throw new SendEmailException("Destinatário e Remetente deve ser preenchido!");
        }
    }
    
    
    private void validaListaAnexos()throws SendEmailException{
    	if (listaAnexos == null || listaAnexos.isEmpty()) {
    		throw new SendEmailException("Informe ao menos um Arquivo para ser anexado ao Email!");
        }
    }
    
    
    public void adicionarDestinatario(String dest) {
        listaDestinatarios.add(dest);
    }
    
    
    public void adicionarAnexo(File arq){
        if(this.listaAnexos == null){
            this.listaAnexos = new ArrayList<File>();
        }
        listaAnexos.add(arq);
    }
    
  //____________________________________METODOS GET E SET______________________________________________//
    
    
    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String dest) {

        String remet = "";
        for (int i = 0; i < dest.length(); i++) {
            
            
            if(dest.charAt(i) != ';'){
                remet =remet +dest.charAt(i);
            }
            
            if(dest.charAt(i) == ';' || i == dest.length()-1){
                adicionarDestinatario(dest);
                remet ="";
            }
        }
    }
    
    
    public String getHostName() {
        return hostName;
    }
    
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
      

    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
   
    
    public String getRemetente() {
        return remetente;
    }
    
    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    
    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

        
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isSsl() {
        return ssl;
    }
    
    
    public String getSslSmtpPort() {
        return sslSmtpPort;
    }
    
    public void setSslSmtpPort(String sslSmtpPort) {
        this.sslSmtpPort = sslSmtpPort;
    }

    
    public boolean isStartTLSRequired() {
        return StartTLSRequired;
    }

    public void setStartTLSRequired(boolean StartTLSRequired) {
        this.StartTLSRequired = StartTLSRequired;
    }

    
    public List<File> getListaAnexos() {
    	if(this.listaAnexos == null){
            this.listaAnexos = new ArrayList<File>();
        }
    	return listaAnexos;
    }
    
    
    public List<String> getListaDestinatario() {
    	return listaDestinatarios;
    }
}
