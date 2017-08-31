package br.com.buch.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;


/**
*
* @author Everton de Souza
*/

@Entity
@Table(name="HOSPEDAGEM_LANCAMENTO")
public class HospedagemLancamento implements Serializable {

	private static final long serialVersionUID = -2398248490404309696L;

	
	public enum OrigemLancamento{
		DESCONTO("Desconto"),
		TX_TURISMO("Taxa de Turismo"),
		TX_SERVICO("Taxa de Serviço"),
		DIARIA("Diária"),
		ADIANTAMENTO("Adiantamento"),
		CONSUMO("Consumo");
		
		private String label;
		
		private OrigemLancamento(String label){
			this.label = label;
		}
		
		public String getLabel(){
			return label;
		}

		public static Boolean isCredito(OrigemLancamento origem){
			switch (origem) {
				case ADIANTAMENTO :
					return true;
				case DESCONTO:
					return true;
				default:
					return false;
			}
		}
		
		public static Boolean isDebito(OrigemLancamento origem){
			switch (origem) {
				case ADIANTAMENTO :
					return false;
				case DESCONTO:
					return false;
				default:
					return true;
			}
		}
	}
	
	
	@Id
    @SequenceGenerator(name="G_HOSPEDAGEM_LANCAMENTO", sequenceName="\"G_HOSPEDAGEM_LANCAMENTO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_HOSPEDAGEM_LANCAMENTO")
    @Column(name = "COD_HOSPEDAGEM_LANCAMENTO")
    private Integer idHospedagemLancamento;
		
	
	@Column(name="DESCRICAO", length = 150)
	private String descricao;
	
	
	@Column(name="QUANTIDADE")
	private Integer quantidade;
	
	
	@Column(name="VL_UNITARIO")
	private Double vlUnitario;
	
	
	@Column(name = "VL_TOTAL", insertable=false, updatable=false)
    private BigDecimal vlTotal;
	
	
	@Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataCadastro;
	

	@Column(name="ORIGEM_LANCAMENTO")
	@Enumerated(EnumType.STRING)
	private OrigemLancamento origemLancamento;
	
	
	
	@ManyToOne
    @JoinColumn(name ="COD_HOSPEDAGEM")
    private Hospedagem hospedagem;


	public HospedagemLancamento() {
	
	}
	
	
	public HospedagemLancamento(String descricao, Integer quantidade, Double vlUnitario) {
		super();
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.vlUnitario = vlUnitario;
	}
	
	
    //--------------------------------	GETs and SETs------------------------------//


	public Integer getIdHospedagemLancamento() {
		return idHospedagemLancamento;
	}

	public void setIdHospedagemLancamento(Integer idHospedagemLancamento) {
		this.idHospedagemLancamento = idHospedagemLancamento;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public Double getVlUnitario() {
		return vlUnitario;
	}

	public void setVlUnitario(Double vlUnitario) {
		this.vlUnitario = vlUnitario;
	}


	public BigDecimal getVlTotal() {
		if(vlTotal == null){
			vlTotal = BigDecimal.valueOf(quantidade*vlUnitario);
		}
		return vlTotal;
	}

	public void setVlTotal(BigDecimal vlTotal) {
		this.vlTotal = vlTotal;
	}



	@Transient
    public String getDataCadastroFormatada(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:MM");
        return sdf.format(this.dataCadastro.getTime());                
    }
    
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public Hospedagem getHospedagem() {
		return hospedagem;
	}

	public void setHospedagem(Hospedagem hospedagem) {
		this.hospedagem = hospedagem;
	}

	
	public OrigemLancamento getOrigemLancamento() {return origemLancamento;}
	
	public void setOrigemLancamento(OrigemLancamento origemLancamento) {this.origemLancamento = origemLancamento;}
	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idHospedagemLancamento == null) ? 0 : idHospedagemLancamento.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HospedagemLancamento other = (HospedagemLancamento) obj;
		if (idHospedagemLancamento == null) {
			if (other.idHospedagemLancamento != null)
				return false;
		} else if (!idHospedagemLancamento.equals(other.idHospedagemLancamento))
			return false;
		return true;
	}
}
