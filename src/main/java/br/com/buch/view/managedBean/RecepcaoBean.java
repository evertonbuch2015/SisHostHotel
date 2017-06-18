package br.com.buch.view.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.service.ServiceApartamento;
import br.com.buch.core.util.PersistenciaException;


@ManagedBean
@ViewScoped
public class RecepcaoBean {

	private List<Apartamento> apartamentos;
	
	@PostConstruct
    public void init() {
		atualizarApartamentos();
    }
	
	
	public void atualizarApartamentos(){
		try {
			this.apartamentos = new ServiceApartamento().buscarTodos();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			this.apartamentos = new ArrayList<Apartamento>();
		}
	}
	
	
	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}

}
