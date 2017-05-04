package br.com.buch.view.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

public class ValidatorNumero implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent arg1, Object arg2){	
		if(arg2.toString().equals("")){
			((UIInput) arg1).setValid(false);
			context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN,"Campo deve ser Informado!",""));
			return;
		}
		
		if ((arg2 instanceof Integer)) {
			try {
				Integer.parseInt(arg2.toString());
				return;
			} catch (Exception e) {
				((UIInput) arg1).setValid(false);
				context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,"Campo deve ser Num�rico!",""));
				return;
			}
		}

		if ((arg2 instanceof String)) {

			try {
				Integer.parseInt((String) arg2);
				return;
			} catch (Exception e) {
				((UIInput) arg1).setValid(false);
				context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,"Campo deve ser Num�rico!",""));
				return;
			}
		}
	}

}
