package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.service.ServiceFormaPagamento;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@SessionScoped
public class FormaPagamentoBean extends GenericBean<FormaPagamento, ServiceFormaPagamento> implements Serializable{

	private static final long serialVersionUID = 8656047049480224440L;

	public enum TipoFiltro{
		NOME("Nome"),
		CODIGO("Codigo");
		
		
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
	
	
	public FormaPagamentoBean() {
		super(new ServiceFormaPagamento());
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
	public FormaPagamento criarEntidade() {
		return new FormaPagamento();
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
	public List<FormaPagamento> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	
}
