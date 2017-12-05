package br.com.buch.core.dao;

import br.com.buch.core.entity.Hospede;

public class HospedeDao extends GenericDao<Hospede> {

	public static final String BUSCAR_TODAS = 
			"Select h From Hospede h LEFT JOIN FETCH h.endereco"; 
			
	public static final String CARREGAR_ENTIDADE =
			"Select h From Hospede h LEFT JOIN FETCH h.endereco LEFT JOIN FETCH h.empresa where h.idHospede = ?1"; 
	
	public static final String FILTRAR_POR_CODIGO = "Select h From Hospede h where h.codigo = ?";
	
	public static final String FILTRAR_POR_NOME   = "Select h From Hospede h where h.nome like ?";
	
	public static final String FILTRAR_POR_CPF    = "Select h From Hospede h where h.cpf = ?";
	
	public static final String BUSCAR_POR_NOME    = "Select h From Hospede h where lower(h.nome) like lower(?)";
	
	public static final String BUSCAR_POR_EMPRESA = "Select h From Hospede h where h.empresa = ?";
	
	
	public HospedeDao() {
		super(Hospede.class);
	}

}
