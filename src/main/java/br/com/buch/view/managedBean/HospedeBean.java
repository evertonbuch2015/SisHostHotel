package br.com.buch.view.managedBean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.Estados;
import br.com.buch.core.service.ServiceHospede;


@ManagedBean
@SessionScoped
public class HospedeBean extends GenericBean<Hospede, ServiceHospede> {
	
	public HospedeBean() {
		super(new ServiceHospede());
	}

	public enum TipoFiltro{
		CODIGO("Código"), NOME("Nome");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private Integer idHospede;
	

	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {
		service.filtrarTabela(filtro, valorFiltro);
	}

	
	@Override
	public Hospede criarEntidade() {
		Hospede hospede = new Hospede();
		hospede.setDataCadastro(new Date());
		return hospede;
	}


	
	// =============================GET AND SET=====================================

	public TipoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}

	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}

	
	public Estados[] getEstados(){
		return Estados.values();
	}

		
	public Integer getIdHospede() {
		return idHospede;
	}
	
	public void setIdHospede(Integer idHospede) {
		this.idHospede = idHospede;
	}
	
	@Override
	public List<Hospede> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
