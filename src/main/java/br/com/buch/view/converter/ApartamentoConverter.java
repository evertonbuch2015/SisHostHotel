package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.Apartamento;

@FacesConverter(forClass = Apartamento.class)
public class ApartamentoConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Apartamento) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Apartamento) {
        	Apartamento entity = (Apartamento) value;
            if (entity != null && entity instanceof Apartamento && entity.getIdApartamento() != null) {
                uiComponent.getAttributes().put( entity.getIdApartamento().toString(), entity);
                return entity.getIdApartamento().toString();
            }
        }
        return "";
    }
}