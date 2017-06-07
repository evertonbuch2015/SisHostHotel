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
public class CaixaBanco implements Serializable{

	private static final long serialVersionUID = -8172847214408341346L;

	
	@Id
    @SequenceGenerator(name="G_CAD_CAIXABANCO", sequenceName="\"G_CAD_CAIXABANCO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_CAIXABANCO")
    @Column(name = "COD_CADCAIXABANCO")
    private Integer idCaixaBanco;
    
    
    @Column(name="CODIGO")
    private Integer codigo;
    
    @Column(name="NOME",nullable = false,length = 60)
    private String nome;
    
    
    @Column(name="CONTA_CORRENTE",nullable = true,length = 20)
    private String contaCorrente;
        
    @Column(name="AGENCIA",nullable = true,length = 10)
    private String agencia;

    @Column(name="DIGITO",nullable = true,length = 10)
    private String digito;

        
    //--------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdCaixaBanco() {
		return idCaixaBanco;
	}

	public void setIdCaixaBanco(Integer idCaixaBanco) {
		this.idCaixaBanco = idCaixaBanco;
	}

	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	
	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	
	public String getDigito() {
		return digito;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCaixaBanco == null) ? 0 : idCaixaBanco.hashCode());
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
		CaixaBanco other = (CaixaBanco) obj;
		if (idCaixaBanco == null) {
			if (other.idCaixaBanco != null)
				return false;
		} else if (!idCaixaBanco.equals(other.idCaixaBanco))
			return false;
		return true;
	}
    
	
	@Override
	public String toString() {	
		return codigo + " - "+ nome;
	}
}
