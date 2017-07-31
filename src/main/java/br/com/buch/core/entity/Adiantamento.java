package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name="ADIANTAMENTO_CLIENTE")
public class Adiantamento implements Serializable {

	private static final long serialVersionUID = 5010332039177303462L;

	
	@Id
	@SequenceGenerator(name="G_ADIANTAMENTO_CLIENTE", sequenceName="\"G_ADIANTAMENTO_CLIENTE\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_ADIANTAMENTO_CLIENTE")
    @Column(name = "COD_ADIANTAMENTO")
    private Integer idAdiantamento;

    
    @Column(name = "CODIGO")
    private String codigo;
    
    
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;
    
    
    @Column(name = "DATA_EMISSAO")
    @Temporal(TemporalType.DATE)
    private Date dtEmissao;
    
    
    @Column(name = "DATA_BAIXA")
    @Temporal(TemporalType.DATE)
    private Date dtBaixa;
    
    
    @Column(name = "VALOR")
    private Double valor;
    
    
    @Column(name = "SALDO")
    private Double saldo;
       
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name ="COD_CADHOSPEDE")
    private Hospede hospede;
    
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COD_CADCAIXABANCO")
    private Banco localRecebimento;
    
    
    //--------------------------------	GETs and SETs------------------------------//
    
    
    public Integer getIdAdiantamento() {return idAdiantamento;}
	public void setIdAdiantamento(Integer idAdiantamento) {this.idAdiantamento = idAdiantamento;}

	
	public String getCodigo() {return codigo;}
	public void setCodigo(String codigo) {this.codigo = codigo;}

	
	public String getDescricao() {return descricao;	}
	public void setDescricao(String descricao) {this.descricao = descricao;	}

	
	@Transient
    public String getDataEmissaoFormatada(){
        return (dtEmissao != null) ? new SimpleDateFormat("dd/MM/yyyy").format(this.dtEmissao.getTime()) : "";                
    }	
	public Date getDtEmissao() {return dtEmissao;}
	public void setDtEmissao(Date dtEmissao) {this.dtEmissao = dtEmissao;}


	@Transient
    public String getDataBaixaFormatada(){
    	return (dtBaixa != null) ? new SimpleDateFormat("dd/MM/yyyy").format(this.dtBaixa.getTime()) : "";                       
    }
	public Date getDtBaixa() {return dtBaixa;}
	public void setDtBaixa(Date dtBaixa) {this.dtBaixa = dtBaixa;}

	
	public Double getValor() {return valor;}
	public void setValor(Double valor) {this.valor = valor;}

	
	public Double getSaldo() {return saldo;}
	public void setSaldo(Double saldo) {this.saldo = saldo;	}


	public Hospede getHospede() {return (hospede == null)? new Hospede() : hospede;	}
	public void setHospede(Hospede hospede) {this.hospede = hospede;}
	
	public Banco getLocalRecebimento() {return (localRecebimento == null)? new Banco(): localRecebimento;}	
	public void setLocalRecebimento(Banco localRecebimento) {this.localRecebimento = localRecebimento;	}
    
    //--------------------------------	Métodos Auxiliares------------------------------//
	
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAdiantamento == null) ? 0 : idAdiantamento.hashCode());
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
		Adiantamento other = (Adiantamento) obj;
		if (idAdiantamento == null) {
			if (other.idAdiantamento != null)
				return false;
		} else if (!idAdiantamento.equals(other.idAdiantamento))
			return false;
		return true;
	}    
}
