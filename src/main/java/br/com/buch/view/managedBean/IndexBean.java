package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.buch.view.util.SessionContext;


@ManagedBean
@SessionScoped
public class IndexBean implements Serializable {
	
	private static final long serialVersionUID = 201405150723L;
	private String localeCode;
	private static Map<String, Locale> countries;

	static {
		countries = new LinkedHashMap<String, Locale>();
		countries.put("English", new Locale("en"));
		countries.put("Português", new Locale("pt"));
	}

	
	// =======================METODOS DO USUARIO=====================================	
	
	public void localeCodeChanged(AjaxBehaviorEvent e) {
		//String newLocaleValue = ((SelectOneMenu)e.getSource()).getValue() + "";

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
	
	
	// =============================GET AND SET======================================
	
	public Map<String, Locale> getCountries() {
		return countries;
	}

	public String getLocaleCode() {
		if ((this.localeCode == null) && (FacesContext.getCurrentInstance().getViewRoot() != null) &&
				(FacesContext.getCurrentInstance().getViewRoot().getLocale() != null)) {
			this.localeCode = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		}

		return this.localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
}
