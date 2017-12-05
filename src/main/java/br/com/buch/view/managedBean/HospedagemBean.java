package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.entity.Tarifario;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.enumerated.SituacaoHospedagem;
import br.com.buch.core.enumerated.TipoFiltroHospedagem;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.core.service.ServiceHospedagem;
import br.com.buch.core.service.ServiceHospede;
import br.com.buch.core.service.ServiceReserva;
import br.com.buch.core.service.ServiceTarifario;
import br.com.buch.core.service.ServiceTipoTarifa;
import br.com.buch.core.util.NegocioException;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@ViewScoped
public class HospedagemBean extends GenericBean<Hospedagem, ServiceHospedagem> implements Serializable{

	private static final long serialVersionUID = -2119605666827486172L;

	private TipoFiltroHospedagem filtro;	
	private ServiceApartamento serviceApartamento;
	private ServiceHospede serviceHospede;
	private ServiceTarifario serviceTarifario;
	private TipoTarifa tipoTarifa;
	private boolean tarifaManual;	
	//Filtros
	private SituacaoHospedagem situacaoFiltro;
	private Date dataFiltro;
	private Date dataFiltroFinal;
	private Integer idReservaParametro;
	
	
	public HospedagemBean() {
		super(new ServiceHospedagem());
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
			
			if(filtro == TipoFiltroHospedagem.SITUACAO){
				this.entidades = service.filtrarTabela(filtro, situacaoFiltro);
			}else if(filtro == TipoFiltroHospedagem.DATA_ENTRADA){
				
				if(this.dataFiltro != null && this.dataFiltroFinal == null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro);
				}
				else if(this.dataFiltro != null && this.dataFiltroFinal != null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro, dataFiltroFinal);
				}				
				
			}else if(filtro == TipoFiltroHospedagem.CODIGO){
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
									entidade.getApartamento(),
									entidade.getDataEntrada(),
									entidade.getDataSaida());
			
		} catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
			entidade.setDataEntrada(null);
			entidade.setDataSaida(null);
		}
		
	}
	
	
	public void carregaReserva(){
		try {
			
			novo();			
			Reserva reserva = new ServiceReserva().carregarEntidade(idReservaParametro);
			
			entidade.setApartamento(reserva.getApartamento());
			entidade.setHospede(reserva.getHospede());
			entidade.setDataEntrada(reserva.getDataEntrada());
			entidade.setDataSaida(reserva.getDataSaida());
			entidade.setSituacao(SituacaoHospedagem.CHECKIN);
			entidade.setValorDesconto(reserva.getValorDesconto());
			entidade.setValorDiaria(reserva.getValorDiaria());
			entidade.setValorTaxaServico(reserva.getValorTaxaServico());
			entidade.setValorTaxaTurismo(reserva.getValorTaxaTurismo());		
			entidade.setObs(reserva.getObs());		
			
			entidade.setReserva(reserva);
			
		} catch (PersistenciaException e) {
			
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
 
	
	public String fazerCheckOut(Hospedagem hospedagem){		
		if (hospedagem.getSituacao().equals(SituacaoHospedagem.CHECKIN)){			
			return "checkOut?faces-redirect=true&hospedagem=" + hospedagem.getIdHospedagem();
		}else{
			UtilMensagens.mensagemAtencao("Já foi realizado o Check-Out desta Hospedagem!");
			return "";
		}			
	}
	
	
	public void retornoCheckOut(){
		UtilMensagens.mensagemInformacao("Check-Out Realizado com Sucesso!");
	}
	
	
	@Override
	public void cancelar() {	
		this.tipoTarifa = null;
		super.cancelar();
	}
	
	@Override
	public void gravar() {	
		this.tipoTarifa = null;
		super.gravar();
	}
	
	// =============================GET AND SET=====================================

	
	public TipoFiltroHospedagem getFiltro() {return filtro;}
	public void setFiltro(TipoFiltroHospedagem filtro) {this.filtro = filtro;}
	
	public TipoFiltroHospedagem[] tipoFiltros(){return TipoFiltroHospedagem.values();}

	
	public List<TipoTarifa> tiposTarifa(){return new ServiceTipoTarifa().buscarTodos();}
	
	
	public TipoTarifa getTipoTarifa() {return tipoTarifa;}	
	public void setTipoTarifa(TipoTarifa tipoTarifa) {this.tipoTarifa = tipoTarifa;}	
	
	
	public boolean isTarifaManual() {return tarifaManual;}
	

	public SituacaoHospedagem[] getSituacoesHospedagem(){return SituacaoHospedagem.values();}
	
	
	@Override
	public List<Hospedagem> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	
	
	private ServiceTarifario getServiceTarifario() {
		if(this.serviceTarifario == null){
			this.serviceTarifario = new ServiceTarifario();
		}
		return serviceTarifario;
	}
	
	
	public SituacaoHospedagem getSituacaoFiltro() {return situacaoFiltro;}	
	public void setSituacaoFiltro(SituacaoHospedagem situacaoFiltro) {this.situacaoFiltro = situacaoFiltro;}
	
	public Date getDataFiltro() {return dataFiltro;}
	public void setDataFiltro(Date dataFiltro) {this.dataFiltro = dataFiltro;}
	
	public Date getDataFiltroFinal() {return dataFiltroFinal;}
	public void setDataFiltroFinal(Date dataFiltroFinal) {this.dataFiltroFinal = dataFiltroFinal;}
	
	public Integer getIdReservaParametro() {return idReservaParametro;}	
	public void setIdReservaParametro(Integer idReservaParametro) {this.idReservaParametro = idReservaParametro;}
}
