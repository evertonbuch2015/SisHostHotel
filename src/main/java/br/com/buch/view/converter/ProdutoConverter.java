package br.com.buch.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.buch.core.entity.Produto;


@FacesConverter(forClass = Produto.class)
public class ProdutoConverter  implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Produto) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Produto) {
        	Produto entity = (Produto) value;
            if (entity != null && entity instanceof Produto && entity.getIdProduto() != null) {
                uiComponent.getAttributes().put( entity.getIdProduto().toString(), entity);
                return entity.getIdProduto().toString();
            }
        }
        return "";
    }
}