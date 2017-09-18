package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.buch.core.entity.Hospedagem;
import br.com.buch.core.entity.Reserva;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.service.ServiceIndex;
import br.com.buch.core.util.Constantes;
import br.com.buch.view.util.SessionContext;


@ManagedBean
@SessionScoped
public class IndexBean implements Serializable {
		
	private static final long serialVersionUID = 201405150723L;
	private String localeCode;
	private static Map<String, Locale> countries;
	
	private BarChartModel barModel;
	private BarChartModel barModel2;
	private Usuario usuarioLogado;
	private ServiceIndex serviceIndex;

	
	static {
		countries = new LinkedHashMap<String, Locale>();
		countries.put("English", new Locale("en"));
		countries.put("Português", new Locale("pt"));
	}


	@PostConstruct
	public void init(){
		usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
		
		serviceIndex = new ServiceIndex();
		atualizarGraficos();
	}
	
	// =======================METODOS DO USUARIO=====================================	
	
	public void localeCodeChanged(AjaxBehaviorEvent e) {
		for (Entry<String, Locale> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(localeCode)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
			}
		}
	}
	
	
	public String deslogar() {
		SessionContext.getInstance().deleteAttribute("usuarioLogado");
		SessionContext.getInstance().deleteAttribute("hotel");
	    SessionContext.getInstance().encerrarSessao();
	    
	    return "/login?faces-redirect=true";
	}
		
	
	private void createBarModel2() {
        barModel2 = new BarChartModel();        
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Apartamentos");
        
        Map<Object,Number> data = serviceIndex.getGraficoApartamentos();
        boys.setData(data);
       
        barModel2.setBarWidth(35);
        
        barModel2.setAnimate(true);
        barModel2.addSeries(boys);
        
        barModel2.setTitle("Resumo dos Apartamentos");
        barModel2.setLegendPosition("ne");
        barModel2.setShowPointLabels(true);
        
        Axis yAxis = barModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Qtd. de Apartamentos");
        yAxis.setMin(0);
        yAxis.setTickInterval("3");
    }
	
	
	private void createBarModel() {
        barModel = new BarChartModel();        
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Reservas");
        
        Map<Object,Number> data = serviceIndex.getGraficoReservas();
        boys.setData(data);
        
        if (data.size() <= 10) {
        	barModel.setBarWidth(40);
		}else if(data.size() <= 35){
			barModel.setBarWidth(15);
		}else{
			barModel.setBarWidth(20);
		}
        
        
        barModel.setAnimate(true);
        barModel.addSeries(boys);
        
        barModel.setTitle("Reservas Agendadas no Mês");
        barModel.setLegendPosition("ne");
        barModel.setShowPointLabels(true);
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Qtd. de Reservas");
        yAxis.setMin(0);
        yAxis.setTickInterval("1");
    }
	
	
	/**
	 * Atualiza as listas de dados que estão na classe Constantes.
	 */
	public void atualizarListasDados(){
		Constantes.getInstance().refreshAll();
	}
	
	
	public void atualizarGraficos(){
		createBarModel();
		createBarModel2();
	}
	
	// =============================GET AND SET======================================
	
	public List<Reserva> getReservasVencidas(){
		try {
			return Constantes.getInstance().getListaReservasVencidas();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Hospedagem> getHospedagensAVencer(){
		try {
			return Constantes.getInstance().getHospedagensParaCheckOut();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Map<String, Locale> getCountries() {return countries;}
	
	
	public String getLocaleCode() {
		if ((this.localeCode == null) && (FacesContext.getCurrentInstance().getViewRoot() != null) &&
				(FacesContext.getCurrentInstance().getViewRoot().getLocale() != null)) {
			this.localeCode = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		}
		return this.localeCode;
	}

	
	public void setLocaleCode(String localeCode) {this.localeCode = localeCode;}

	
	public BarChartModel getBarModel() {return barModel;}
	
	
	public BarChartModel getBarModel2() {return barModel2;}
	
	
	public Usuario getUsuarioLogado() {return usuarioLogado;}

}
