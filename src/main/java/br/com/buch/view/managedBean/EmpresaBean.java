package br.com.buch.view.managedBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import br.com.buch.core.entity.Empresa;
import br.com.buch.core.enumerated.RamoAtividade;
import br.com.buch.core.enumerated.RegimeTributario;
import br.com.buch.core.service.EmpresaService;

@ManagedBean
@SessionScoped
public class EmpresaBean extends GenericBean<Empresa> {
	
	public enum TipoFiltro{
		CODIGO("C�digo"), NOME("Nome");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private Integer empresaId;
	private EmpresaService empresaService;

	
	public EmpresaBean() {
		empresaService = new EmpresaService();
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	public void filtrar(){
		this.entidades = empresaService.filtrarTabela(filtro, valorFiltro);
	}
	
	
	@Override
	public void carregaEntidade() {
		//empresaDao.fecharConexao();		
	}
	
	
	@Override
	public Empresa criarEntidade() {
		this.entidade = new Empresa();
		entidade.setDataCadastro(new Date());
		return entidade;
	}

	
	@Override
	public void refresh() {
		if(this.entidades != null){
			this.entidades.clear();
		}
		this.entidades = empresaService.buscarTodos();
	}
	
	
	@Override
	public void gravar() {
		if (empresaService.salvar(this.entidade)) {			
			refresh();
			mudarBuscar();
		}	
	}

	
	@Override
	public void excluir(Empresa entidade) {
		empresaService.excluir(entidade);
		refresh();
		mudarBuscar();
	}

	
	public void AbrirDialogEmpresas() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        options.put("contentHeight", 450);				
        RequestContext.getCurrentInstance().openDialog("/dialogosSelecao/SelecaoEmpresa", options, null);
    }
	
	
	public void retornoDialog(Empresa empresa){
		RequestContext.getCurrentInstance().closeDialog(empresa);
	}
		
	
	// =============================GET AND SET=====================================

	public Integer getEmpresaId() {
		return empresaId;
	}
	
	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}
	
	
	public RegimeTributario[] getRegimes(){
		return RegimeTributario.values();
	}
	
	public RamoAtividade[] getRamoAtividades(){
		return RamoAtividade.values();
	}

	
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
