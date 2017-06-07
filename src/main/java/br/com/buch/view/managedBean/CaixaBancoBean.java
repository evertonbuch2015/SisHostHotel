package br.com.buch.view.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.CaixaBanco;
import br.com.buch.core.enumerated.TipoFiltroCaixaBanco;
import br.com.buch.core.service.ServiceCaixaBanco;

@ManagedBean
@SessionScoped
public class CaixaBancoBean extends GenericBean<CaixaBanco, ServiceCaixaBanco> {

	
	private TipoFiltroCaixaBanco filtro;
	
	public CaixaBancoBean() {
		super(new ServiceCaixaBanco());
	}

	
	// =======================METODOS DO USUARIO=====================================
	
	@Override
	public void filtrar() {
		
	}

	@Override
	public CaixaBanco criarEntidade() {
		return new CaixaBanco();
	}
	
	
	// =============================GET AND SET=====================================
	
	public TipoFiltroCaixaBanco[] tipoFiltros(){
		return TipoFiltroCaixaBanco.values();
	}
	
	public TipoFiltroCaixaBanco getFiltro() {
		return filtro;
	}
	
	public void setFiltro(TipoFiltroCaixaBanco filtro) {
		this.filtro = filtro;
	}
}