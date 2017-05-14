package br.com.buch.core.dao;

import br.com.buch.core.entity.Hotel;

public class HotelDao extends GenericDao<Hotel> {

	public HotelDao() {
		super(Hotel.class);
	}

	
	@Override
	public Hotel findAllAttributesEntity(Integer id) {
		String jpql = "Select e From Hotel e where e.id = ?1";
		
		try {
			return findOne(jpql, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
		}
	}
}
