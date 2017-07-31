package br.com.buch.view.managedBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class RecepcaoBean implements Serializable{

	private static final long serialVersionUID = -3069042223172297429L;	
	
	public RecepcaoBean() {	
	}
	
	
	@PostConstruct
    public void init() {

    }
}
