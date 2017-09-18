package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.dao.MapaReservaDao;
import br.com.buch.core.entity.MapaReserva;

@ManagedBean
@ViewScoped
public class MapaReservaBean implements Serializable{

	private static final long serialVersionUID = -7371992755633594553L;

	private List<MapaReserva> mapaReservas;
	private Integer mes;
	private Integer ano;
	
	
	public MapaReservaBean() {
		
	}
	
	
	@PostConstruct
	public void init(){
		this.mapaReservas = new ArrayList<>();
		this.ano = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		this.mes = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))-1;
		atualizar();
	}
	
	
	public void atualizar(){
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 01);
		ca.set(Calendar.MONTH, mes);
		ca.set(Calendar.YEAR, ano);
		
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.DAY_OF_MONTH, 01);
		c2.set(Calendar.MONTH, mes+1);
		c2.set(Calendar.YEAR, ano);
		
		c2.add(Calendar.DAY_OF_MONTH, -1);
				
		this.mapaReservas = new MapaReservaDao().getMapaReserva(ca.getTime(), c2.getTime());
	}
		
	public List<MapaReserva> getMapaReservas() {return mapaReservas;}

	public Integer getMes() {return mes;}
	public void setMes(Integer mes) {this.mes = mes;}

	public Integer getAno() {return ano;}
	public void setAno(Integer ano) {this.ano = ano;}
	
	
}
