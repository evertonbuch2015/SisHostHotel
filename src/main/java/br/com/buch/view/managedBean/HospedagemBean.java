package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.enumerated.TipoFiltroHospedagem;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.core.service.ServiceHospedagem;
import br.com.buch.core.service.ServiceTarifario;
import br.com.buch.core.service.ServiceTipoTarifa;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@SessionScoped
public class HospedagemBean extends GenericBean<Hospedagem, ServiceHospedagem> implements Serializable{


	private static final long serialVersionUID = -2119605666827486172L;

	private TipoFiltroHospedagem filtro;	
	private ServiceApartamento serviceApartamento;
	private ServiceTarifario serviceTarifario;
	private TipoTarifa tipoTarifa;
	private boolean tarifaManual;	
	private String descricaoApartamento;
	
	
	public HospedagemBean() {
		super(new ServiceHospedagem());
	}

	@PostConstruct
	public void init(){
		serviceApartamento = new ServiceApartamento();
	}
	
	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {

	}

		
	@Override
	public Hospedagem criarEntidade() {
		Hospedagem hospedagem = new Hospedagem();
		hospedagem.setSituacao(SituacaoHospedagem.CHECKIN);
		return hospedagem;
	}

	
	public void hospedeSelecionado(SelectEvent event){
		Hospede hospede = (Hospede) event.getObject();	
		this.entidade.setHospede(hospede);;
	}
	
	
	public void apartamentoSelecionado(SelectEvent event){
		Apartamento apartamento = (Apartamento) event.getObject();	
		this.entidade.setApartamento(apartamento);
		
		verificaDisponibilidadeApartamento();
		buscarTarifario();
	}
	
	
	public void onDataEntradaSelect(SelectEvent event) {
		verificaDisponibilidadeApartamento();
    }
	
	public void onDataSaidaSelect(SelectEvent event) {
		verificaDisponibilidadeApartamento();
    }
	
	
	public void onTipoTarifaChange(){
		buscarTarifario();
	}
	
	
	private void buscarTarifario(){
		
		if(tipoTarifa == null){
			return;
		}
		else if(tipoTarifa.getTarifaManual()){
			tarifaManual = true;
			return;
		}		
		
		if(entidade.getApartamento() == null) return;
		
		if(entidade.getDataEntrada() == null) return;
				
		try {
			Tarifario tarifario = 
					getServiceTarifario().buscarPelaCategoriaTipoTarifa(
							entidade.getApartamento().getCategoria().getIdCategoria(),
							tipoTarifa.getIdTipoTarifa(),
							entidade.getDataEntrada());
						
			this.entidade.setValorDiaria(tarifario.getValor());			
			
		}
		catch (NegocioException e) {
			UtilMensagens.mensagemInformacao(e.getMessage());
			entidade.setValorDiaria(null);
		} 
		catch (Exception e) {			
			entidade.setValorDiaria(null);
		}
	}
	
	
	private void verificaDisponibilidadeApartamento(){
		
		if(entidade.getDataEntrada() == null || 
				entidade.getDataSaida() == null ||
					entidade.getApartamento() == null){
			return;
		}
		
		
		try {
			serviceApartamento.verificaDisponibilidade(
									entidade.getApartamento().getIdApartamento(),
									entidade.getDataEntrada(),
									entidade.getDataSaida());
			
		} catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
			entidade.setDataEntrada(null);
			entidade.setDataSaida(null);
		}
		
	}
	
	
	// =============================GET AND SET=====================================

	
	public TipoFiltroHospedagem getFiltro() {return filtro;}
	
	public void setFiltro(TipoFiltroHospedagem filtro) {this.filtro = filtro;}
	
	public TipoFiltroHospedagem[] tipoFiltros(){return TipoFiltroHospedagem.values();}

	
	public List<TipoTarifa> tiposTarifa(){return new ServiceTipoTarifa().buscarTodos();}
	
	public TipoTarifa getTipoTarifa() {return tipoTarifa;}
	
	public void setTipoTarifa(TipoTarifa tipoTarifa) {this.tipoTarifa = tipoTarifa;}	
	
	public boolean isTarifaManual() {return tarifaManual;}
	
	public String getDescricaoApartamento() {
		if(entidade.getApartamento() != null){
			this.descricaoApartamento = "Nº: " + entidade.getApartamento().getNumero() + "  -  " + entidade.getApartamento().getCategoria().getNome();
		}	
		return descricaoApartamento;
	}
	
	public void setDescricaoApartamento(String descricaoApartamento) {
		this.descricaoApartamento = descricaoApartamento;
	}	
	
	
	@Override
	public List<Hospedagem> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	
	
	public List<Apartamento> apartamentos(){
		try {
			return serviceApartamento.buscarTodos();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return new ArrayList<Apartamento>();
		}
	}
	
	private ServiceTarifario getServiceTarifario() {
		if(this.serviceTarifario == null){
			this.serviceTarifario = new ServiceTarifario();
		}
		return serviceTarifario;
	}
}
