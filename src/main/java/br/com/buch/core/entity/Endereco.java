package br.com.buch.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_ENDERECO")
public class Endereco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6239389754217648981L;



	@Id
    @SequenceGenerator(name="G_CAD_ENDERECO", sequenceName="\"G_CAD_ENDERECO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_ENDERECO")
    @Column(name = "COD_CADENDERECO")
    private Integer idEndereco;

    
    @Column(name = "LOGRADOURO" ,length = 20,nullable = true)
    private String logradouro;

    
    @Column(name = "RUA" ,length = 100,nullable = true)
    private String rua;

    
    @Column(name = "NUMERO" ,nullable = true)
    private Integer numero;

    
    @Column(name = "COMPLEMENTO" ,length = 50,nullable = true)
    private String complemento;

    
    @Column(name = "BAIRRO" ,length = 50 ,nullable = true)
    private String bairro;

    
    @Column(name = "CEP" ,length = 50 ,nullable = true)
    private String cep;

    
    @Column(name = "ESTADO" ,length = 2 ,nullable = true)
    private String estado;

    
    @Column(name = "CIDADE" ,length = 50 ,nullable = true)
    private String cidade;
    
    
    @Column(name = "PAIS" ,length = 20 ,nullable = true)
    private String pais;
      
    
    @Column(name = "CODIGO_MUNICIPIO", nullable = true)
    private Integer codigoMunicipio;


    //--------------------------------	GETs and SETs------------------------------//
    
    
	public Integer getIdEndereco() {
		return idEndereco;
	}


	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getRua() {
		return rua;
	}


	public void setRua(String rua) {
		this.rua = rua;
	}


	public Integer getNumero() {
		return numero;
	}


	public void setNumero(Integer numero) {
		this.numero = numero;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}


	public Integer getCodigoMunicipio() {
		return codigoMunicipio;
	}


	public void setCodigoMunicipio(Integer codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEndereco == null) ? 0 : idEndereco.hashCode());
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
		Endereco other = (Endereco) obj;
		if (idEndereco == null) {
			if (other.idEndereco != null)
				return false;
		} else if (!idEndereco.equals(other.idEndereco))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return rua + ", " + numero + " - " + cidade + " - " + estado;
	}
}
