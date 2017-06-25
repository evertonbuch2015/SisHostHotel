package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.TipoFiltroHospede;
import br.com.buch.core.service.ServiceHospede;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class DialogHospedeBean implements Serializable{
	
	private static final long serialVersionUID = -754880717006615790L;
	
	private TipoFiltroHospede tipoFiltro;
	private String valorFiltro;
	private List<Hospede> hospedes;
	private ServiceHospede serviceHospede;
	
	
	
	public DialogHospedeBean() {
		serviceHospede = new ServiceHospede();
	}
	
	//--------------------------------	Métodos da View ------------------------------//
	
	
	public void pesquisar(){				
		try {
			this.hospedes = serviceHospede.filtrarTabela(tipoFiltro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	public void abrirDialog() {		
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 550);
        options.put("height", 550);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/dialogs/dialogHospede", options, null);
    }
	
	
	public void retornoDialog(Hospede hospede){
		RequestContext.getCurrentInstance().closeDialog(hospede);
	}
	
	
	//--------------------------------	GETs and SETs------------------------------//
	
	public List<Hospede> getHospedes() {
		if(this.hospedes == null){
			this.hospedes = new ArrayList<Hospede>();
		}
		return hospedes;
	}
	
	
	public TipoFiltroHospede getFiltro() {
		return tipoFiltro;
	}

	public void setFiltro(TipoFiltroHospede filtro) {
		this.tipoFiltro = filtro;
	}

	public TipoFiltroHospede[] tipoFiltros(){
		return TipoFiltroHospede.values();
	}


	public String getValorFiltro() {
		return valorFiltro;
	}
	
	public void setValorFiltro(String valorFiltro) {
		this.valorFiltro = valorFiltro;
	}
}
