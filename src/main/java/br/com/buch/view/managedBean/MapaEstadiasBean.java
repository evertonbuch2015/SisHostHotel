package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.util.Constantes;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@ViewScoped
public class MapaEstadiasBean implements Serializable{

	private static final long serialVersionUID = -6856740750834658985L;
	private List<Apartamento> apartamentos;
	
	
	@PostConstruct
    public void init() {
		atualizarApartamentos();
    }
	
	
	public void atualizarApartamentos(){
		try {
			this.apartamentos = Constantes.getInstance().getListaApartamentos();
		} catch (Exception e) {			
			this.apartamentos = new ArrayList<Apartamento>();
			UtilMensagens.mensagemErro(UtilMensagens.MSM_ERRO_INTERNO);
		}
	}
	
	
	public List<Apartamento> getApartamentos() {return apartamentos;}

}
