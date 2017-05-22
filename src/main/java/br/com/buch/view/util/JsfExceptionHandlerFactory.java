package br.com.buch.view.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;
	
	public JsfExceptionHandlerFactory(ExceptionHandlerFactory factory) {
		this.parent = factory;
	}
	
	
	@Override
	public ExceptionHandler getExceptionHandler() {
		return new JsfExceptionHandler(parent.getExceptionHandler());
	}
}
