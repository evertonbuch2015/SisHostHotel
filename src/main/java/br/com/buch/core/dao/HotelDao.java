package br.com.buch.core.dao;

import br.com.buch.core.entity.Hotel;

public class HotelDao extends GenericDao<Hotel> {

	public static final String BUSCAR_TODAS       = "Select e From Hotel e order by e.nomeFantasia";
	
	public static final String CARREGAR_ENTIDADE  = "Select e From Hotel e left JOIN FETCH e.endereco where e.id = ?1";
	
	public static final String FILTRAR_POR_CODIGO = "Select e From Hotel e where e.codigo = ?1";
	
	public static final String FILTRAR_POR_NOME   = "Select e From Hotel e where e.nomeRazao like ?"; 
	
	
	public HotelDao() {
		super(Hotel.class);
	}


}
