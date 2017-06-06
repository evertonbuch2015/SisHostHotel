package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="CRP_ADIANTAMENTO")
public class CrpAdiantamento implements Serializable {

	private static final long serialVersionUID = 5010332039177303462L;

	
	@Id
    @SequenceGenerator(name="G_CRP_ADIANTAMENTO", sequenceName="\"G_CRP_ADIANTAMENTO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CRP_ADIANTAMENTO")
    @Column(name = "COD_CRP_ADIANTAMENTO")
    private Integer idAdiantamento;

    
    @Column(name = "CODIGO")
    private String codigo;
    
    
    @Column(name = "descricao", length = 100, nullable = true)
    private String descricao;
    
    
    @Column(name = "DATA_EMISSAO")
    @Temporal(TemporalType.DATE)
    private Calendar dtEmissao;
    
    
    @Column(name = "DATA_BAIXA")
    @Temporal(TemporalType.DATE)
    private Calendar dtBaixa;
    
    
    @Column(name = "VALOR")
    private Double valor;
    
    
    @Column(name = "SALDO")
    private Double saldo;
    
    
    @Column(name = "OBS" ,nullable = true , length = 255)
    private String obs;
    
    
    @OneToOne
    @JoinColumn(name ="COD_CADHOSPEDE")
    private Hospede hospede;
    
    
    //--------------------------------	GETs and SETs------------------------------//
    
    
    public Integer getIdAdiantamento() {
		return idAdiantamento;
	}

	public void setIdAdiantamento(Integer idAdiantamento) {
		this.idAdiantamento = idAdiantamento;
	}

	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	public Calendar getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Calendar dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	
	public Calendar getDtBaixa() {
		return dtBaixa;
	}

	public void setDtBaixa(Calendar dtBaixa) {
		this.dtBaixa = dtBaixa;
	}

	
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	
	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	
	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	
	public Hospede getHospede() {
		return hospede;
	}

	public void setHospede(Hospede hospede) {
		this.hospede = hospede;
	}

	
	@Transient
    public String getDataEmissaoFormatada(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.dtEmissao.getTime());                
    }
    
    @Transient
    public String getDataBaixaFormatada(){
        if(this.dtBaixa != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(this.dtBaixa.getTime()); 
        }else{
            return null;
        }
                       
    }
    
    
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
		CrpAdiantamento other = (CrpAdiantamento) obj;
		if (idAdiantamento == null) {
			if (other.idAdiantamento != null)
				return false;
		} else if (!idAdiantamento.equals(other.idAdiantamento))
			return false;
		return true;
	}    
}
