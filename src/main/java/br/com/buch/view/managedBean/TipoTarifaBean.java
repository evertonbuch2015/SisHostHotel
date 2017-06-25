package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.service.ServiceTipoTarifa;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@SessionScoped
public class TipoTarifaBean extends GenericBean<TipoTarifa,ServiceTipoTarifa> implements Serializable{

	private static final long serialVersionUID = -1633365565029634032L;

	public enum TipoFiltro{
		CODIGO("Código"), NOME("Nome");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	
	private TipoFiltro filtro;	
	private Integer idTipotarifa;
	
	
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
	
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}
	
	public TipoFiltro getFiltro() {
		return filtro;
	}
	
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}

	
	public Integer getIdTipotarifa() {
		return idTipotarifa;
	}
	
	public void setIdTipotarifa(Integer idTipotarifa) {
		this.idTipotarifa = idTipotarifa;
	}
	
	
	@Override
	public List<TipoTarifa> getEntidades() {	
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
