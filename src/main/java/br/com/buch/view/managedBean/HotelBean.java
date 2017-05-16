package br.com.buch.view.managedBean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.enumerated.Estados;
import br.com.buch.core.enumerated.RegimeTributario;
import br.com.buch.core.service.ServiceHotel;

@ManagedBean
@SessionScoped
public class HotelBean extends GenericBean<Hotel, ServiceHotel> {
	
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
	private Integer idHotel;

	
	public HotelBean() {
		super(new ServiceHotel());
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	public void filtrar(){
		this.entidades = this.service.filtrarTabela(filtro, valorFiltro);
	}

	
	
	@Override
	public Hotel criarEntidade() {
		this.entidade = new Hotel();
		entidade.setDataCadastro(new Date());
		return entidade;
	}	
	
	// =============================GET AND SET=====================================

	public Integer getIdHotel() {
		return idHotel;
	}
	
	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
	}
	
	
	public RegimeTributario[] getRegimes(){
		return RegimeTributario.values();
	}
	
	
	public Estados[] getEstados(){
		return Estados.values();
	}

	
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}
	
	public TipoFiltro getFiltro() {
		return filtro;
	}
	
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}
}
