package br.com.buch.view.managedBean;

import java.util.Date;

import br.com.buch.core.entity.Hospede;
import br.com.buch.core.service.ServiceHospede;

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
	
	

	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {
		// TODO Auto-generated method stub
		
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
}
