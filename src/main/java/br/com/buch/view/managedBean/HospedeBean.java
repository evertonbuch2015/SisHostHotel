package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Empresa;
import br.com.buch.core.entity.Endereco;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.Estados;
import br.com.buch.core.enumerated.TipoFiltroHospede;
import br.com.buch.core.service.ServiceEmpresa;
import br.com.buch.core.service.ServiceHospede;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@ViewScoped
public class HospedeBean extends GenericBean<Hospede, ServiceHospede> implements Serializable{
	
	private static final long serialVersionUID = -6802914921786106522L;
	private TipoFiltroHospede filtro;	
	private ServiceEmpresa serviceEmpresa;
	
	public HospedeBean() {
		super(new ServiceHospede());
	}

	
	@PostConstruct
	public void init(){
		serviceEmpresa = new ServiceEmpresa();
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
	public Hospede criarEntidade() {
		Hospede hospede = new Hospede();
		hospede.setDataCadastro(new Date());
		return hospede;
	}


	public void consultaCepWebService(){
		if (!isVisualizando()) {
			Endereco endereco = service.consultaCepWebService(entidade.getEndereco().getCep());
			
			if(endereco != null){
				this.entidade.setEndereco(endereco);
			}
		}		
	}
	
	
	public List<Empresa> buscarEmpresas(String query){
		return serviceEmpresa.buscarPorNomeFantasia("%"+query+"%");
	}
	
	// =============================GET AND SET=====================================

	public TipoFiltroHospede getFiltro() {return filtro;}

	public void setFiltro(TipoFiltroHospede filtro) {this.filtro = filtro;}

	public TipoFiltroHospede[] tipoFiltros(){return TipoFiltroHospede.values();}

	
	public Estados[] getEstados(){return Estados.values();}
	
	
	@Override
	public List<Hospede> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}
}
