package br.com.buch.view.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.CaixaBanco;
import br.com.buch.core.enumerated.TipoFiltroCaixaBanco;
import br.com.buch.core.service.ServiceCaixaBanco;

@ManagedBean
@ViewScoped
public class CaixaBancoBean extends GenericBean<CaixaBanco, ServiceCaixaBanco> implements Serializable{

	private static final long serialVersionUID = 4996140944326181060L;

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
