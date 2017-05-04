package br.com.buch.view.managedBean;

import java.util.List;

import org.primefaces.event.SelectEvent;


public abstract class GenericBean<E> {
	
	public enum EstadoTela {
		INSERINDO, ALTERANDO, BUSCANDO, VISUALIZANDO
	}
	
	protected String valorFiltro;
	protected E entidade;
	protected List<E> entidades;
	private EstadoTela estadoTela = EstadoTela.BUSCANDO;
	
	
	// ================M�todos para controlar e consultar o estado da Tela.============
	public boolean isInserindo() {
		return estadoTela.equals(EstadoTela.INSERINDO);
	}

	public boolean isAlterando() {
		return estadoTela.equals(EstadoTela.ALTERANDO);
	}

	public boolean isBuscando() {
		return estadoTela.equals(EstadoTela.BUSCANDO);
	}
	
	public boolean isVisualizando() {
		return estadoTela.equals(EstadoTela.VISUALIZANDO);
	}

	
	// ================M�todos para alterar o estado da Tela.==========================
	public void mudarInserir() {
		this.estadoTela = EstadoTela.INSERINDO;
	}

	public void mudarAlterar() {
		this.estadoTela = EstadoTela.ALTERANDO;
	}

	public void mudarBuscar() {
		this.estadoTela = EstadoTela.BUSCANDO;
	}
	
	public void mudarVisualizar() {
		this.estadoTela = EstadoTela.VISUALIZANDO;
	}
	
	
	
	// ================Metodos a serem Implementados==================================
	
	public abstract void filtrar();
	
	public abstract E criarEntidade();
	
	public abstract void gravar();
	
	public abstract void excluir(E entidade);
	
	public abstract void refresh();
	
	public abstract void carregaEntidade();

	
	// ================Metodos j� implementados (Prontos)=============================
	public void novo(){
		this.entidade = criarEntidade();
		mudarInserir();
	}
	
	
	public void editar(){
		mudarAlterar();
	}
	
	
	public void editar(E entidade){
		this.entidade = entidade;
		carregaEntidade();
		mudarAlterar();
	}
	

	public void cancelar(){
		this.entidade = null;
		mudarBuscar();
	}
	
		
	public void onRowSelect(SelectEvent event) {		
		//this.entidade = (E) event.getObject();
	}
	
	
	public void onRowDblClckSelect(final SelectEvent event) {
		carregaEntidade();
		if(this.entidade != null){
			mudarVisualizar();
		}
	}
	

	// ================M�todos GET e SET=============================================
	public void setEntidade(E entidade) {
		this.entidade = entidade;
	}
	
	public E getEntidade() {
		return entidade;
	}
	
	
	public void setEntidades(List<E> entidades) {
		this.entidades = entidades;
	}
	
	public List<E> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	

	public String getValorFiltro() {
		return valorFiltro;
	}
	
	public void setValorFiltro(String valorFiltro) {
		this.valorFiltro = valorFiltro;
	}
}
