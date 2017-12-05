package br.com.buch.core.dao;

import br.com.buch.core.entity.Empresa;

public class EmpresaDao extends GenericDao<Empresa> {
	
	public static final String CARREGAR_ENTIDADE =
			"Select e From Empresa e left JOIN FETCH e.endereco where e.idEmpresa = ?1";
	
	public static final String BUSCAR_POR_NOME_FANTASIA = 
			"Select e From Empresa e where e.nomeFantasia Like ? order by e.nomeFantasia";

	public static final String BUSCAR_TODAS = "Select e From Empresa e order by e.nomeFantasia";
	
	public static final String FILTRAR_POR_NOME = "Select e From Empresa e where e.nomeRazao like ?";
	
	public static final String FILTRAR_POR_CODIGO = "Select e From Empresa e where e.codigo = ?";
		
	public static final String BUSCAR_POR_NOME    = "Select e From Empresa e where lower(e.nomeFantasia) like lower(?)";
	
	
	public EmpresaDao() {
		super(Empresa.class);
	}


}
