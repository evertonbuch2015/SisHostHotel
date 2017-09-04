package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.entity.Recebimento;
import br.com.buch.core.entity.Recebimento.OrigemRecebimento;
import br.com.buch.core.enumerated.TipoFiltroRecebimento;
import br.com.buch.core.service.ServiceRecebimento;
import br.com.buch.core.util.Constantes;

@ManagedBean
@SessionScoped
public class RecebimentoBean extends GenericBean<Recebimento, ServiceRecebimento> implements Serializable{

	private static final long serialVersionUID = 4996140944326181060L;

	private TipoFiltroRecebimento filtro;
	
	public RecebimentoBean() {
		super(new ServiceRecebimento());
	}

	
	// =======================METODOS DO USUARIO=====================================
	
	@Override
	public void filtrar() {
		
	}

	
	@Override
	public Recebimento criarEntidade() {
		Recebimento recebimento = new Recebimento();
		recebimento.setOrigemRecebimento(OrigemRecebimento.ENTRADA_MANUAL);
		return recebimento;
	}
	
		
	// =============================GET AND SET=====================================
	
    
	public List<Banco> getBancos() {
		try {
			return Constantes.getInstance().getListaBancos();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<FormaPagamento> getFormasPagamento() {
		try {
			return Constantes.getInstance().getListaFormasPagamento();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<Recebimento> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
		
	public TipoFiltroRecebimento[] tipoFiltros(){return TipoFiltroRecebimento.values();}
	
	public TipoFiltroRecebimento getFiltro() {return filtro;}
	
	public void setFiltro(TipoFiltroRecebimento filtro) {this.filtro = filtro;}
}
