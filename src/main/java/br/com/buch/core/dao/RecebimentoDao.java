package br.com.buch.core.dao;

import br.com.buch.core.entity.Recebimento;

public class RecebimentoDao extends GenericDao<Recebimento> {

	
	public static final String CARREGAR_ENTIDADE = 
			"From Recebimento r LEFT JOIN FETCH r.formaPagamento LEFT JOIN FETCH r.localRecebimento where r.idRecebimento = ?";
	
	public static final String BUSCAR_TODOS = 
			"From Recebimento r LEFT JOIN FETCH r.formaPagamento LEFT JOIN FETCH r.localRecebimento where r.dtEmissao Between ? and ? order by r.dtEmissao";

	public static final String FILTRAR_POR_CODIGO = 
			"Select r From Recebimento r LEFT JOIN FETCH r.localRecebimento where r.numero = ?";
	
	public static final String FILTRAR_POR_FORMA_PAGAMENTO = 
			"Select r From Recebimento r LEFT JOIN FETCH r.localRecebimento where r.formaPagamento = ?";
	
	public static final String FILTRAR_POR_FORMA_PAGAMENTO_NULL = 
			"Select r From Recebimento r LEFT JOIN FETCH r.localRecebimento where r.formaPagamento is null";
	
	public static final String FILTRAR_POR_BANCO = 
			"Select r From Recebimento r LEFT JOIN FETCH r.localRecebimento where r.localRecebimento = ?";
	
	public static final String FILTRO_POR_DATA_ENTRADA = 
			"Select r From Recebimento r LEFT JOIN FETCH r.localRecebimento where r.dtEmissao = ?";
	
	public static final String FILTRO_POR_DATA_ENTRADA_BEETWEN = 
			"Select r From Recebimento r LEFT JOIN FETCH r.localRecebimento where r.dtEmissao Between ? and ?";
	
	
	public RecebimentoDao() {
		super(Recebimento.class);
	}
}
