package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.Empresa;

@FacesConverter(forClass = Empresa.class)
public class EmpresaConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Empresa) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Empresa) {
        	Empresa empresa = (Empresa) value;
            if (empresa != null && empresa instanceof Empresa && empresa.getIdEmpresa() != null) {
                uiComponent.getAttributes().put( empresa.getIdEmpresa().toString(), empresa);
                return empresa.getIdEmpresa().toString();
            }
        }
        return "";
    }
}