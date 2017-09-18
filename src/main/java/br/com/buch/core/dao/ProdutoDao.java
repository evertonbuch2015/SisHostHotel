package br.com.buch.core.dao;

import br.com.buch.core.entity.Produto;

public class ProdutoDao extends GenericDao<Produto> {

	public ProdutoDao() {
		super(Produto.class);
	}

}
