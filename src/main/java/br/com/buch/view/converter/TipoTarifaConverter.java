package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.TipoTarifa;

@FacesConverter(forClass = TipoTarifa.class)
public class TipoTarifaConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (TipoTarifa) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TipoTarifa) {
        	TipoTarifa entity = (TipoTarifa) value;
            if (entity != null && entity instanceof TipoTarifa && entity.getIdTipoTarifa() != null) {
                uiComponent.getAttributes().put( entity.getIdTipoTarifa().toString(), entity);
                return entity.getIdTipoTarifa().toString();
            }
        }
        return "";
    }
}