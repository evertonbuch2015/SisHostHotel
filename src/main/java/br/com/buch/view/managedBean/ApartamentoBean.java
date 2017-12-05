package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Categoria;
import br.com.buch.core.enumerated.SituacaoApartamento;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class ApartamentoBean extends GenericBean<Apartamento, ServiceApartamento> implements Serializable{

	public enum TipoFiltro{
		CODIGO("Código"), 
		SITUACAO("Situação"),
		CATEGORIA("Categoria");
		
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;	
		
		public String getLabel(){return this.label;}
	}
	
	private static final long serialVersionUID = 6409242364742848848L;	
	private TipoFiltro filtro;	
	private SituacaoApartamento situacaoFiltro;
	private Categoria categoriaFiltro;
	
	public ApartamentoBean() {
		super(new ServiceApartamento());		
	}
		
	
	// =======================METODOS DO USUARIO=====================================
	

	@Override
	public void filtrar() {
		try {
			if(filtro == TipoFiltro.SITUACAO){
				this.entidades = service.filtrarTabela(filtro, situacaoFiltro);
			}else if(filtro == TipoFiltro.CATEGORIA){
				this.entidades = service.filtrarTabela(filtro, categoriaFiltro);
			}else if(filtro != null){
				this.entidades = service.filtrarTabela(filtro, valorFiltro);
			}			
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	
	@Override
	public Apartamento criarEntidade() {
		return new Apartamento();
	}

	
	// =============================GET AND SET=====================================	

	
	public TipoFiltro getFiltro() {return filtro;}
	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
	public Categoria getCategoriaFiltro() {return categoriaFiltro;}
	public void setCategoriaFiltro(Categoria categoriaFiltro) {this.categoriaFiltro = categoriaFiltro;}
	
	public SituacaoApartamento getSituacaoFiltro() {return situacaoFiltro;}
	public void setSituacaoFiltro(SituacaoApartamento situacaoFiltro) {this.situacaoFiltro = situacaoFiltro;}
	
	
	public SituacaoApartamento[] situacaoApartamentos(){return SituacaoApartamento.values();}
	
	
	@Override
	public List<Apartamento> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}
}
