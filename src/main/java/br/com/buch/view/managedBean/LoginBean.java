package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.service.ServiceHotel;
import br.com.buch.core.service.ServiceUsuario;
import br.com.buch.view.util.SessionContext;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1444783038549353503L;
	private boolean selecionandoHotel; 
	private Usuario usuario;
	
	private String fraseSecreta;
	private String email;
	private String senha;
	private String login;
	
	private Hotel hotel;
	private ServiceUsuario usuarioService;
	
	
	public LoginBean() {
		selecionandoHotel = false;
		usuario = new Usuario();
		usuarioService = new ServiceUsuario();
	}
	
	
	// ================Métodos do Usuário============================================
	
	public void efetuaLogin() {		
		if(usuarioService.logar(this.login, this.senha)){
			this.usuario.setNomeUsuario(login);
			this.usuario = usuarioService.buscarPeloNome(this.usuario);
			selecionandoHotel = true;
		}else{
			this.usuario = new Usuario();
		}
		
	}
	

	public String prosseguir(){
		SessionContext.getInstance().setAttribute("usuarioLogado", this.usuario);
		SessionContext.getInstance().setAttribute("hotel", this.hotel);
		
		return "index?faces-redirect=true";
	}
		
	
	public String abrirUsuario(){
		
		Usuario usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
		
		if(usuarioLogado != null) {
			return "editarUsuario?faces-redirect=true&usuarioId=" + usuarioLogado.getIdUsusario();
		}
		return "#";
	}
	
	
	// ================Métodos GET e SET=============================================
	
	public boolean isSelecionandoHotel() {
		return selecionandoHotel;
	}
		
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	public Hotel getHotel() {
		return hotel;
	}
	
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	
	public List<Hotel> getHoteis(){
		return new ServiceHotel().buscarTodos();
	}
	

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	
	public String getFraseSecreta() {
		return fraseSecreta;
	}

	public void setFraseSecreta(String fraseSecreta) {
		this.fraseSecreta = fraseSecreta;
	}

	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	
}
