package br.com.buch.view.managedBean;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.Categoria;
import br.com.buch.core.service.ServiceCategoria;

@ManagedBean
@SessionScoped
public class CategoriaBean extends GenericBean<Categoria, ServiceCategoria> {

	
	public enum TipoFiltro{
		NOME("Nome");
		
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private Integer idEntidade;
	
	
	public CategoriaBean() {
		super(new ServiceCategoria());
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {
		service.filtrarTabela(filtro, valorFiltro);
	}

	
	@Override
	public Categoria criarEntidade() {
		return new Categoria();
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
	
	
	public Integer getIdEntidade() {
		return idEntidade;
	}
	
	public void setIdEntidade(Integer idEntidade) {
		this.idEntidade = idEntidade;
	}

	
	@Override
	public List<Categoria> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
