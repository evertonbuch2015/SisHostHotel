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
@Table(name="CAD_CATEGORIA")
public class Categoria implements Serializable{


	private static final long serialVersionUID = -4367708770034258386L;
	
	@Id
    @SequenceGenerator(name="G_CAD_CATEGORIA", sequenceName="\"G_CAD_CATEGORIA\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_CATEGORIA")
    @Column(name = "COD_CADCATEGORIA")
    private Integer idCategoria;
    
    @Column(name="NOME",nullable = true, length = 20)
    private String nome;
    
    
    @Column(name="DESCRICAO",nullable = true, length = 255)
    private String descricao;

    
    //-------------------------------	GETs and SETs------------------------------//
    
    public Integer getIdCategoria() {return idCategoria;}
    public void setIdCategoria(Integer idCategoria) {this.idCategoria = idCategoria;}
    
    
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}


	public String getDescricao() {return descricao;}
	public void setDescricao(String descricao) {this.descricao = descricao;}

	
	//--------------------------------	Métodos Auxiliares------------------------------//

	@Override
	public String toString() {
		return idCategoria +" - "+ nome;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
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
		Categoria other = (Categoria) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		return true;
	}
}
