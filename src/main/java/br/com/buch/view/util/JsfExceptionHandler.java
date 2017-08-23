package br.com.buch.view.util;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;
	
	
	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	
	@Override
	public javax.faces.context.ExceptionHandler getWrapped() {
		return this.wrapped;
	}
	
	
	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		
		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			Throwable throwable = context.getException();
			
			boolean handled = false;
			try {				
				if(throwable instanceof ViewExpiredException){					
					handled = true;
					redirect("/login.xhtml");									
				}else{
					handled = true;
					throwable.printStackTrace();
					redirect("/erros.xhtml?faces-redirect=true");
				}					
			} finally {
				if (handled) {
					events.remove();
				}					
			}			
		}
		getWrapped().handle();
	}


	private void redirect(String page) {
		try{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		
		String contexPath = externalContext.getRequestContextPath();
		externalContext.redirect(contexPath + page);
		facesContext.responseComplete();
		}catch (Exception e) {
			throw new FacesException(e);
		}
		
	}

}
