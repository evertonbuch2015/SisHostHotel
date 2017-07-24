package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Categoria;
import br.com.buch.core.enumerated.SituacaoApartamento;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.core.service.ServiceCategoria;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class ApartamentoBean extends GenericBean<Apartamento, ServiceApartamento> implements Serializable{

	private static final long serialVersionUID = 6409242364742848848L;

	public enum TipoFiltro{
		CODIGO("Código"), 
		SITUACAO("Situação"),
		CATEGORIA("Categoria");
		
		
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
	
		
	public ApartamentoBean() {
		super(new ServiceApartamento());
	}
	
	
	
	// =======================METODOS DO USUARIO=====================================
	

	@Override
	public void filtrar() {
		try {
			service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	
	@Override
	public Apartamento criarEntidade() {
		return new Apartamento();
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

	

	public SituacaoApartamento[] situacaoApartamentos(){
		return SituacaoApartamento.values();
	}
	
	
	public List<Categoria> getCategorias(){
		try {
			return new ServiceCategoria().buscarTodos();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public List<Apartamento> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
