package br.com.buch.core.dao;

import br.com.buch.core.entity.Categoria;

public class CategoriaDao extends GenericDao<Categoria> {

	public static final String BUSCAR_POR_NOME = "Select c From Categoria c where c.nome like ?";
	
	public static final String CARREGAR_ENTIDADE = "Select c From Categoria c where c.idCategoria = ?1";
	
	
	public CategoriaDao() {
		super(Categoria.class);
	}

}
