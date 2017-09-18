package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import br.com.buch.core.enumerated.Unidade;

@Entity
@Table(name="CAD_PRODUTOS")
public class Produto implements Serializable{

	private static final long serialVersionUID = 1587711517325382685L;

	@Id
    @SequenceGenerator(name="G_CAD_PRODUTOS", sequenceName="\"G_CAD_PRODUTOS\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_PRODUTOS")
    @Column(name = "COD_CADPRODUTO")
    private Integer idProduto;
    	
    @Column(name = "CODIGO")
    private Integer codigo;    

    @Column(name = "NOME",length = 150)
    private String nome;   
    
    @Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    
    @Column(name = "VALOR")
    private Double valor;
    
    @Column(name = "UNIDADE")
    @Enumerated(EnumType.STRING)
    private Unidade unidade;
    
    @Column(name = "QTD_ESTOQUE")
    private Integer qtdEstoque;
    
    @Column(name = "ESTOQUE_MINIMO")
    private Integer estoqueMinimo;
    
    @Column(name = "ESTOQUE_MAXIMO")
    private Integer estoqueMaximo;
            
    @Column(name = "ATIVO")
    private Character ativo;
    
    @Column(name = "OBS",length= 255)
    private String obs;
    
    
    //-------------------------------	GETs and SETs------------------------------//

	public Integer getIdProduto() {return idProduto;}
	public void setIdProduto(Integer idProduto) {this.idProduto = idProduto;}

	public Integer getCodigo() {return codigo;}
	public void setCodigo(Integer codigo) {this.codigo = codigo;}

	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}


	public String getDataCadastroFormatada(){
		return (this.dataCadastro != null) ? new SimpleDateFormat("dd/MM/yyyy").format(this.dataCadastro): "";
	}
	public Date getDataCadastro() {return dataCadastro;}
	public void setDataCadastro(Date dataCadastro) {this.dataCadastro = dataCadastro;}

	public Double getValor() {return valor;}
	public void setValor(Double valor) {this.valor = valor;}

	public Unidade getUnidade() {return unidade;}
	public void setUnidade(Unidade unidade) {this.unidade = unidade;}

	public Integer getQtdEstoque() {return qtdEstoque;}
	public void setQtdEstoque(Integer qtdEstoque) {this.qtdEstoque = qtdEstoque;}

	public Integer getEstoqueMinimo() {return estoqueMinimo;}
	public void setEstoqueMinimo(Integer estoqueMinimo) {this.estoqueMinimo = estoqueMinimo;}

	public Integer getEstoqueMaximo() {return estoqueMaximo;}
	public void setEstoqueMaximo(Integer estoqueMaximo) {this.estoqueMaximo = estoqueMaximo;}

	
	public Boolean isAtivo(){
		if(ativo == null){return null;}
		return ativo.equals('S')?true:false;
	}
	public Character getAtivo() {return ativo;}
	public void setAtivo(Character ativo) {this.ativo = ativo;}

	public String getObs() {return obs;}
	public void setObs(String obs) {this.obs = obs;}
	
	//--------------------------------	Métodos Auxiliares------------------------------//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProduto == null) ? 0 : idProduto.hashCode());
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
		Produto other = (Produto) obj;
		if (idProduto == null) {
			if (other.idProduto != null)
				return false;
		} else if (!idProduto.equals(other.idProduto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return codigo + " - " + nome;
	}
}
