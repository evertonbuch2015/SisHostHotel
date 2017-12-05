package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Categoria;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.service.ServiceTarifario;
import br.com.buch.core.util.NegocioException;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class TarifarioBean extends GenericBean<Tarifario, ServiceTarifario> implements Serializable{
	
	private static final long serialVersionUID = 7612562993588960200L;
	
	public enum TipoFiltro{
		CATEGORIA("Categoria"),
		TIPO_TARIFA("Tipo de Tarifa"),
		DATA("Data Inicial");
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;		
		
		public String getLabel(){return this.label;}
	}
	
	//Filtros
	private TipoFiltro filtro;	
	private Categoria categoriaFiltro;
	private TipoTarifa tipoTarifaFiltro;
	private Date dataFiltro;
	private Date dataFiltroFinal;
	
	
	public TarifarioBean() {
		super(new ServiceTarifario());
	}

	
	// =======================METODOS DO USUARIO=====================================
	
	@Override
	public void filtrar() {
		try {			
			if(filtro == TipoFiltro.DATA){
				
				if(this.dataFiltro != null && this.dataFiltroFinal == null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro);
				}
				else if(this.dataFiltro != null && this.dataFiltroFinal != null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro, dataFiltroFinal);
				}
			}
			else if(filtro == TipoFiltro.CATEGORIA){
				this.entidades = service.filtrarTabela(filtro, categoriaFiltro);
			}
			else if(filtro == TipoFiltro.TIPO_TARIFA){
				this.entidades = service.filtrarTabela(filtro, tipoTarifaFiltro);
			}
		}catch(NegocioException e){
			UtilMensagens.mensagemAtencao(e.getMessage());
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}finally {
			dataFiltro       = null; 	dataFiltroFinal  = null;
			categoriaFiltro  = null;	tipoTarifaFiltro = null;
		}
	}

	
	@Override
	public Tarifario criarEntidade() {
		return new Tarifario();
	}

	
	// =============================GET AND SET=====================================

	public TipoFiltro getFiltro() {return filtro;}
	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}
	
	public Categoria getCategoriaFiltro() {return categoriaFiltro;}
	public void setCategoriaFiltro(Categoria categoriaFiltro) {this.categoriaFiltro = categoriaFiltro;}
	
	public void setTipoTarifaFiltro(TipoTarifa tipoTarifaFiltro) {this.tipoTarifaFiltro = tipoTarifaFiltro;}		
	public TipoTarifa getTipoTarifaFiltro() {return tipoTarifaFiltro;}
	
	public Date getDataFiltro() {return dataFiltro;}
	public void setDataFiltro(Date dataFiltro) {this.dataFiltro = dataFiltro;}
	
	public Date getDataFiltroFinal() {return dataFiltroFinal;}	
	public void setDataFiltroFinal(Date dataFiltroFinal) {this.dataFiltroFinal = dataFiltroFinal;}

	
	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
	@Override
	public List<Tarifario> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}

}
