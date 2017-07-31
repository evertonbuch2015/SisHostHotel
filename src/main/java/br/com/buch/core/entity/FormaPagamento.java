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
@Table(name="CAD_FORMA_PAGAMENTO")
public class FormaPagamento implements Serializable{

	
	private static final long serialVersionUID = 696533888224034459L;


	@Id
    @SequenceGenerator(name="G_CAD_FORMA_PAGAMENTO", sequenceName="\"G_CAD_FORMA_PAGAMENTO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_FORMA_PAGAMENTO")
    @Column(name = "COD_CADFORMAPAGAMENTO")
    private Integer idFormaPag;
    
    
	@Column(name = "CODIGO")
    private Integer codigo;
    
    
    @Column(name = "DESCRICAO", length = 60)
    private String descricao;
    
    
    @Column(name = "PARCELAS")
    private Integer parcelas;

    
    //-------------------------------	GETs and SETs------------------------------//
	
    public Integer getIdFormaPag() {return idFormaPag;}

	public void setIdFormaPag(Integer idFormaPag) {this.idFormaPag = idFormaPag;}


	public Integer getCodigo() {return codigo;}

	public void setCodigo(Integer codigo) {this.codigo = codigo;}


	public String getDescricao() {return descricao;}

	public void setDescricao(String descricao) {this.descricao = descricao;}


	public Integer getParcelas() {return parcelas;}
	
	public void setParcelas(Integer parcelas) {this.parcelas = parcelas;}


	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFormaPag == null) ? 0 : idFormaPag.hashCode());
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
		FormaPagamento other = (FormaPagamento) obj;
		if (idFormaPag == null) {
			if (other.idFormaPag != null)
				return false;
		} else if (!idFormaPag.equals(other.idFormaPag))
			return false;
		return true;
	}


	@Override
	public String toString() {	
		return descricao;
	}
}
