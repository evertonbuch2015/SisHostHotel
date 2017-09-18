package br.com.buch.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name="CONSUMO")
public class Consumo implements Serializable{

	private static final long serialVersionUID = 4607195753277439893L;

	
	@Id
    @SequenceGenerator(name="G_CONSUMO", sequenceName="\"G_CONSUMO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CONSUMO")
    @Column(name = "COD_CONSUMO")
    private Integer idConsumo;
	
	
	@Column(name="QUANTIDADE")
	private Integer quantidade;

	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CONSUMO")
	private Date dataConsumo;
	
	@Transient
	private BigDecimal valorTotal;
	
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name ="COD_CADPRODUTO")
	private Produto produto;
	
	
	@OneToOne
    @JoinColumn(name ="COD_HOSPEDAGEM")
    private Hospedagem hospedagem;

	
	//-------------------------------	GETs and SETs------------------------------//
	
	public Integer getIdConsumo() {return idConsumo;}
	public void setIdConsumo(Integer idConsumo) {this.idConsumo = idConsumo;}

	public Integer getQuantidade() {return quantidade;}
	public void setQuantidade(Integer quantidade) {this.quantidade = quantidade;}

	
	public String getDataConsumoFormatada(){
		return (this.dataConsumo != null) ? new SimpleDateFormat("dd/MM/yyyy").format(this.dataConsumo): "";
	}
	public Date getDataConsumo() {return dataConsumo;}
	public void setDataConsumo(Date dataConsumo) {this.dataConsumo = dataConsumo;}

	
	public BigDecimal getValorTotal() {
		if(quantidade != null && produto != null){
			valorTotal = BigDecimal.valueOf(quantidade * produto.getValor().doubleValue());
		}
		return valorTotal;
	}

	public Produto getProduto() {return produto;}
	public void setProduto(Produto produto) {this.produto = produto;}
	
	public Hospedagem getHospedagem() {return hospedagem;}
	public void setHospedagem(Hospedagem hospedagem) {this.hospedagem = hospedagem;}
	
	
	//--------------------------------	Métodos Auxiliares------------------------------//	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConsumo == null) ? 0 : idConsumo.hashCode());
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
		Consumo other = (Consumo) obj;
		if (idConsumo == null) {
			if (other.idConsumo != null)
				return false;
		} else if (!idConsumo.equals(other.idConsumo))
			return false;
		return true;
	}	
}
