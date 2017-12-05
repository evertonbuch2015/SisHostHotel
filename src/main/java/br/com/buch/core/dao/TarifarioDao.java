package br.com.buch.core.dao;

import java.util.Date;

import javax.persistence.Query;

import br.com.buch.core.entity.Tarifario;

public class TarifarioDao extends GenericDao<Tarifario> {


	public static final String BUSCAR_TODOS = 
			" Select t From Tarifario t left Join FETCH t.categoria "+
			" left Join FETCH t.tipoTarifa order by t.dataInicial, t.categoria";
	
	public static final String CARREGAR_ENTIDADE = 
			"Select t From Tarifario t left Join FETCH t.categoria left Join FETCH t.tipoTarifa "
			+ " where t.idTarifario = ?1 order by t.categoria, t.tipoTarifa, t.dataInicial";
	
	public static final String FILTRAR_POR_CATEGORIA = 
			"Select t From Tarifario t LEFT JOIN FETCH t.categoria " +
			"LEFT JOIN FETCH t.tipoTarifa where t.categoria = ?";
	
	public static final String FILTRAR_POR_TIPO_TARIFA = 
			"Select t From Tarifario t LEFT JOIN FETCH t.categoria "+
			"LEFT JOIN FETCH t.tipoTarifa where t.tipoTarifa = ?";
	
	public static final String FILTRAR_POR_DATA = 
			"Select t From Tarifario t LEFT JOIN FETCH t.categoria "+
			"LEFT JOIN FETCH t.tipoTarifa where t.dataInicial >= ?1";
	
	public static final String FILTRO_POR_DATA_BEETWEN = 
			"Select t From Tarifario t LEFT JOIN FETCH t.categoria LEFT JOIN FETCH t.tipoTarifa "+
			" where t.dataInicial Between ? and ?";
	
	public static final String VALIDA_ANTES_SALVAR_01 = 
			" Select t From Tarifario t where t.categoria = ?1 and t.tipoTarifa = ?2" +
			" and ((t.dataInicial >= ?3 and t.dataInicial <= ?4) or (t.dataFinal >= ?3 and t.dataFinal <= ?4))";
	
	public static final String VALIDA_ANTES_SALVAR_02 = 
			" Select t From Tarifario t where t.categoria = ?1 and t.tipoTarifa = ?2 and t.idTarifario != ?3" +
			" and ((t.dataInicial >= ?4 and t.dataInicial <= ?5) or (t.dataFinal >= ?4 and t.dataFinal <= ?5))";
	
	
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
