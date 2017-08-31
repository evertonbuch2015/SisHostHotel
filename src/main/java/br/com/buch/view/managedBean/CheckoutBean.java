package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;

import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.HospedagemLancamento;
import br.com.buch.core.entity.HospedagemLancamento.OrigemLancamento;
import br.com.buch.core.service.ServiceAdiantamento;
import br.com.buch.core.service.ServiceHospedagem;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class CheckoutBean implements Serializable{

	

	private static final long serialVersionUID = 3896117530939388974L;
	private Hospedagem hospedagem;
	private ServiceHospedagem service;
	private Integer idHospedagem;
	private List<Adiantamento> adiantamentos;
	
	private boolean skip;
	
	
	@PostConstruct
	public void init(){
		service = new ServiceHospedagem();
		try {
			this.hospedagem = service.carregarEntidade(5);			
			adiantamentos = new ServiceAdiantamento().buscarPorHospede(hospedagem.getHospede());			
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void buscarHospedagem(){
		try {
			this.hospedagem = service.carregarEntidade(idHospedagem);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
	}
	
	
	public void adiantamentoSelecionado(Adiantamento adiantamento){
		if(adiantamento.getSaldo() > 0){
			HospedagemLancamento hospedagemLancamento = new HospedagemLancamento();
			hospedagemLancamento.setDataCadastro(new Date());
			hospedagemLancamento.setDescricao("Adiantamento de Cliente Nº " + adiantamento.getCodigo());
			hospedagemLancamento.setHospedagem(hospedagem);
			hospedagemLancamento.setOrigemLancamento(OrigemLancamento.ADIANTAMENTO);
			hospedagemLancamento.setQuantidade(1);
			hospedagemLancamento.setVlUnitario(adiantamento.getValor());
			
			hospedagem.getLancamentos().add(hospedagemLancamento);
			
			adiantamento.setDtBaixa(new Date());
			adiantamento.setSaldo(0.00);
			
			UtilMensagens.mensagemInformacao("Adiantamento Inserido com sucesso!");
		}else{
			UtilMensagens.mensagemAtencao("Adiantamento sem Saldo Disponivel!");
		}
		
	}
	
	
	public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
	
	
	public void confirmarCheckOut(){
		
	}
	
	
	
	public Hospedagem getHospedagem() {return hospedagem;}
	
	public void setHospedagem(Hospedagem hospedagem) {this.hospedagem = hospedagem;}
	
	public Integer getIdHospedagem() {
		return idHospedagem;
	}
	
	public void setIdHospedagem(Integer idHospedagem) {
		this.idHospedagem = idHospedagem;
	}
	
	
	public List<Adiantamento> getAdiantamentos() {		
		if (adiantamentos == null){
			adiantamentos = new ArrayList<>();
		}
		return adiantamentos;
	}
	
	public boolean isSkip() {return skip;}
 
    public void setSkip(boolean skip) {this.skip = skip;}    
}
