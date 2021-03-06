package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import br.com.buch.core.entity.Empresa;
import br.com.buch.core.entity.Endereco;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.Estados;
import br.com.buch.core.service.ServiceEmpresa;
import br.com.buch.core.service.ServiceHospede;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class EmpresaBean extends GenericBean<Empresa, ServiceEmpresa> implements Serializable{
	
	public enum TipoFiltro{
		CODIGO("Código"), NOME("Nome");

		TipoFiltro(String label) {this.label = label;}
		
		private String label;
		
		public String getLabel(){return this.label;}
	}
	
	private static final long serialVersionUID = -7531048359021089980L;	
	private TipoFiltro filtro;
	private List<Hospede> hospedes;
	private ServiceHospede serviceHospede;
	
	private TabView tabView;
	
	public EmpresaBean() {
		super(new ServiceEmpresa());
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	public void filtrar(){
		try {
			this.entidades = this.service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	
	@Override
	public Empresa criarEntidade() {
		this.entidade = new Empresa();
		entidade.setDataCadastro(new Date());
		return entidade;
	}	
	
	
	public void consultaCepWebService(){
		if (!isVisualizando()) {
			Endereco endereco = service.consultaCepWebService(entidade.getEndereco().getCep());
			
			if(endereco != null){
				this.entidade.setEndereco(endereco);
			}
		}		
	}
	
	
	public void onTabChange(TabChangeEvent event) {		
        if (event.getTab().getId().equals("tabHospedes")){
        	if(serviceHospede == null){
    			serviceHospede = new ServiceHospede();
    		}
        	
        	try {
				this.hospedes = serviceHospede.buscarPorEmpresa(entidade);
			} catch (Exception e) {
				e.printStackTrace();
			}        	
        }        
    }
	
	
	@Override
	public void cancelar() {	
		super.cancelar();
		tabView.setActiveIndex(0);
		this.hospedes = null;
	}
	
		
	// =============================GET AND SET=====================================
		
	
	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
	public TipoFiltro getFiltro() {return filtro;}
	
	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	
	public Estados[] getEstados(){return Estados.values();}
	
	public List<Hospede> getHospedes() {return hospedes;}
	
	@Override
	public List<Empresa> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}

	
	public TabView getTabView() {return tabView;}

	public void setTabView(TabView tabView) {this.tabView = tabView;}
}
