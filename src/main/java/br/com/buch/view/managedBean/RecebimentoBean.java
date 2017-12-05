package br.com.buch.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.buch.core.entity.Banco;
import br.com.buch.core.entity.FormaPagamento;
import br.com.buch.core.entity.Recebimento;
import br.com.buch.core.entity.Recebimento.OrigemRecebimento;
import br.com.buch.core.enumerated.TipoFiltroRecebimento;
import br.com.buch.core.service.ServiceRecebimento;
import br.com.buch.core.util.NegocioException;
import br.com.buch.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class RecebimentoBean extends GenericBean<Recebimento, ServiceRecebimento> implements Serializable{

	private static final long serialVersionUID = 4996140944326181060L;
	private TipoFiltroRecebimento filtro;
	//Filtros
	private Date dataFiltro;
	private Date dataFiltroFinal;
	private FormaPagamento formaPagamento;
	private Banco banco;
	
	
	public RecebimentoBean() {
		super(new ServiceRecebimento());
	}

	
	// =======================METODOS DO USUARIO=====================================
	
	@Override
	public void filtrar() {
		try {			
			if(filtro == TipoFiltroRecebimento.FORMA_PAGAMENTO){
				this.entidades = service.filtrarTabela(filtro, formaPagamento);
			}
			else if(filtro == TipoFiltroRecebimento.DATA_EMISSAO){
				if(this.dataFiltro != null && this.dataFiltroFinal == null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro);
				}
				else if(this.dataFiltro != null && this.dataFiltroFinal != null){
					this.entidades = service.filtrarTabela(filtro, dataFiltro, dataFiltroFinal);
				}
			}
			else if(filtro == TipoFiltroRecebimento.LOCAL_RECEBIMENTO){
				this.entidades = service.filtrarTabela(filtro, banco);
			}
			else if(filtro != null){
				this.entidades = service.filtrarTabela(filtro, valorFiltro);
			}
			
			dataFiltro =null;
			dataFiltroFinal =null;
			banco=null;
			formaPagamento=null;
			
		}catch(NegocioException e){
			UtilMensagens.mensagemAtencao(e.getMessage());
		}catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	
	@Override
	public Recebimento criarEntidade() {
		Recebimento recebimento = new Recebimento();
		recebimento.setOrigemRecebimento(OrigemRecebimento.ENTRADA_MANUAL);
		return recebimento;
	}
	
		
	// =============================GET AND SET=====================================
	
	@Override
	public List<Recebimento> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
		
	public TipoFiltroRecebimento[] tipoFiltros(){return TipoFiltroRecebimento.values();}
	
	
	public TipoFiltroRecebimento getFiltro() {return filtro;}
	public void setFiltro(TipoFiltroRecebimento filtro) {this.filtro = filtro;}
	
	public Date getDataFiltro() {return dataFiltro;}
	public void setDataFiltro(Date dataFiltro) {this.dataFiltro = dataFiltro;}
	
	public Date getDataFiltroFinal() {return dataFiltroFinal;}
	public void setDataFiltroFinal(Date dataFiltroFinal) {this.dataFiltroFinal = dataFiltroFinal;}
	
	public Banco getBanco() {return banco;}
	public void setBanco(Banco banco) {this.banco = banco;}
	
	public FormaPagamento getFormaPagamento() {return formaPagamento;}
	public void setFormaPagamento(FormaPagamento formaPagamento) {this.formaPagamento = formaPagamento;}
}
