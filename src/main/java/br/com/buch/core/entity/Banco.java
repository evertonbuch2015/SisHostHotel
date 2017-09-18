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
 * @author Everton Buchkorn
 */

@Entity
@Table(name="CAD_CAIXABANCO")
public class Banco implements Serializable{

	private static final long serialVersionUID = -8172847214408341346L;

	
	@Id
    @SequenceGenerator(name="G_CAD_CAIXABANCO", sequenceName="\"G_CAD_CAIXABANCO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_CAIXABANCO")
    @Column(name = "COD_CADCAIXABANCO")
    private Integer idBanco;
    
    
    @Column(name="CODIGO")
    private Integer codigo;
    
    @Column(name="NOME",nullable = false,length = 60)
    private String nome;
    
        
    //--------------------------------	GETs and SETs------------------------------//
    
    public Integer getIdBanco() {return idBanco;}
    public void setIdBanco(Integer idBanco) {this.idBanco = idBanco;}
	
	public Integer getCodigo() {return codigo;}
	public void setCodigo(Integer codigo) {this.codigo = codigo;}

	
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	
	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBanco == null) ? 0 : idBanco.hashCode());
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
		Banco other = (Banco) obj;
		if (idBanco == null) {
			if (other.idBanco != null)
				return false;
		} else if (!idBanco.equals(other.idBanco))
			return false;
		return true;
	}

	
	@Override
	public String toString() {	
		return codigo + " - "+ nome;
	}
}
