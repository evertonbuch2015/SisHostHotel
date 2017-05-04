package br.com.buch.core.dao;

import br.com.buch.core.entity.Empresa;

public class EmpresaDao extends GenericDao<Empresa> {

	public EmpresaDao() {
		super(Empresa.class);
	}

	
	@Override
	public Empresa findAllAttributesEntity(Integer id) {
		String jpql = "Select e From Empresa e where e.id = ?1";
		
		try {
			return findOne(jpql, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
		}
	}
}
