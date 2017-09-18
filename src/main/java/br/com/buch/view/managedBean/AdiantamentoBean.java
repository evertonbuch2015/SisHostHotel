package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.TipoFiltroAdiantamento;
import br.com.buch.core.service.ServiceAdiantamento;
import br.com.buch.core.service.ServiceHospede;
import br.com.buch.core.util.Constantes;
import br.com.buch.core.util.NegocioException;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class AdiantamentoBean extends GenericBean<Adiantamento, ServiceAdiantamento> implements Serializable{

	private static final long serialVersionUID = -8165871784161603162L;
	private TipoFiltroAdiantamento filtro;	
	private ServiceHospede serviceHospede;
	
	public AdiantamentoBean() {
		super(new ServiceAdiantamento());
		serviceHospede = new ServiceHospede();
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {
		try {
			service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	
	@Override
	public Adiantamento criarEntidade() {
		return new Adiantamento();
	}

		
	public void hospedeSelecionado(SelectEvent event){
		Hospede hospede = (Hospede) event.getObject();	
		this.entidade.setHospede(hospede);;
	}
		
	
	public void realizarBaixa(){
		try {
			service.realizarBaixa(entidade);
			this.entidade = service.carregarEntidade(entidade);
			UtilMensagens.mensagemAtencao("Baixa de Adiantamento de Cliente realizada com sucesso!");
		}
		catch (NegocioException e) {
			UtilMensagens.mensagemAtencao(e.getMessage());
		}
		catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	public List<Hospede> buscarHospedes(String query){
		if (query != null && query.length() < 3){
			return null;
		}
		
		try{
			return serviceHospede.buscarPorNome("%"+query+"%");
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
			return null;
		}
	}
	
	// =============================GET AND SET=====================================
	
	
	public TipoFiltroAdiantamento[] tipoFiltros(){return TipoFiltroAdiantamento.values();}
	
	public TipoFiltroAdiantamento getFiltro() {return filtro;}
	
	public void setFiltro(TipoFiltroAdiantamento filtro) {this.filtro = filtro;}
	
	
	public List<Banco> getLocaisRecebimento(){
		try {
			return Constantes.getInstance().getListaBancos();
		} catch (Exception e) {
			UtilMensagens.mensagemErro(UtilMensagens.MSM_ERRO_INTERNO);
			return new ArrayList<>();
		}
	}
	
	
	@Override
	public List<Adiantamento> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}
}
