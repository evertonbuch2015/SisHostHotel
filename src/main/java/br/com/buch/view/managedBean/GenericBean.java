package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.service.GenericService;


public abstract class GenericBean<E extends Serializable,T extends GenericService<E>> {
	
	public enum EstadoTela {
		INSERINDO, ALTERANDO, BUSCANDO, VISUALIZANDO
	}
	
	protected String valorFiltro;
	protected E entidade;
	protected List<E> entidades;
	private EstadoTela estadoTela = EstadoTela.BUSCANDO;
	
	protected T service;
	
	public GenericBean(T service) {
		this.service = service;
	}
	
	// ================Métodos para controlar e consultar o estado da Tela.===========
	
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
	
	
	// ================Metodos já implementados (Prontos)=============================
	
	public void gravar(){
		if (service.salvar(this.entidade)) {			
			refresh();
			mudarBuscar();
		}	
	}
	
	
	public void excluir(){
		service.excluir(entidade);
		refresh();
		mudarBuscar();
	}
	
	
	public void excluir(E entity){
		service.excluir(entity);
		refresh();
		mudarBuscar();
	}
		
	
	public  void refresh(){
		if(this.entidades != null){
			this.entidades.clear();
		}
		this.entidades = service.buscarTodos();
	}
	
	
	public void carregaEntidade(){
		this.entidade = service.carregarEntidade(entidade);
	}

	
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
	

	// ================Métodos GET e SET=============================================
	public void setEntidade(E entidade) {
		this.entidade = entidade;
	}
	
	public E getEntidade() {
		if(this.entidade == null){
			this.entidade = criarEntidade();
		}
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
