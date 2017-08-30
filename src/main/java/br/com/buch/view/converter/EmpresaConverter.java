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
        	Empresa emp =(Empresa) uiComponent.getAttributes().get(value); 
            return emp;
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Empresa) {
        	Empresa entity = (Empresa) value;
            if (entity != null && entity instanceof Empresa && entity.getIdEmpresa() != null) {
                uiComponent.getAttributes().put( entity.getIdEmpresa().toString(), entity);
                return entity.getIdEmpresa().toString();
            }
        }
        return "";
    }
}