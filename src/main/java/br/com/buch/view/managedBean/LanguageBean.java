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

import org.primefaces.component.selectonemenu.SelectOneMenu;


@ManagedBean
@SessionScoped
public class LanguageBean implements Serializable {
	
	private static final long serialVersionUID = 201405150723L;
	private String localeCode;
	private static Map<String, Locale> countries;

	static {
		countries = new LinkedHashMap<String, Locale>();
		countries.put("English", new Locale("en"));
		countries.put("Portugu�s", new Locale("pt"));
	}

	
	// =======================METODOS DO USUARIO=====================================	
	
	public void localeCodeChanged(AjaxBehaviorEvent e) {
		String newLocaleValue = ((SelectOneMenu)e.getSource()).getValue() + "";

		for (Entry<String, Locale> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(newLocaleValue)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
			}
		}
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
