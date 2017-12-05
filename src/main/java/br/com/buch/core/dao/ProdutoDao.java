package br.com.buch.core.dao;

import br.com.buch.core.entity.Produto;

public class ProdutoDao extends GenericDao<Produto> {

	public static final String CARREGAR_ENTIDADE = "Select p From Produto p where p.idProduto = ?1";
	
	public static final String BUSCAR_POR_NOME   = "Select p From Produto p where lower(p.nome) like ?";
	
	public static final String BUSCAR_POR_CODIGO = "Select p From Produto p where p.codigo like ?";;
	
	
	public ProdutoDao() {
		super(Produto.class);
	}

}
