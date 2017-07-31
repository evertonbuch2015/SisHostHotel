package br.com.buch.view.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Banco;
import br.com.buch.core.enumerated.TipoFiltroBanco;
import br.com.buch.core.service.ServiceBanco;

@ManagedBean
@ViewScoped
public class BancoBean extends GenericBean<Banco, ServiceBanco> implements Serializable{

	private static final long serialVersionUID = 4996140944326181060L;

	private TipoFiltroBanco filtro;
	
	public BancoBean() {
		super(new ServiceBanco());
	}

	
	// =======================METODOS DO USUARIO=====================================
	
	@Override
	public void filtrar() {
		
	}

	
	@Override
	public Banco criarEntidade() {
		return new Banco();
	}
	
	
	// =============================GET AND SET=====================================
	
	public TipoFiltroBanco[] tipoFiltros(){return TipoFiltroBanco.values();}
	
	public TipoFiltroBanco getFiltro() {return filtro;}
	
	public void setFiltro(TipoFiltroBanco filtro) {this.filtro = filtro;}
}
