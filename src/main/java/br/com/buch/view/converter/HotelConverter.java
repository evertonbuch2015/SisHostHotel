package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.Hotel;

@FacesConverter(forClass = Hotel.class)
public class HotelConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Hotel) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Hotel) {
        	Hotel empresa = (Hotel) value;
            if (empresa != null && empresa instanceof Hotel && empresa.getIdHotel() != null) {
                uiComponent.getAttributes().put( empresa.getIdHotel().toString(), empresa);
                return empresa.getIdHotel().toString();
            }
        }
        return "";
    }
}