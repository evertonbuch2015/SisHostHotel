package br.com.buch.view.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.Categoria;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.service.ServiceCategoria;
import br.com.buch.core.service.ServiceTarifario;
import br.com.buch.core.service.ServiceTipoTarifa;

@ManagedBean
@SessionScoped
public class TarifarioBean extends GenericBean<Tarifario, ServiceTarifario> {

	
	public enum TipoFiltro{
		CATEGORIA("Categoria"),
		TIPO_TARIFA("Tipo de Tarifa"),
		DATA("Data");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private Integer idTarifario;	
	
	
	public TarifarioBean() {
		super(new ServiceTarifario());
	}

	
	// =======================METODOS DO USUARIO=====================================
	
	@Override
	public void filtrar() {
		this.entidades = service.filtrarTabela(filtro, valorFiltro);
	}

	
	@Override
	public Tarifario criarEntidade() {
		return new Tarifario();
	}

	
	// =============================GET AND SET=====================================

	public TipoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}

	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}
		

	
	public Integer getIdTarifario() {
		return idTarifario;
	}
	
	public void setIdTarifario(Integer idTarifario) {
		this.idTarifario = idTarifario;
	}
	
	
	@Override
	public List<Tarifario> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	
	
	public List<Categoria> getCategorias(){
		return new ServiceCategoria().buscarTodos();
	}
	
	
	public List<TipoTarifa> getTipoTarifas(){
		return new ServiceTipoTarifa().buscarTodos();
	}
}