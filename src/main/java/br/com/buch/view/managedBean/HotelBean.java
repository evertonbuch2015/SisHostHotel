package br.com.buch.view.managedBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.enumerated.Estados;
import br.com.buch.core.enumerated.RegimeTributario;
import br.com.buch.core.service.ServiceHotel;

@ManagedBean
@SessionScoped
public class HotelBean extends GenericBean<Hotel> {
	
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
	private ServiceHotel serviceHotel;

	
	public HotelBean() {
		serviceHotel = new ServiceHotel();
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	public void filtrar(){
		this.entidades = serviceHotel.filtrarTabela(filtro, valorFiltro);
	}
	
	
	@Override
	public void carregaEntidade() {
	
	}
	
	
	@Override
	public Hotel criarEntidade() {
		this.entidade = new Hotel();
		entidade.setDataCadastro(new Date());
		return entidade;
	}

	
	@Override
	public void refresh() {
		if(this.entidades != null){
			this.entidades.clear();
		}
		this.entidades = serviceHotel.buscarTodos();
	}
	
	
	@Override
	public void gravar() {
		if (serviceHotel.salvar(this.entidade)) {			
			refresh();
			mudarBuscar();
		}	
	}

	
	@Override
	public void excluir(Hotel entidade) {
		serviceHotel.excluir(entidade);
		refresh();
		mudarBuscar();
	}

	
	public void AbrirDialogHotel() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentHeight", 450);				
        RequestContext.getCurrentInstance().openDialog("/dialogosSelecao/SelecaoEmpresa", options, null);
    }
	
	
	public void retornoDialog(Hotel empresa){
		RequestContext.getCurrentInstance().closeDialog(empresa);
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
