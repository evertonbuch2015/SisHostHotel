package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.enumerated.TipoFiltroReserva;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.core.service.ServiceHospede;
import br.com.buch.core.service.ServiceReserva;
import br.com.buch.core.service.ServiceTarifario;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.NegocioException;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@ViewScoped
public class ReservaBean extends GenericBean<Reserva, ServiceReserva> implements Serializable{

	private static final long serialVersionUID = 7126384947748854847L;
	
	private TipoFiltroReserva filtro;	
	private ServiceApartamento serviceApartamento;
	private ServiceTarifario serviceTarifario;
	private ServiceHospede serviceHospede;
	private TipoTarifa tipoTarifa;
	private boolean tarifaManual;
	//Filtros
	private SituacaoHospedagem situacaoFiltro;
	private Date dataFiltro;
	private Date dataFiltroFinal;
	
	public ReservaBean() {
		super(new ServiceReserva());	
	}
	
	
	@PostConstruct
	public void init(){
		serviceApartamento = new ServiceApartamento();
		serviceHospede = new ServiceHospede();
	}
	
	// =======================METODOS DO USUARIO=====================================
	
	@Override
	public void filtrar() {
		try {			
			if(filtro == TipoFiltroReserva.SITUACAO){
				this.entidades = service.filtrarTabela(filtro, situacaoFiltro);
			}else if(filtro == TipoFiltroReserva.DATA_ENTRADA){
				
				if(this.dataFiltro != null && this.dataFiltroFinal == null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro);
				}
				else if(this.dataFiltro != null && this.dataFiltroFinal != null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro, dataFiltroFinal);
				}				
				
			}else if(filtro == TipoFiltroReserva.CODIGO){
				this.entidades = service.filtrarTabela(filtro, valorFiltro);
			}
			else if(filtro != null){
				this.entidades = service.filtrarTabela(filtro, valorFiltro);
			}
			
			dataFiltro =null;
			dataFiltroFinal =null;
			situacaoFiltro=null;
			
		}catch(NegocioException e){
			UtilMensagens.mensagemAtencao(e.getMessage());
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
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
	
	
	public String fazerCheckin(Reserva reserva){		
		if (reserva.getSituacao().equals(SituacaoHospedagem.CONFIRMADA)){			
			return "hospedagem?faces-redirect=true&reserva=" + reserva.getIdReserva();
		}else{
			UtilMensagens.mensagemAtencao("Reserva deve estar com Status de Confirmada para realizar o Check-In");
			return "";
		}			
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
	
	
	public void cancelarReserva(Reserva reserva){
		try {
			service.cancelarReserva(reserva);
			refresh();
			mudarBuscar();
			
			UtilMensagens.mensagemInformacao("Cancelamento de Reserva Realizado com Sucesso!");
		}
		catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	public void confirmarReserva(){
		if (this.entidade == null) {
			UtilMensagens.mensagemAtencao("Selecione um Registro na Lista!");
			return;
		}
		
		try {
			service.confirmarReserva(this.entidade);
			refresh();
			mudarBuscar();
			
			UtilMensagens.mensagemInformacao("Reserva Confirmada com Sucesso!");
		}
		catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	public List<Apartamento> buscarApartamentos(String query){
		try{
			if (query.equals("")) {
				return serviceApartamento.buscarTodos();
			} else {
				return serviceApartamento.buscarPorNumero(Integer.parseInt(query));
			}
		}catch (NumberFormatException e) {
			UtilMensagens.mensagemErro("Informe um Número Válido");
			return null;			
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
			return null;
		}
	}
	
	
	public List<Hospede> buscarHospedes(String query){
		if (query != null && query.length() < 3){
			return null;
		}
		
		try{
			return serviceHospede.buscarPorNome("%"+query+"%");
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
			return null;
		}
	}
	 
	
	// =============================GET AND SET=====================================

	
	public TipoFiltroReserva getFiltro() {return filtro;}
	public void setFiltro(TipoFiltroReserva filtro) {this.filtro = filtro;}

	public TipoFiltroReserva[] tipoFiltros(){return TipoFiltroReserva.values();}

	public SituacaoHospedagem getSituacaoFiltro() {return situacaoFiltro;}
	public void setSituacaoFiltro(SituacaoHospedagem situacaoFiltro) {this.situacaoFiltro = situacaoFiltro;}

	public Date getDataFiltro() {return dataFiltro;}
	public void setDataFiltro(Date dataFiltro) {this.dataFiltro = dataFiltro;}
	
	public Date getDataFiltroFinal() {return dataFiltroFinal;}	
	public void setDataFiltroFinal(Date dataFiltroFinal) {this.dataFiltroFinal = dataFiltroFinal;}
	
	
	public SituacaoHospedagem[] getSituacoesHospedagem(){return SituacaoHospedagem.values();}
		
	@Override
	public List<Reserva> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}
		
	
	public List<TipoTarifa> tiposTarifa(){
		try {
			return Constantes.getInstance().getListaTiposTarifa();
		} catch (Exception e) {
			UtilMensagens.mensagemErro(UtilMensagens.MSM_ERRO_INTERNO);
			return new ArrayList<>();
		}
	}
	
	public TipoTarifa getTipoTarifa() {return tipoTarifa;}
	public void setTipoTarifa(TipoTarifa tipoTarifa) {this.tipoTarifa = tipoTarifa;}
		
	
	public boolean isTarifaManual() {return tarifaManual;}
	
	private ServiceTarifario getServiceTarifario() {
		if(this.serviceTarifario == null){
			this.serviceTarifario = new ServiceTarifario();
		}
		return serviceTarifario;
	}
	
	}
