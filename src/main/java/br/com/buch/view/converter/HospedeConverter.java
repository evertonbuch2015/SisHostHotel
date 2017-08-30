package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.Hospede;

@FacesConverter(forClass = Hospede.class)
public class HospedeConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {        	 
            return (Hospede) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Hospede) {
        	Hospede entity = (Hospede) value;
            if (entity != null && entity instanceof Hospede && entity.getIdHospede() != null) {
                uiComponent.getAttributes().put( entity.getIdHospede().toString(), entity);
                return entity.getIdHospede().toString();
            }
        }
        return "";
    }
}