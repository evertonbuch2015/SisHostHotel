package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Consumo;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Produto;
import br.com.buch.core.service.ServiceConsumo;
import br.com.buch.core.service.ServiceProduto;
import br.com.buch.core.util.Constantes;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class ConsumoBean extends GenericBean<Consumo, ServiceConsumo> implements Serializable{

	private static final long serialVersionUID = -3984110632736420732L;
	private Date dataFiltroInicial;
	private Date dataFiltroFinal;
	private ServiceProduto serviceProduto;
	
	
	public enum TipoFiltro{
		DATA("Data"),
		HOSPEDAGEM("Hospedagem");
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;
		
		public String getLabel(){return this.label;}
	}
	
	private TipoFiltro filtro;	
	
	
	public ConsumoBean() {
		super(new ServiceConsumo());
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {
		try {
			if(filtro == TipoFiltro.DATA){
				
				if(this.dataFiltroInicial != null && this.dataFiltroFinal == null){
					this.entidades = service.filtrarTabela(filtro, dataFiltroInicial);
				}
				else if(this.dataFiltroInicial != null && this.dataFiltroFinal != null){
					this.entidades = service.filtrarTabela(filtro, dataFiltroInicial, dataFiltroFinal);
				}
			}else{
				this.entidades = service.filtrarTabela(filtro, valorFiltro);	
			}			
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	
	@Override
	public Consumo criarEntidade() {
		Consumo consumo = new Consumo();
		consumo.setDataConsumo(new Date());
		return consumo;
	}
	
	
	public List<Hospedagem> buscarHospedagens(String query){				
		try{
			return Constantes.getInstance().getHospedagensAtivas();
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
			return null;
		}
	}
	
	
	public void produtoSelecionado(SelectEvent event){
		Produto produto = (Produto) event.getObject();	
		this.entidade.setProduto(produto);
	}
	
	
	public List<Produto> buscarProdutos(String query){
		if (query != null && query.length() < 3){
			return null;
		}
		
		if(serviceProduto == null){
			serviceProduto = new ServiceProduto();
		}
		
		try{
			return serviceProduto.buscarPorNome("%"+query+"%");
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
			return null;
		}
	}
	// =============================GET AND SET=====================================	
	
	
	public TipoFiltro getFiltro() {return filtro;}

	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
		
	public Date getDataFiltroInicial() {return dataFiltroInicial;}
	public void setDataFiltroInicial(Date dataFiltroInicial) {this.dataFiltroInicial = dataFiltroInicial;}

	public Date getDataFiltroFinal() {return dataFiltroFinal;}
	public void setDataFiltroFinal(Date dataFiltroFinal) {this.dataFiltroFinal = dataFiltroFinal;}


	@Override
	public List<Consumo> getEntidades(){
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
