package br.com.buch.core.dao;

import br.com.buch.core.entity.Adiantamento;

public class AdiantamentoDao extends GenericDao<Adiantamento> {

	public static final String CARREGAR_ENTIDADE = 
			"Select a From Adiantamento a LEFT JOIN FETCH a.hospede LEFT JOIN FETCH a.localRecebimento where a.idAdiantamento = ?";
		
	public static final String BUSCAR_TODOS = 
			"Select a From Adiantamento a LEFT JOIN FETCH a.hospede order by a.dtEmissao";
	
	public static final String FILTRAR_POR_CODIGO = 
			"Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.codigo = ?";
	
	public static final String FILTRAR_POR_BANCO = 
			"Select r From Recebimento r LEFT JOIN FETCH r.localRecebimento where r.localRecebimento = ?";

	public static final String FILTRAR_POR_NOME_HOSPEDE = 
			"Select a From Adiantamento a LEFT JOIN FETCH a.hospede where lower(a.hospede.nome) like ?";
	
	public static final String FILTRAR_POR_CPF_HOSPEDE = 
			"Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.hospede.cpf = ?";
	
	public static final String FILTRO_POR_DATA_ENTRADA = 
			"Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.dtEmissao = ?";
	
	public static final String FILTRO_POR_DATA_ENTRADA_BEETWEN = 
			"Select a From Adiantamento a LEFT JOIN FETCH a.hospede where a.dtEmissao Between ? and ?";
	
	
	public AdiantamentoDao() {
		super(Adiantamento.class);
	}

}
