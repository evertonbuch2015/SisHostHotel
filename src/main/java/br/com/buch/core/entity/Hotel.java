package br.com.buch.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import br.com.buch.core.enumerated.RegimeTributario;

@Entity
@Table(name="CAD_HOTEL")
public class Hotel implements Serializable {

	private static final long serialVersionUID = 2733530189950376803L;


	@Id
    @SequenceGenerator(name="G_CAD_HOTEL", sequenceName="\"G_CAD_HOTEL\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_HOTEL")
    @Column(name = "COD_CADHOTEL")
    private Integer idHotel;
	
	
    @Column(name = "CODIGO" ,nullable = true , length = 2, unique = true)
	private String codigo;
    
    
    @Column(name = "FILIAL" ,nullable = true , length = 2, unique = true)
	private String filial;
    
	
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
    
  
    @Column(name = "REGIME_TRIBUTARIO", length = 20)
    @Enumerated(EnumType.STRING)
	private RegimeTributario regimeTributario;
        
    
    @Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    
    
    @Email(message="Informe um e-mail v�lido")
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
    

    @Column(name = "LOGOTIPO")
    private Byte[] logo;
    
     
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_CADENDERECO")
    private Endereco endereco;
    
	//--------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdHotel() {
		return idHotel;
	}
	
	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
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


	public String getDocumento() {
		return documento;
	}

	public String getDocumentoFormatado(){
		if(this.documento != null){
			return 	documento.substring(0, 2) + "." + documento.substring(2, 5) + "." + documento.substring(5, 8)
			+ "/" + documento.substring(8,12) +"-"+ documento.substring(12);
		}else{
			return null;
		}
		
	}

	public void setDocumento(String cnpj) {
		if(cnpj != null){
			this.documento = cnpj.replace(".", "").replace("-", "").replace("/", "");
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

	
	public String getTelefone1() {
		return telefone1;
	}
	
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	
	
	public String getTelefone2() {
		return telefone2;
	}
	
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
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
	
	
	public Endereco getEndereco() {
		if(this.endereco == null){
			this.endereco = new Endereco();
		}
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	
	@Override
	public String toString() {
		return nomeFantasia;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idHotel == null) ? 0 : idHotel.hashCode());
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
		Hotel other = (Hotel) obj;
		if (idHotel == null) {
			if (other.idHotel != null)
				return false;
		} else if (!idHotel.equals(other.idHotel))
			return false;
		return true;
	}
}
