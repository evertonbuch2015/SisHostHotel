package br.com.buch.view.util;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.buch.core.entity.Usuario;
import br.com.buch.core.util.Constantes;


@ApplicationScoped
public class SessionControllerListener implements HttpSessionListener{
	
	public SessionControllerListener() {
		
	}
	
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {		
		
	}
	
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		Usuario u = (Usuario) se.getSession().getAttribute("usuarioLogado");
        if (u != null) {
             Constantes.getInstance().removeUsuarioLogado(u);             
        }
	}

}
