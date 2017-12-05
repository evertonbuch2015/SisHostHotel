package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.Categoria;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.entity.TipoTarifa;
import br.com.buch.core.util.Constantes;


@ManagedBean
@ApplicationScoped()
public class ConstantesBean implements Serializable{	
	
	private static final long serialVersionUID = 1430940396088268839L;


	public List<Apartamento> getApartamentos() throws Exception{
		return Constantes.getInstance().getListaApartamentos();
	}
	
	
	public List<Categoria> getCategorias() throws Exception {
		return Constantes.getInstance().getListaCategorias();
	}
	
	
	public List<Banco> getBancos() throws Exception {
		return Constantes.getInstance().getListaBancos();
	}
	
	
	public List<TipoTarifa> getTiposTarifa()throws Exception{
		return Constantes.getInstance().getListaTiposTarifa();
	}
	
	
	public List<FormaPagamento> getFormasPagamento()throws Exception{
		return Constantes.getInstance().getListaFormasPagamento();
	}
}
