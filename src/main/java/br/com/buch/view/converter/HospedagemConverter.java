package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.Hospedagem;


@FacesConverter(forClass = Hospedagem.class)
public class HospedagemConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Hospedagem) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Hospedagem) {
        	Hospedagem entity = (Hospedagem) value;
            if (entity != null && entity instanceof Hospedagem && entity.getIdHospedagem() != null) {
                uiComponent.getAttributes().put( entity.getIdHospedagem().toString(), entity);
                return entity.getIdHospedagem().toString();
            }
        }
        return "";
    }
}