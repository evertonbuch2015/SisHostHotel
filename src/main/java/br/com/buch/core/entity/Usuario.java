package br.com.buch.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.buch.core.enumerated.GrupoUsuario;

/**
 *
 * @author Everton
 */
@Entity
@Table(name = "SIS_USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -2880609378828789038L;

	
	@Id
	@SequenceGenerator(name = "G_SIS_USUARIO", sequenceName = "\"G_SIS_USUARIO\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G_SIS_USUARIO")
	@Column(name = "COD_SISUSUARIO")
	private Integer idUsusario;

	
	@NotEmpty(message = "O Usuário deve ser informado!")
	@Column(name = "NOME_USUARIO", nullable = true, length = 20)
	private String nomeUsuario;

	@Column(name = "SENHA", nullable = true, length = 70)
	private String senha;
	
	
	@Column(name = "NOME_COLABORADOR", nullable = false, length = 40)
	private String nomeColaborador;
		
	@Column(name="EMAIL" , nullable = false, length = 60)
	private String email;	
	
	@Column(name="FRASE_SECRETA" , nullable = false, length = 60)
	private String fraseSecreta;

	@Column(name = "GRUPO_USUARIO", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private GrupoUsuario grupo;
	
	@NotEmpty(message = "O Setor deve ser informado!")
	@Column(name = "SETOR", length = 50)
	private String setor;

	@Column(name = "ATIVO")
	private Character ativo;

	@Column(name = "EM_FERIAS")
	private Character emFerias;
	

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SIS_USUARIO_HOTEL", joinColumns = {
			@JoinColumn(name = "COD_SISUSUARIO") }, inverseJoinColumns = { @JoinColumn(name = "COD_CADHOTEL") })
	private List<Hotel> hoteis;

	// -------------------------------- GETs and SETs------------------------------//
	
	public Integer getIdUsusario() {return idUsusario;}

	public void setIdUsusario(Integer idUsusario) {	this.idUsusario = idUsusario;}

	
	public String getNomeColaborador() {return nomeColaborador;}

	public void setNomeColaborador(String nomeColaborador) {this.nomeColaborador = nomeColaborador;}

	
	public String getNomeUsuario() {return nomeUsuario;}

	public void setNomeUsuario(String nomeUsuario) {this.nomeUsuario = nomeUsuario;}

	
	public GrupoUsuario getGrupoUsuario() {return grupo;}

	public void setGrupoUsuario(GrupoUsuario grupo) {this.grupo = grupo;}


	public List<Hotel> getHoteis() {
		if (this.hoteis == null) {
			this.hoteis = new ArrayList<Hotel>();
		}
		return hoteis;
	}

	public void setHoteis(List<Hotel> hoteis) {this.hoteis = hoteis;}

	
	public String getSetor() {return setor;}

	public void setSetor(String setor) {this.setor = setor;}

	
	public Character getAtivo() {return ativo;}
	
	public void setAtivo(Character ativo) {this.ativo = ativo;}
	
	
	public Character getEmFerias() {return emFerias;}

	public void setEmFerias(Character emFerias) {this.emFerias = emFerias;}

	
	public String getSenha() {return senha;}

	public void setSenha(String senha) {this.senha = senha;}
	
	
	public String getEmail() {return email;}

	public void setEmail(String email) {this.email = email;}


	public String getFraseSecreta() {return fraseSecreta;}

	public void setFraseSecreta(String fraseSecreta) {this.fraseSecreta = fraseSecreta;}
	
	// -------------------------------- Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsusario == null) ? 0 : idUsusario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsusario == null) {
			if (other.idUsusario != null)
				return false;
		} else if (!idUsusario.equals(other.idUsusario))
			return false;
		return true;
	}
}