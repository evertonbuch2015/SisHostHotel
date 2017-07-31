package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Categoria;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.service.ServiceTarifario;
import br.com.buch.core.util.Constantes;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class TarifarioBean extends GenericBean<Tarifario, ServiceTarifario> implements Serializable{

	public enum TipoFiltro{
		CATEGORIA("Categoria"),
		TIPO_TARIFA("Tipo de Tarifa"),
		DATA("Data");
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;		
		
		public String getLabel(){return this.label;}
	}
	
	private static final long serialVersionUID = 7612562993588960200L;
	private TipoFiltro filtro;	
	
	
	public TarifarioBean() {
		super(new ServiceTarifario());
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
	public Tarifario criarEntidade() {
		return new Tarifario();
	}

	
	// =============================GET AND SET=====================================

	public TipoFiltro getFiltro() {return filtro;}

	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}

	
	@Override
	public List<Tarifario> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}
	
	public List<Categoria> getCategorias(){
		try {
			return Constantes.getInstance().getListaCategorias();
		} catch (Exception e) {
			UtilMensagens.mensagemErro(UtilMensagens.MSM_ERRO_INTERNO);
			return new ArrayList<>();
		}
	}	
	
	public List<TipoTarifa> getTipoTarifas(){
		try {
			return Constantes.getInstance().getListaTiposTarifa();
		} catch (Exception e) {
			UtilMensagens.mensagemErro(UtilMensagens.MSM_ERRO_INTERNO);
			return new ArrayList<>();
		}
	}
}
