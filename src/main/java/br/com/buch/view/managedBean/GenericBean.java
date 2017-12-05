package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.service.GenericService;
import br.com.buch.core.util.NegocioException;
import br.com.buch.view.util.UtilMensagens;


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
		try {
			String mensagem = service.salvar(this.entidade);
			refresh();
			mudarBuscar();
			
			UtilMensagens.mensagemInformacao(mensagem);
		}
		catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	public void excluir(){
		try {
			service.excluir(entidade);
			refresh();
			mudarBuscar();
			
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
		}
		catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
		
	}
	
	
	public void excluir(E entity){
		try {
			service.excluir(entity);			
			
			UtilMensagens.mensagemInformacao("Exclusão Realizada com Sucesso");
		}
		catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}finally {
			refresh();
			mudarBuscar();
		}
	}
		
	
	public  void refresh(){				
		try {			
			
			if(this.entidades != null){
				this.entidades.clear();
			}
			this.entidades = service.buscarTodos();
			
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	protected E carregaEntidade(E entidade)throws Exception{
		return service.carregarEntidade(entidade);
	}

	
	public void novo(){
		this.entidade = criarEntidade();
		mudarInserir();
	}
	
	
	public void editar(){
		try {
			service.consisteAntesEditar(entidade);
			mudarAlterar();
			
		}
		catch (NegocioException ex) {
			UtilMensagens.mensagemAtencao(ex.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
		
	public void editar(E entidade){					
		try {
			service.consisteAntesEditar(entidade);
			
			this.entidade = carregaEntidade(entidade);			
			mudarAlterar();
			
		}
		catch (NegocioException ex) {
			UtilMensagens.mensagemAtencao(ex.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	

	public void cancelar(){
		this.entidade = null;
		mudarBuscar();
	}
	
	
	public void visualizar(){
		try {			
			if(this.entidade != null){
				this.entidade = carregaEntidade(entidade);
				mudarVisualizar();
			}else{
				UtilMensagens.mensagemInformacao("Selecione um registro da lista!");
			}
			
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}		
	}
	
			
	@SuppressWarnings("unchecked")
	public void onRowSelect(SelectEvent event) {		
		this.entidade = (E) event.getObject();
	}
	
	
	public void onRowDblClckSelect(final SelectEvent event) {
		visualizar();	
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
