package br.com.buch.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.buch.view.util.Criptografia;

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
	@Column(name = "CODIGOSISUSUARIO")
	private Integer idUsusario;

	@Column(name = "NOME_COLABORADOR", nullable = false, length = 40)
	private String nomeColaborador;

	@NotEmpty(message = "O Usu�rio deve ser informado!")
	@Column(name = "NOME", nullable = true, length = 20)
	private String nomeUsuario;


	@NotEmpty(message = "O C�digo Estrutural deve ser informado!")
	@Column(name = "CODIGO_ESTRUTURAL",length = 20)
	private String codigoEstrutural;
	
	@Column(name = "PWD", nullable = true, length = 70)
	private String senha;

	
	@NotEmpty(message = "O Grupo deve ser informado!")
	@Column(name = "GRUPO", nullable = false, length = 40)
	private String grupo;
	
	@NotEmpty(message = "O Setor deve ser informado!")
	@Column(name = "SETOR", length = 50)
	private String setor;

	@Column(name = "INATIVO")
	private Character inativo;

	@Column(name = "EM_FERIAS")
	private Character emFerias;

		

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SIS_USUARIO_EMPRESA", joinColumns = {
			@JoinColumn(name = "CODIGOSISUSUARIO") }, inverseJoinColumns = { @JoinColumn(name = "CODIGOEMPRESA") })
	private List<Empresa> empresas;

	// -------------------------------- GETs and SETs------------------------------//

	public Usuario() {
	}

	
	public Integer getIdUsusario() {
		return idUsusario;
	}

	public void setIdUsusario(Integer idUsusario) {
		this.idUsusario = idUsusario;
	}

	
	public String getNomeColaborador() {
		return nomeColaborador;
	}

	public void setNomeColaborador(String nomeColaborador) {
		this.nomeColaborador = nomeColaborador;
	}

	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}


	public List<Empresa> getEmpresas() {
		if (this.empresas == null) {
			this.empresas = new ArrayList<Empresa>();
		}
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	
	public String getCodigoEstrutural() {
		return codigoEstrutural;
	}

	public void setCodigoEstrutural(String codigoEstrutural) {
		this.codigoEstrutural = codigoEstrutural;
	}


	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}


	// M�todos Modificados
	public Boolean getInativo() {
		if (this.inativo == null)
			return null;

		return inativo.equals('S') ? true : false;
	}

	public void setInativo(Boolean value) {
		if (value == null) {
			this.inativo = null;
		} else {
			this.inativo = value == true ? 'S' : 'N';
		}
	}

	
	public Boolean getEmFerias() {
		if (this.emFerias == null)
			return null;

		return emFerias.equals('S') ? true : false;
	}

	public void setEmFerias(Boolean value) {
		if (value == null) {
			this.emFerias = null;
		} else {
			this.emFerias = value == true ? 'S' : 'N';
		}
	}

	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	public String getSenhaCriptografada() {
		return Criptografia.criptografarSha256(this.senha);
	}
	
	// -------------------------------- M�todos Auxiliares------------------------------//

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