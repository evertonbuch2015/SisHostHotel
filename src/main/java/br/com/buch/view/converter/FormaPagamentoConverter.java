package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.FormaPagamento;

@FacesConverter(forClass = FormaPagamento.class)
public class FormaPagamentoConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (FormaPagamento) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof FormaPagamento) {
        	FormaPagamento entity = (FormaPagamento) value;
            if (entity != null && entity instanceof FormaPagamento && entity.getIdFormaPag() != null) {
                uiComponent.getAttributes().put( entity.getIdFormaPag().toString(), entity);
                return entity.getIdFormaPag().toString();
            }
        }
        return "";
    }
}