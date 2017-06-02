package br.com.buch.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
*
* @author Everton de Souza
*/

@Entity
@Table(name="CAD_TIPO_TARIFA")
public class TipoTarifa implements Serializable {

	@Id
    @SequenceGenerator(name="G_CAD_TIPO_TARIFA", sequenceName="\"G_CAD_TIPO_TARIFA\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_TIPO_TARIFA")
    @Column(name = "COD_CADTIPOTARIFA")
    private Integer idTipoTarifa;
    
    @Column(name="NOME", length = 40)
    private String nome;
	
    @Column(name="TARIFA_MANUAL", length = 1)
    private Character tarifaManual;

    
    //--------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdTipoTarifa() {
		return idTipoTarifa;
	}

	public void setIdTipoTarifa(Integer idTipoTarifa) {
		this.idTipoTarifa = idTipoTarifa;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	
	//Métodos Modificados
	public Boolean getTarifaManual() {
		if(this.tarifaManual == null) return null;
		return tarifaManual.equals('S') ? true : false;
	}

	public void setTarifaManual(Boolean value) {
		if(value == null){ 
			this.tarifaManual = null;
		}else{
			this.tarifaManual = value == true ? 'S' : 'N';
		}
	}
	
	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTipoTarifa == null) ? 0 : idTipoTarifa.hashCode());
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
		TipoTarifa other = (TipoTarifa) obj;
		if (idTipoTarifa == null) {
			if (other.idTipoTarifa != null)
				return false;
		} else if (!idTipoTarifa.equals(other.idTipoTarifa))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return nome ;
	}
}
