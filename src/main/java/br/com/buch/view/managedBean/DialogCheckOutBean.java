package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.util.Constantes;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class DialogCheckOutBean implements Serializable{
		
	private static final long serialVersionUID = -3871976865283686929L;	
	private List<Apartamento> apartamentos;	
	
	
	public DialogCheckOutBean() {		
		try {
			this.apartamentos = Constantes.getInstance().getListaApartamentos();
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	//--------------------------------	Métodos da View ------------------------------//
	
	
	public void abrirDialog() {		
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 550);
        options.put("height", 550);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/dialogs/dialogApartamento", options, null);
    }
	
	
	public void retornoDialog(Apartamento entidade){
		RequestContext.getCurrentInstance().closeDialog(entidade);
	}
	
	
	//--------------------------------	GETs and SETs------------------------------//
	
	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}
}
