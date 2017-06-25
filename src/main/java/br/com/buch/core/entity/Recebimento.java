package br.com.buch.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="RECEBIMENTO")
public class Recebimento implements Serializable {
	
	private static final long serialVersionUID = 2394833593975015801L;

	public enum OrigemRecebimento{
		ADIANTAMENTO("Adiantamento"),
		HOSPEDAGEM("Hospedagem"),
		ENTRADA_MANUAL("Entrada Manual");
		
		private String label;
		
		private OrigemRecebimento(String label){
			this.label = label;
		}
		
		public String getLabel(){
			return label;
		}

	}
	
	
	@Id
    @SequenceGenerator(name="G_RECEBIMENTO", sequenceName="\"G_RECEBIMENTO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_RECEBIMENTO")
    @Column(name = "COD_RECEBIMENTO")
    private Integer idRecebimento;
	
	
	@Column(name="NUMERO")
    private Integer numero;
	
	
	@Column(name="DOCUMENTO_ORIGEM")
    private Integer documentoOrigem;
	
	
	@Column(name="ORIGEM_RECEBIMENTO", length=20)
	@Enumerated(EnumType.STRING)
    private OrigemRecebimento origemRecebimento;
	
	
	@Column(name="DESCRICAO",nullable = false, length=100)
    private String descricao;
	
	
	@Column(name = "DATA_EMISSAO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtEmissao;
	
	
	@Column(name="VALOR")
    private Double valor;
	
	
	@OneToOne()
    @JoinColumn(name ="COD_CADCAIXABANCO")
    private CaixaBanco localRecebimento;

	
	//--------------------------------	GETs and SETs------------------------------//
	
	public Integer getIdRecebimento() {
		return idRecebimento;
	}

	public void setIdRecebimento(Integer idRecebimento) {
		this.idRecebimento = idRecebimento;
	}


	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}


	public Integer getDocumentoOrigem() {
		return documentoOrigem;
	}

	public void setDocumentoOrigem(Integer documentoOrigem) {
		this.documentoOrigem = documentoOrigem;
	}


	public OrigemRecebimento getOrigemRecebimento() {
		return origemRecebimento;
	}

	public void setOrigemRecebimento(OrigemRecebimento origemRecebimento) {
		this.origemRecebimento = origemRecebimento;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}


	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}


	public CaixaBanco getLocalRecebimento() {
		return localRecebimento;
	}

	public void setLocalRecebimento(CaixaBanco localRecebimento) {
		this.localRecebimento = localRecebimento;
	}

	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRecebimento == null) ? 0 : idRecebimento.hashCode());
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
		Recebimento other = (Recebimento) obj;
		if (idRecebimento == null) {
			if (other.idRecebimento != null)
				return false;
		} else if (!idRecebimento.equals(other.idRecebimento))
			return false;
		return true;
	}
	
	
		
}
