package br.com.buch.view.managedBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Hotel;
import br.com.buch.core.entity.Usuario;
import br.com.buch.core.enumerated.GrupoUsuarios;
import br.com.buch.core.service.ServiceUsuario;


@ManagedBean
@SessionScoped
public class UsuarioBean extends GenericBean<Usuario> {

	public enum TipoFiltro{
		CODIGO("Código"), NOME("Nome Usuário");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private ServiceUsuario usuarioService;
	private Integer usuarioId;
	
	
	public UsuarioBean() {
		usuarioService = new ServiceUsuario();
	}
		
	// =======================METODOS DO USUARIO=================================================
	
	public void filtrar(){
		this.entidades = usuarioService.filtrarTabela(filtro, valorFiltro);
	}
	
	
	@Override
	public void carregaEntidade() {
		this.entidade = usuarioService.carregarEntidade(this.entidade.getIdUsusario());				
	}
	
	
	@Override
	public Usuario criarEntidade() {		
		return new Usuario();
	}
	
	
	@Override
	public void refresh() {
		this.entidades = usuarioService.buscarTodos();
	}
	
	
	@Override
	public void gravar() {
		if (usuarioService.salvar(this.entidade)) {			
			refresh();
			mudarBuscar();
		}		
	}

	
	@Override
	public void excluir(Usuario entidade) {
		usuarioService.excluir(entidade);
		refresh();
		mudarBuscar();
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

		
	public GrupoUsuarios[] getGrupoUsuarios(){
		return GrupoUsuarios.values();
	}	
		
	
	//RETORNA LISTA DE SETORES PARA O COMBO
	public List<String> getSetores(){
		return usuarioService.buscarSetores();
	}
	
	//RETORNA LISTA DE FILTROS PARA O COMBO
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}
		
	public TipoFiltro getFiltro() {
		return filtro;
	}
		
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}
}
