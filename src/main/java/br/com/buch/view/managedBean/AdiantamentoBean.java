package br.com.buch.view.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.CaixaBanco;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.TipoFiltroAdiantamento;
import br.com.buch.core.service.ServiceAdiantamento;
import br.com.buch.core.service.ServiceCaixaBanco;

@ManagedBean
@SessionScoped
public class AdiantamentoBean extends GenericBean<Adiantamento, ServiceAdiantamento> {

	
	private TipoFiltroAdiantamento filtro;	
	
	
	public AdiantamentoBean() {
		super(new ServiceAdiantamento());
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {
		service.filtrarTabela(filtro, valorFiltro);
	}

	@Override
	public Adiantamento criarEntidade() {
		return new Adiantamento();
	}

	
	
	public void hospedeSelecionado(SelectEvent event){
		Hospede hospede = (Hospede) event.getObject();	
		this.entidade.setHospede(hospede);;
	}
		
	
	
	// =============================GET AND SET=====================================
	
	
	public TipoFiltroAdiantamento[] tipoFiltros(){
		return TipoFiltroAdiantamento.values();
	}
	
	public TipoFiltroAdiantamento getFiltro() {
		return filtro;
	}
	
	public void setFiltro(TipoFiltroAdiantamento filtro) {
		this.filtro = filtro;
	}
	
	
	public List<CaixaBanco> getLocaisRecebimento(){
		return new ServiceCaixaBanco().buscarTodos();
	}
	
	
	@Override
	public List<Adiantamento> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
