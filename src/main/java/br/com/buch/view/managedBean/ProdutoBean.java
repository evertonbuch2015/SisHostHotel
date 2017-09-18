package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.Produto;
import br.com.buch.core.enumerated.Unidade;
import br.com.buch.core.service.ServiceProduto;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@SessionScoped
public class ProdutoBean extends GenericBean<Produto, ServiceProduto> implements Serializable{

	private static final long serialVersionUID = -3984110632736420732L;

	public enum TipoFiltro{
		NOME("Nome"),
		CODIGO("Codigo");
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;
		
		public String getLabel(){return this.label;}
	}
	
	private TipoFiltro filtro;	
	
	
	public ProdutoBean() {
		super(new ServiceProduto());
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
	public Produto criarEntidade() {
		Produto produto = new Produto();
		produto.setAtivo('S');
		produto.setDataCadastro(new Date());
		
		return produto;
	}
	
	
	// =============================GET AND SET=====================================	
	
	
	public TipoFiltro getFiltro() {return filtro;}

	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
	public Unidade[] getUnidades(){
		return Unidade.values();
	}
	
	@Override
	public List<Produto> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
