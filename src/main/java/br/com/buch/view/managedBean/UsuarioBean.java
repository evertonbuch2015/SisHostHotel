package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.enumerated.GrupoUsuario;
import br.com.buch.core.service.ServiceUsuario;
import br.com.buch.view.util.UtilMensagens;


@ManagedBean
@ViewScoped
public class UsuarioBean extends GenericBean<Usuario, ServiceUsuario> implements Serializable{

	public enum TipoFiltro{
		CODIGO("Código"), 
		NOME("Nome Usuário");
		
		TipoFiltro(String label) {this.label = label;}
		
		private String label;		
		
		public String getLabel(){return this.label;}
	}
	
	private static final long serialVersionUID = 7007370320624874450L;
	private TipoFiltro filtro;
	
	
	public UsuarioBean() {
		super(new ServiceUsuario());
	}
		
	// =======================METODOS DO USUARIO=================================================
	
	public void filtrar(){
		try {
			this.entidades = service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	@Override
	public Usuario criarEntidade() {		
		return new Usuario();
	}
	

	public void excluirEmpresa(Hotel empresa){
		if(this.entidade.getHoteis().contains(empresa)){
			this.entidade.getHoteis().remove(empresa);
		}
	}
		
	
	public void adicionarEmpresa(Hotel empresa){		
		if(!this.entidade.getHoteis().contains(empresa)){
			this.entidade.getHoteis().add(empresa);
		}else{
			UtilMensagens.mensagemAtencao("Hotel já cadastrada para este Usuário!");
		}
	}	
		
	
	public void empresaSelecionada(SelectEvent event){
		Hotel empresa = (Hotel) event.getObject();
		adicionarEmpresa(empresa);
	}
	
	
	// =============================GET AND SET=================================
	
	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}
	
	public TipoFiltro getFiltro() {return filtro;}
		
	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}

	
	public GrupoUsuario[] getGrupoUsuarios(){return GrupoUsuario.values();}	
		
	public List<String> getSetores(){return service.buscarSetores();}		
}
