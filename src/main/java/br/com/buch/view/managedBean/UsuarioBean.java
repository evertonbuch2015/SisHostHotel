package br.com.buch.view.managedBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.enumerated.GrupoUsuario;
import br.com.buch.core.service.ServiceUsuario;


@ManagedBean
@SessionScoped
public class UsuarioBean extends GenericBean<Usuario, ServiceUsuario> {

	public enum TipoFiltro{
		CODIGO("Código"), 
		NOME("Nome Usuário");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private Integer usuarioId;
	
	
	public UsuarioBean() {
		super(new ServiceUsuario());
	}
		
	// =======================METODOS DO USUARIO=================================================
	
	public void filtrar(){
		this.entidades = service.filtrarTabela(filtro, valorFiltro);
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
			FacesMessage msg = new FacesMessage("Hotel já cadastrada para este Usuário", "Hotel já cadastrada para este Usuário");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}	
		
	
	public void empresaSelecionada(SelectEvent event){
		Hotel empresa = (Hotel) event.getObject();
		adicionarEmpresa(empresa);
	}
	
	
	// =============================GET AND SET=================================
	
	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	
	public TipoFiltro getFiltro() {
		return filtro;
	}
		
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}

		
	public GrupoUsuario[] getGrupoUsuarios(){
		return GrupoUsuario.values();
	}	
		
	
	//RETORNA LISTA DE SETORES PARA O COMBO
	public List<String> getSetores(){
		return service.buscarSetores();
	}
	
	
	//RETORNA LISTA DE FILTROS PARA O COMBO
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}
		
}
