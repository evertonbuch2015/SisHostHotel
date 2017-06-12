package br.com.buch.core.dao;

import java.util.Date;

import javax.persistence.Query;

import br.com.buch.core.entity.Tarifario;

public class TarifarioDao extends GenericDao<Tarifario> {

	public TarifarioDao() {
		super(Tarifario.class);
	}

	
	public Tarifario findByCategoriaTipoTarifa(Integer categoria, Integer tipoTarifa, Date data ) throws Exception{
        Query query = getEntityManager().createNativeQuery("select * from GET_TARIFARIO(:categoria, :tipo_tarifa, :data)");
        query.setParameter("categoria", categoria);
        query.setParameter("tipo_tarifa", tipoTarifa);
        query.setParameter("data", data);

        Integer codigo =  (Integer) query.getSingleResult();
        
        Tarifario tarifario = null;
        if(codigo != null){
        	tarifario = findById(codigo);
        }
        
        return tarifario;
	}
}
