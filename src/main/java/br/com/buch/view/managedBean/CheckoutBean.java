package br.com.buch.view.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;

import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.service.ServiceHospedagem;
import br.com.buch.core.util.PersistenciaException;

@ManagedBean
@ViewScoped
public class CheckoutBean {

	
	private Hospedagem hospedagem;
	private ServiceHospedagem service;
	private Integer idHospedagem;
	
	private boolean skip;
	
	
	@PostConstruct
	public void init(){
		service = new ServiceHospedagem();
	}
	
	
	public void buscarHospedagem(){
		try {
			this.hospedagem = service.carregarEntidade(idHospedagem);
		} catch (PersistenciaException e) {
			e.printStackTrace();
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
	
	
	public boolean isSkip() {return skip;}
 
    public void setSkip(boolean skip) {this.skip = skip;}    
}
