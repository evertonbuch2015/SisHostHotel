package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.enumerated.Estados;
import br.com.buch.core.enumerated.RegimeTributario;
import br.com.buch.core.service.ServiceHotel;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class HotelBean extends GenericBean<Hotel, ServiceHotel> implements Serializable{
	
	private static final long serialVersionUID = -7531048359021089980L;

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
		try {
			this.entidades = this.service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
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

	
	@Override
	public List<Hotel> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
