package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.HospedagemLancamento;
import br.com.buch.core.entity.HospedagemLancamento.OrigemLancamento;
import br.com.buch.core.entity.Recebimento;
import br.com.buch.core.service.ServiceAdiantamento;
import br.com.buch.core.service.ServiceHospedagem;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.PersistenciaException;
import br.com.buch.core.util.UtilErros;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class CheckoutBean implements Serializable{

	private static final long serialVersionUID = 3896117530939388974L;
	private Hospedagem hospedagem;
	private ServiceHospedagem service;
	private Integer idHospedagem;
	private List<Adiantamento> adiantamentos;
	private Recebimento recebimento;
	private boolean skip;
	
	
	@PostConstruct
	public void init(){
		service = new ServiceHospedagem();
		try {
			this.hospedagem = service.carregarEntidade(5);			
			adiantamentos = new ServiceAdiantamento().buscarPorHospede(hospedagem.getHospede());	
			recebimento = new Recebimento();
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
		

	public void formaPagamentoSelecionado(SelectEvent event){
		FormaPagamento formaPagamento = (FormaPagamento) event.getObject();	
		this.recebimento.setFormaPagamento(formaPagamento);
	}
	
	
	public void bancoSelecionado(SelectEvent event){
		Banco banco = (Banco) event.getObject();	
		this.recebimento.setLocalRecebimento(banco);
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
	
	
	public String confirmarCheckOut(){
		try {
			service.confirmarCheckOut(hospedagem, recebimento, adiantamentos);
			return "hospedagem?faces-redirect=true&retornoCheckOut=true";
		} catch (Exception e) {			
			UtilMensagens.mensagemErro("Ocorreu uma exceção ao gravar o Check-Out!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
			return "";
		}		
	}
	
	
	// =============================GET AND SET=====================================
	
	
	public Hospedagem getHospedagem() {return hospedagem;}
	
	public void setHospedagem(Hospedagem hospedagem) {this.hospedagem = hospedagem;}
	
	
	public Integer getIdHospedagem() {return idHospedagem;}
	
	public void setIdHospedagem(Integer idHospedagem) {this.idHospedagem = idHospedagem;}
	
	
	public Recebimento getRecebimento() {return recebimento;}
	
	
	public boolean isSkip() {return skip;}
	 
    public void setSkip(boolean skip) {this.skip = skip;}   
    
    
	public List<Banco> getBancos(String string) {
		try {
			return Constantes.getInstance().getListaBancos();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<FormaPagamento> getFormasPagamento(String string) {
		try {
			return Constantes.getInstance().getListaFormasPagamento();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<Adiantamento> getAdiantamentos() {		
		if (adiantamentos == null){
			adiantamentos = new ArrayList<>();
		}
		return adiantamentos;
	} 
}
