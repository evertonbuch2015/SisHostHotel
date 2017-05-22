package br.com.buch.view.managedBean;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.service.ServiceApartamento;

public class ApartamentoBean extends GenericBean<Apartamento, ServiceApartamento> {

	
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
		service.filtrarTabela(filtro, valorFiltro);
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

}
