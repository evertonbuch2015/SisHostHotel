package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.service.ServiceTipoTarifa;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class TipoTarifaBean extends GenericBean<TipoTarifa,ServiceTipoTarifa> implements Serializable{

	public enum TipoFiltro{
		CODIGO("Código"), NOME("Nome");
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;		
		
		public String getLabel(){return this.label;}
	}
	
	private static final long serialVersionUID = -1633365565029634032L;	
	private TipoFiltro filtro;
	
	
	public TipoTarifaBean() {
		super(new ServiceTipoTarifa());
	}
	
	// =======================METODOS DO USUARIO=====================================
	

	@Override
	public void filtrar() {
		try {
			this.entidades = service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	@Override
	public TipoTarifa criarEntidade() {
		return new TipoTarifa();
	}

	
	// =============================GET AND SET=====================================
	
	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
	public TipoFiltro getFiltro() {return filtro;}
	
	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	
	@Override
	public List<TipoTarifa> getEntidades() {	
		if (this.entidades == null)
			refresh();	
		return entidades;
	}
}
