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

@Entity
@Table(name = "CAD_HOSPEDE")
public class Hospede implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7642812451427541465L;

	@Id
    @SequenceGenerator(name="G_CAD_HOSPEDE", sequenceName="\"G_CAD_HOSPEDE\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CAD_HOSPEDE")
    @Column(name = "COD_CADHOSPEDE")
    private Integer idHospede;
    
    
    @Column(name = "CODIGO", unique = true)
	private Integer codigo;

	
    @Column(name = "NOME" ,nullable = false , length = 50)
    private String nome;
    
    
    @Column(name = "CPF" ,nullable = false , length = 20)
    private String cpf;
    
    
    @Column(name = "RG" ,nullable = false , length = 20)
    private String rg;
    
    
    @Column(name = "PASSAPORTE" ,nullable = true , length = 10)
    private String passaporte;
    
    
    @Column(name = "NACIONALIDADE" ,nullable = true , length = 40)
    private String nacionalidade;
    
    
    @Column(name = "PROFISSAO" ,nullable = true , length = 40)
    private String profissao;

        
    @Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    
    
    @Column(name = "DATA_NASCIMENTO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    
    
    @Column(name = "SEXO" ,nullable = true , length = 10)
    private String sexo;
    
    
    @Column(name = "ESTADO_CIVIL" ,nullable = true , length = 10)
    private String estadoCivil;
      
    
    @Column(name = "EMAIL" ,nullable = false , length = 40)
    private String email;
       
    
    @Column(name = "ATIVO")
    private Character ativo;
    
    
    @Column(name = "AUTORIZA_EMAIL")
    private Character autorizaEmail;
    
    
    @Column(name = "OBS" ,nullable = true , length = 255)
    private String obs;
       
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_CADENDERECO")
    private Endereco endereco;
    
    
    @OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name ="COD_CADEMPRESA")
    private Empresa empresa;


    //-------------------------------	GETs and SETs------------------------------//
    
	public Integer getIdHospede() {
		return idHospede;
	}

	public void setIdHospede(Integer idHospede) {
		this.idHospede = idHospede;
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


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}


	public String getPassaporte() {
		return passaporte;
	}

	public void setPassaporte(String passaporte) {
		this.passaporte = passaporte;
	}


	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}


	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}


	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Boolean isAtivo() {
		if (this.ativo == null)
			return null;
		
		return ativo.equals('S') ? true : false;
	}

	public void setAtivo(Boolean value) {
		if (value == null) {
			this.ativo = null;
		} else {
			this.ativo = value == true ? 'S' : 'N';
		}
	}
	

	public Boolean isAutorizaEmail() {
		if (this.autorizaEmail == null)
			return null;
		
		return autorizaEmail.equals('S') ? true : false;
	}

	public void setAutorizaEmail(Boolean value) {
		if (value == null) {
			this.autorizaEmail = null;
		} else {
			this.autorizaEmail = value == true ? 'S' : 'N';
		}
	}


	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}


	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	
	
	//--------------------------------	Métodos Auxiliares------------------------------//	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idHospede == null) ? 0 : idHospede.hashCode());
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
		Hospede other = (Hospede) obj;
		if (idHospede == null) {
			if (other.idHospede != null)
				return false;
		} else if (!idHospede.equals(other.idHospede))
			return false;
		return true;
	}	
}
