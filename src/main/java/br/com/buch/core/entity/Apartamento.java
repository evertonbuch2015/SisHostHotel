package br.com.buch.core.entity;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;

import br.com.buch.core.enumerated.SituacaoApartamento;

@Entity
@Table(name="CAD_APARTAMENTO")
public class Apartamento implements Serializable {

	private static final long serialVersionUID = 971479148955247553L;

	
	@Id
    @SequenceGenerator(name="G_CAD_APARTAMENTO", sequenceName="\"G_CAD_APARTAMENTO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_APARTAMENTO")
    @Column(name = "COD_CADAPARTAMENTO")
    private Integer idApartamento;
    
	
	@NotNull(message="Informe o Numero do Apartamento!")
    @Column(name="NUMERO",nullable = true)
    private Integer numero;
    
	
    @Column(name="SITUACAO",nullable = true, length = 20)
    private SituacaoApartamento situacao;
    
    
    @Column(name="RAMAL",nullable = true, length = 10)
    private String ramal;
    
    
    @NotNull(message="Informe a Qtd de camas de casal!")
    @Column(name="CAMAS_CASAL")
    private Integer camasCasal;
    
    
    @NotNull(message="Informe a Qtd de camas de Solteiro!")
    @Column(name="CAMAS_SOLTEIRO")
    private Integer camasSolteiro;
    
    
    @Column(name="DESCRICAO",nullable = true, length = 100)
    private String descricao;
    
    
    @NotNull(message="Informe a Categoria do Apartamento!")
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_CADCATEGORIA")
    private Categoria categoria;   
    
    //-------------------------------	GETs and SETs------------------------------//
    
    
	public Integer getIdApartamento() {	return idApartamento;}
	public void setIdApartamento(Integer idApartamento) {this.idApartamento = idApartamento;}

	public Integer getNumero() {return numero;}
	public void setNumero(Integer numero) {this.numero = numero;}
	
	public SituacaoApartamento getSituacao() {return situacao;}
	public void setSituacao(SituacaoApartamento situacao) {this.situacao = situacao;}
	
	public String getRamal() {return ramal;}
	public void setRamal(String ramal) {this.ramal = ramal;}

	public Integer getCamasCasal() {return camasCasal;}
	public void setCamasCasal(Integer camasCasal) {this.camasCasal = camasCasal;}

	public Integer getCamasSolteiro() {return camasSolteiro;}
	public void setCamasSolteiro(Integer camasSolteiro) {this.camasSolteiro = camasSolteiro;}

	public String getDescricao() {return descricao;}
	public void setDescricao(String descricao) {this.descricao = descricao;}

	public Categoria getCategoria() {return categoria;}
	public void setCategoria(Categoria categoria) {this.categoria = categoria;}
	
	//--------------------------------	Métodos Auxiliares------------------------------//

	@Override
	public String toString() {
		return "Nº: " + numero + "  -  " + categoria.getNome();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idApartamento == null) ? 0 : idApartamento.hashCode());
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
		Apartamento other = (Apartamento) obj;
		if (idApartamento == null) {
			if (other.idApartamento != null)
				return false;
		} else if (!idApartamento.equals(other.idApartamento))
			return false;
		return true;
	}    
}
