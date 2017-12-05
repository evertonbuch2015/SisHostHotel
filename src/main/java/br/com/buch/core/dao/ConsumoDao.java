package br.com.buch.core.dao;

import br.com.buch.core.entity.Consumo;

public class ConsumoDao extends GenericDao<Consumo> {

	public static final String BUSCAR_TODAS = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.dataConsumo Between ? and ? order by c.dataConsumo";

	public static final String BUSCAR_POR_HOSPEDAGEM = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.hospedagem.codigo = ?";

	public static final String CARREGAR_ENTIDADE = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.idConsumo = ?1";
	
	public static final String FILTRO_POR_DATA = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.dataConsumo = ?";
	
	public static final String FILTRO_POR_DATA_BEETWEN = 
			"Select c From Consumo c LEFT JOIN FETCH c.produto LEFT JOIN FETCH c.hospedagem where c.dataConsumo Between ? and ?";
	
	
	public ConsumoDao() {
		super(Consumo.class);
	}

}
