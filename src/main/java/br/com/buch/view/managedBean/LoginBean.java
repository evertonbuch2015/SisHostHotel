package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.service.ServiceUsuario;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.NegocioException;
import br.com.buch.view.util.SessionContext;
import br.com.buch.view.util.UtilMensagens;

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
		
	
	@PostConstruct
	public void init(){
		selecionandoHotel = false;
		usuario = new Usuario();
		usuarioService = new ServiceUsuario();
	}
	
	// ================Métodos do Usuário============================================
	
	public void efetuaLogin() {		
		if (usuarioService.fazerLogin(this.login, this.senha)) {
			this.usuario.setNomeUsuario(login);
			this.usuario = usuarioService.buscarPeloNome(this.usuario);
			selecionandoHotel = true;

			Constantes.getInstance().addUsuarioLogado(usuario);
			SessionContext.getInstance().setAttribute("usuarioLogado", usuario);

			//UtilMensagens.mensagemInformacao("Selecione um Hotel");
		} else {
			this.usuario = new Usuario();
			UtilMensagens.mensagemAtencao("Usuário não encontrado");
		}
	}
	

	public String prosseguir(){		
		SessionContext.getInstance().setAttribute("hotel", hotel);		
		return "index?faces-redirect=true";
	}

	
	public void recuperarSenha(){
		try {
			usuarioService.recuperarSenha(email, fraseSecreta);
			UtilMensagens.mensagemInformacao("Enviamos um email de redefinição de senha para a conta: " + email);			
		} 
		catch (NegocioException e) {			
			UtilMensagens.mensagemAtencao(e.getMessage());			
		}
		finally {
			email = "";
			senha = "";
		}
	}
	
	
	// ================Métodos GET e SET=============================================
	
	public boolean isSelecionandoHotel() {return selecionandoHotel;}
			
	
	public Usuario getUsuario() {return usuario;}
	
	public void setUsuario(Usuario usuario) {this.usuario = usuario;}
		
	
	public Hotel getHotel() {return hotel;}
	
	public void setHotel(Hotel hotel) {this.hotel = hotel;}
		
	public List<Hotel> getHoteis(){return usuario.getHoteis();}
	

	public String getSenha() {return senha;}
	
	public void setSenha(String senha) {this.senha = senha;}
	
	
	public String getLogin() {return login;}
	
	public void setLogin(String login) {this.login = login;}

	
	public String getFraseSecreta() {return fraseSecreta;}

	public void setFraseSecreta(String fraseSecreta) {this.fraseSecreta = fraseSecreta;}

	
	public String getEmail() {return email;}
	
	public void setEmail(String email) {this.email = email;}	
}
