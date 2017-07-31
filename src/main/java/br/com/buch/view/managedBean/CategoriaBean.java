package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Categoria;
import br.com.buch.core.service.ServiceCategoria;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class CategoriaBean extends GenericBean<Categoria, ServiceCategoria> implements Serializable{

	private static final long serialVersionUID = -3984110632736420732L;

	public enum TipoFiltro{
		NOME("Nome"),
		CODIGO("Codigo");
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;
		
		public String getLabel(){return this.label;}
	}
	
	private TipoFiltro filtro;	
	
	
	public CategoriaBean() {
		super(new ServiceCategoria());
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
	public Categoria criarEntidade() {
		return new Categoria();
	}
	
	
	// =============================GET AND SET=====================================	
	
	
	public TipoFiltro getFiltro() {return filtro;}

	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
	
	@Override
	public List<Categoria> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
