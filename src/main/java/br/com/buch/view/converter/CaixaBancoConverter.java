package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.CaixaBanco;


@FacesConverter(forClass = CaixaBanco.class)
public class CaixaBancoConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (CaixaBanco) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof CaixaBanco) {
        	CaixaBanco entity = (CaixaBanco) value;
            if (entity != null && entity instanceof CaixaBanco && entity.getIdCaixaBanco() != null) {
                uiComponent.getAttributes().put( entity.getIdCaixaBanco().toString(), entity);
                return entity.getIdCaixaBanco().toString();
            }
        }
        return "";
    }
}