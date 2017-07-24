package br.com.buch.core.entity;

import java.io.Serializable;
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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="CAD_EMPRESA")
public class Empresa implements Serializable {

	private static final long serialVersionUID = -4732933043750247762L;

	@Id
    @SequenceGenerator(name="G_CAD_EMPRESA", sequenceName="\"G_CAD_EMPRESA\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_EMPRESA")
    @Column(name = "COD_CADEMPRESA")
    private Integer idEmpresa;
	
	
    @Column(name = "CODIGO" ,nullable = true , length = 2, unique = true)
	private String codigo;
   
	
	@NotEmpty(message="O Nome deve ser informado!")
    @Column(name = "NOME_RAZAO" ,nullable = true , length = 70)
    private String nomeRazao;

  
    @Column(name = "NOME_FANTASIA" ,nullable = true , length = 70)
    private String nomeFantasia;
    
    
    @Column(name = "DOCUMENTO" ,nullable = true , length = 18, unique = true)
    private String documento;
    
    
    @Column(name = "INSCRICAO_ESTADUAL", length = 15)
    private String inscEstadual;
    
    
    @Column(name = "INSCRICAO_MUNICIPAL", length = 15)
    private String inscMunicipal;  
            
    
    @Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    
    
    @Email(message="Informe um e-mail válido")
    @Column(name = "EMAIL" , length = 60)
    private String email;
    
    
    @Column(name = "HOME_PAGE", length = 50)
    private String site;
    
    
    @Column(name = "TELEFONE1", length = 20)
    private String telefone1;
    
    
    @Column(name = "TELEFONE2", length = 20)
    private String telefone2;
    
            
    @Column(name = "OBS" , length = 255)
    private String obs;
    
    
    @Column(name = "ATIVO")
    private Character ativo;
    
     
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name ="COD_CADENDERECO")
    private Endereco endereco;


    //-------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdEmpresa() {return idEmpresa;}
	public void setIdEmpresa(Integer idEmpresa) {this.idEmpresa = idEmpresa;}


	public String getCodigo() {return codigo;}
	public void setCodigo(String codigo) {this.codigo = codigo;}


	public String getNomeRazao() {return nomeRazao;}
	public void setNomeRazao(String nomeRazao) {this.nomeRazao = nomeRazao;}


	public String getNomeFantasia() {return nomeFantasia;}
	public void setNomeFantasia(String nomeFantasia) {this.nomeFantasia = nomeFantasia;}


	public String getDocumento() {return documento;}
	public void setDocumento(String documento) {this.documento = documento;}


	public String getInscEstadual() {return inscEstadual;}
	public void setInscEstadual(String inscEstadual) {this.inscEstadual = inscEstadual;}


	public String getInscMunicipal() {return inscMunicipal;}
	public void setInscMunicipal(String inscMunicipal) {this.inscMunicipal = inscMunicipal;}


	public Date getDataCadastro() {return dataCadastro;}
	public void setDataCadastro(Date dataCadastro) {this.dataCadastro = dataCadastro;}

	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}


	public String getSite() {return site;}
	public void setSite(String site) {this.site = site;}


	public String getTelefone1() {return telefone1;}
	public void setTelefone1(String telefone1) {this.telefone1 = telefone1;}


	public String getTelefone2() {return telefone2;}
	public void setTelefone2(String telefone2) {this.telefone2 = telefone2;}


	public String getObs() {return obs;}
	public void setObs(String obs) {this.obs = obs;}


	public Boolean isAtivo() {		
		return (ativo == null) ? null: (ativo.equals('S')) ? true : false;
	}
	public void setAtivo(Boolean value) {
		if (value == null) {this.ativo = null;} 
		else {this.ativo = ((value == true) ? 'S' : 'N');}
	}


	public Endereco getEndereco() {return (endereco == null) ? new Endereco() : endereco;}
	public void setEndereco(Endereco endereco) {this.endereco = endereco;}
	
	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
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
		Empresa other = (Empresa) obj;
		if (idEmpresa == null) {
			if (other.idEmpresa != null)
				return false;
		} else if (!idEmpresa.equals(other.idEmpresa))
			return false;
		return true;
	}	
}
