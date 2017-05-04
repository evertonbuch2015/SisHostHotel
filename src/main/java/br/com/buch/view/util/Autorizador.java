package br.com.buch.view.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.buch.core.entity.Usuario;


public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	
	public void afterPhase(PhaseEvent event) {

		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();

		if ("/Login.xhtml".equals(nomePagina)) {
			return;
		}

		Usuario usuarioLogado = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuarioLogado");
		
		
		if(usuarioLogado == null) {
			return;
		}
		
		if(usuarioLogado != null) {
			return;
		}
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/Login");
		
		context.renderResponse();
		
	}

	
	public void beforePhase(PhaseEvent event) {
	}

	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}