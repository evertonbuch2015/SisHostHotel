package br.com.buch.core.dao;

import br.com.buch.core.entity.TipoTarifa;


public class TipoTarifaDao extends GenericDao<TipoTarifa> {
	
	public static final String CARREGAR_ENTIDADE  = "Select t From TipoTarifa t where t.idTipoTarifa = ?1";
	
	public static final String FILTRAR_POR_CODIGO = "Select t From TipoTarifa t where t.idTipoTarifa = ?1";
	
	public static final String FILTRAR_POR_NOME   = "Select t From TipoTarifa t where t.nome like ?"; 
	
	
	public TipoTarifaDao() {
		super(TipoTarifa.class);
	}

}
