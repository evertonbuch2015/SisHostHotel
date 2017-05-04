package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.buch.core.enumerated.RamoAtividade;
import br.com.buch.core.enumerated.RegimeTributario;

@Entity
@Table(name = "CAD_EMPRESA")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 2733530189950376803L;


	@Id
    @SequenceGenerator(name="G_CAD_EMPRESA", sequenceName="\"G_CAD_EMPRESA\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_EMPRESA")
    @Column(name = "COD_CADEMPRESA")
    private Integer idEmpresa;
	
	
    @Column(name = "CEMP" ,nullable = true , length = 2)
	private String codigo;
    
    
    @Column(name = "CFIL" ,nullable = true , length = 2)
	private String filial;
    
	
	@NotEmpty(message="O Nome deve ser informado!")
    @Column(name = "NOME" ,nullable = true , length = 100)
    private String nomeRazao;

  
    @Column(name = "FANTASIA" ,nullable = true , length = 40)
    private String nomeFantasia;
    
    
    @Column(name = "DOCUMENTO" ,nullable = true , length = 18)
    private String cnpj;
    
    
    @Column(name = "INSCRICAO_ESTADUAL", length = 15)
    private String inscEstadual;
    
    
    @Column(name = "INSCRICAO_MUNICIPAL", length = 15)
    private String inscMunicipal;  
    
  
    @Column(name = "regime_tributario", length = 20)
    @Enumerated(EnumType.STRING)
	private RegimeTributario regimeTributario;
        
    
    @Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    
    
    @Column(name = "HOME_PAGE", length = 28)
    private String site;
    
    
    @Email(message="Informe um e-mail v�lido")
    @Column(name = "EMAIL" , length = 150)
    private String email;
    
        
    @Column(name = "OBS" , length = 255)
    private String obs;
    

    @Column(name = "LOGOTIPO")
    private Byte[] logo;
    
    
    @Column(name = "ramo_atividade" ,nullable = true, length = 15)
    @Enumerated(EnumType.STRING)
    private RamoAtividade ramoAtividade;

    
    @ManyToMany(mappedBy="empresas")
    private List<Usuario> usuarios;
    
    
	//--------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	
	public String getFilial() {
		return filial;
	}
	
	public void setFilial(String filial) {
		this.filial = filial;
	}
	
	public String getNomeRazao() {
		return nomeRazao;
	}

	public void setNomeRazao(String nomeRazao) {
		this.nomeRazao = nomeRazao;
	}


	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}


	public String getCnpj() {
		return cnpj;
	}

	public String getCnpjFormatado(){
		if(this.cnpj != null){
			return 	cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8)
			+ "/" + cnpj.substring(8,12) +"-"+ cnpj.substring(12);
		}else{
			return null;
		}
		
	}

	public void setCnpj(String cnpj) {
		if(cnpj != null){
			this.cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "");
		}
	}


	public String getInscEstadual() {
		return inscEstadual;
	}

	public void setInscEstadual(String inscEstadual) {
		this.inscEstadual = inscEstadual;
	}


	public String getInscMunicipal() {
		return inscMunicipal;
	}

	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}


	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}
	
	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	
	public String getDataCadastroFormatada() {
		if(this.dataCadastro != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(dataCadastro);
		}else{
			return null;
		}
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}	

	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}


	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Byte[] getLogo() {
		return logo;
	}


	public void setLogo(Byte[] logo) {
		this.logo = logo;
	}


	public RamoAtividade getRamoAtividade() {
		return ramoAtividade;
	}


	public void setRamoAtividade(RamoAtividade ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}
	

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
	//--------------------------------	M�todos Auxiliares------------------------------//
	
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

	@Override
	public String toString() {
		return nomeFantasia;
	}
}
