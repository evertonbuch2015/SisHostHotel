package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.Banco;

@FacesConverter(forClass = Banco.class)
public class BancoConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Banco) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Banco) {
        	Banco entity = (Banco) value;
            if (entity != null && entity instanceof Banco && entity.getIdCaixaBanco() != null) {
                uiComponent.getAttributes().put( entity.getIdCaixaBanco().toString(), entity);
                return entity.getIdCaixaBanco().toString();
            }
        }
        return "";
    }
}