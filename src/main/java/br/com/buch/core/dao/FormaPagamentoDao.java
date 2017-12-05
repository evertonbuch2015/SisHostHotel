package br.com.buch.core.dao;

import br.com.buch.core.entity.FormaPagamento;

public class FormaPagamentoDao extends GenericDao<FormaPagamento> {

	public static final String CARREGAR_ENTIDADE  = "Select f From FormaPagamento f where f.idFormaPag = ?1";
	
	public static final String FILTRAR_POR_CODIGO =	"Select f From FormaPagamento f where f.codigo = ?1";
	
	public static final String FILTRAR_POR_NOME   = "Select f From FormaPagamento f where f.descricao like ?";
	
	
	public FormaPagamentoDao() {
		super(FormaPagamento.class);
	}

}
