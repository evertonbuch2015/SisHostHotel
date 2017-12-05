package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.buch.core.entity.Adiantamento;
import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.entity.Hospede;
import br.com.buch.core.enumerated.TipoFiltroAdiantamento;
import br.com.buch.core.service.ServiceAdiantamento;
import br.com.buch.core.service.ServiceHospede;
import br.com.buch.core.util.NegocioException;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class AdiantamentoBean extends GenericBean<Adiantamento, ServiceAdiantamento> implements Serializable{

	private static final long serialVersionUID = -8165871784161603162L;
	private TipoFiltroAdiantamento filtro;	
	private ServiceHospede serviceHospede;
	
	//Filtros
	private Banco bancoFiltro;
	private Date dataFiltro;
	private Date dataFiltroFinal;
	
	private Banco bancoBaixa;
	private FormaPagamento formaPagamentoBaixa; 
	
	public AdiantamentoBean() {
		super(new ServiceAdiantamento());
		serviceHospede = new ServiceHospede();
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	
	@Override
	public void filtrar() {
		try {			
			if(filtro == TipoFiltroAdiantamento.LOCAL_RECEBIMENTO){
				this.entidades = service.filtrarTabela(filtro, bancoFiltro);
			}
			
			else if(filtro == TipoFiltroAdiantamento.DATA_EMISSAO){
				if(this.dataFiltro != null && this.dataFiltroFinal == null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro);
				}
				else if(this.dataFiltro != null && this.dataFiltroFinal != null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro, dataFiltroFinal);
				}
			}
			
			else if(filtro != null){
				this.entidades = service.filtrarTabela(filtro, valorFiltro);
			}				
		}catch(NegocioException e){
			UtilMensagens.mensagemAtencao(e.getMessage());
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}finally {
			dataFiltro =null;  dataFiltroFinal =null;  bancoFiltro=null;
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
		if (this.entidade == null || this.entidade.getIdAdiantamento() == null){
			UtilMensagens.mensagemAtencao("Escolha um Adiantamento de Cliente na Lista!");
			return;
		}
		
		try {
			entidade.setLocalRecebimento(bancoBaixa);
			service.realizarBaixa(entidade);
			this.entidade = service.carregarEntidade(entidade);
			UtilMensagens.mensagemInformacao("Baixa de Adiantamento de Cliente realizada com sucesso!");
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
	
	public Date getDataFiltro() {return dataFiltro;}
	public void setDataFiltro(Date dataFiltro) {this.dataFiltro = dataFiltro;}
	
	public Date getDataFiltroFinal() {return dataFiltroFinal;}
	public void setDataFiltroFinal(Date dataFiltroFinal) {this.dataFiltroFinal = dataFiltroFinal;}
	
	public Banco getBancoFiltro() {return bancoFiltro;}
	public void setBancoFiltro(Banco bancoFiltro) {this.bancoFiltro = bancoFiltro;}

	
	public Banco getBancoBaixa() {return bancoBaixa;}	
	public void setBancoBaixa(Banco bancoBaixa) {this.bancoBaixa = bancoBaixa;}

	public FormaPagamento getFormaPagamentoBaixa() {return formaPagamentoBaixa;}
	public void setFormaPagamentoBaixa(FormaPagamento formaPagamentoBaixa) {this.formaPagamentoBaixa = formaPagamentoBaixa;}
	
	@Override
	public List<Adiantamento> getEntidades() {
		if (this.entidades == null)
			refresh();
		return entidades;
	}
}
