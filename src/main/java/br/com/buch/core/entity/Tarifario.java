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

/**
*
* @author Everton de Souza
*/

@Entity
@Table(name="CAD_TARIFARIO")
public class Tarifario implements Serializable {
	
	@Id
    @SequenceGenerator(name="G_CAD_TARIFARIO", sequenceName="\"G_CAD_TARIFARIO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_TARIFARIO")
    @Column(name = "COD_CADTARIFARIO")
    private Integer idTarifario;
    
    
    @Column(name = "DATA_INICIAL")
    @Temporal(TemporalType.DATE)
    private Date dataInicial;
    
    @Column(name = "DATA_FINAL")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;
    
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_CADCATEGORIA")
    private Categoria categoria;
    
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_CADTIPOTARIFA")
    private TipoTarifa tipoTarifa;
    
    
    @Column(name="VALOR")
    private Double valor;

    
    //--------------------------------	GETs and SETs------------------------------//
	
    public Integer getIdTarifario() {
		return idTarifario;
	}

	public void setIdTarifario(Integer idTarifario) {
		this.idTarifario = idTarifario;
	}


	
	public String getDataInicialFormatada(){
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dataInicial);
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	
	
	public String getDataFinalFormatada(){
		return new SimpleDateFormat("dd/MM/yyyy").format(this.dataFinal);
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public TipoTarifa getTipoTarifa() {
		return tipoTarifa;
	}

	public void setTipoTarifa(TipoTarifa tipoTarifa) {
		this.tipoTarifa = tipoTarifa;
	}


	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}


	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTarifario == null) ? 0 : idTarifario.hashCode());
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
		Tarifario other = (Tarifario) obj;
		if (idTarifario == null) {
			if (other.idTarifario != null)
				return false;
		} else if (!idTarifario.equals(other.idTarifario))
			return false;
		return true;
	}
}
