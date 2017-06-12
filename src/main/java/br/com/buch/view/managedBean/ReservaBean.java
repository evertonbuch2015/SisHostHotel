package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.enumerated.TipoFiltroReserva;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.core.service.ServiceReserva;
import br.com.buch.core.service.ServiceTarifario;
import br.com.buch.core.service.ServiceTipoTarifa;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@SessionScoped
public class ReservaBean extends GenericBean<Reserva, ServiceReserva> implements Serializable{

	private static final long serialVersionUID = 7126384947748854847L;
	
	private TipoFiltroReserva filtro;	
	private ServiceApartamento serviceApartamento;
	private ServiceTarifario serviceTarifario;
	private TipoTarifa tipoTarifa;
	private boolean tarifaManual;
	
	
	public ReservaBean() {
		super(new ServiceReserva());
		this.serviceApartamento = new ServiceApartamento();
	}

	
	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {

	}

	
	@Override
	public Reserva criarEntidade() {
		Reserva reserva = new Reserva();
		reserva.setSituacao(SituacaoHospedagem.NAO_CONFIRMADA);
		return reserva;
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
		
		if(entidade.getApartamento() == null) 
			return;
		
		if(entidade.getDataEntrada() == null) 
			return;
		
		
		try {
			Tarifario tarifario = 
					getServiceTarifario().buscarPelaCategoriaTipoTarifa(
							entidade.getApartamento().getCategoria().getIdCategoria(),
							tipoTarifa.getIdTipoTarifa(),
							entidade.getDataEntrada());
						
			this.entidade.setValorDiaria(tarifario.getValor());			
			
		} catch (Exception e) {			
			if(e.toString().contains("NoResultException")){
				UtilMensagens.mensagemInformacao("Não existe Tarifário cadastrado para esta Categoria e Tipo de Tarifa!");
				entidade.setValorDiaria(null);
			}			
		}
	}
	
	
	private void verificaDisponibilidadeApartamento(){
		
		if(entidade.getDataEntrada() == null || 
				entidade.getDataSaida() == null ||
					entidade.getApartamento() == null){
			return;
		}
		
		Integer disponivel = serviceApartamento.verificaDisponibilidade(
								entidade.getApartamento().getIdApartamento(),
								entidade.getDataEntrada(),
								entidade.getDataSaida());
		
		if(disponivel == 1){
			UtilMensagens.mensagemAtencao("Apartamento não está disponivel para esta Data!");
			entidade.setDataEntrada(null);
			entidade.setDataSaida(null);
		}
	}
	
	
	public String onFlowProcess(FlowEvent event) {
        
        
            return event.getNewStep();
        
    }
	
	
	// =============================GET AND SET=====================================

	
	public TipoFiltroReserva getFiltro() {
		return filtro;
	}

	public void setFiltro(TipoFiltroReserva filtro) {
		this.filtro = filtro;
	}

	public TipoFiltroReserva[] tipoFiltros(){
		return TipoFiltroReserva.values();
	}

	
	@Override
	public List<Reserva> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	
	
	public List<Apartamento> apartamentos(){
		return serviceApartamento.buscarTodos();
	}
	
	
	public List<TipoTarifa> tiposTarifa(){
		return new ServiceTipoTarifa().buscarTodos();
	}
	
	public TipoTarifa getTipoTarifa() {
		return tipoTarifa;
	}
	
	public void setTipoTarifa(TipoTarifa tipoTarifa) {
		this.tipoTarifa = tipoTarifa;
	}
		
	
	public boolean isTarifaManual() {
		return tarifaManual;
	}

	
	private ServiceTarifario getServiceTarifario() {
		if(this.serviceTarifario == null){
			this.serviceTarifario = new ServiceTarifario();
		}
		return serviceTarifario;
	}
}
