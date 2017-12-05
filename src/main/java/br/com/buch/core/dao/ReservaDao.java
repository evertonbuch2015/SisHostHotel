package br.com.buch.core.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import br.com.buch.core.entity.Reserva;

public class ReservaDao extends GenericDao<Reserva> {


	public static final String BUSCAR_RESERVAS_VENCIDAS = 
			"Select r From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento"
			+ " where r.dataEntrada <= ?1 and r.situacao in (?2,?3) order by r.dataEntrada";

	public static final String FILTRO_POR_CODIGO = 
			"From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.codigo = ?";

	public static final String FILTRO_POR_SITUACAO = 
			"From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.situacao = ?";

	public static final String FILTRO_POR_NOME_HOSPEDE = 
			"From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where lower(r.hospede.nome) like ?";

	public static final String FILTRO_POR_CPF_HOSPEDE = 
			"From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.hospede.cpf = ?";
	
	public static final String FILTRO_POR_DATA_ENTRADA = 
			"From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.dataEntrada = ?";
			
	public static final String FILTRO_POR_DATA_ENTRADA_BEETWEN = 
			"From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.apartamento where r.dataEntrada Between ? and ?";
	
	public static final String BUSCAR_TODOS = 
			"From Reserva r where r.dataEntrada Between ?1 and ?2 order by r.dataEntrada";
	
	public static final String CARREGAR_ENTIDADE = 
			"Select r From Reserva r LEFT JOIN FETCH r.hospede LEFT JOIN FETCH r.empresa "+
			" LEFT JOIN FETCH r.apartamento where r.idReserva = ?1";
	
	
	public ReservaDao() {
		super(Reserva.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getReservasAgrupadoPorData(){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
	    //Dentro do mês Corrente
		c1.set(Calendar.DAY_OF_MONTH, c1.getMinimum(Calendar.DAY_OF_MONTH));
        c2.set(Calendar.DAY_OF_MONTH, c1.getMaximum(Calendar.DAY_OF_MONTH));
		
        try{
            Query query2 = getEntityManager().createNativeQuery(
            		" SELECT R.DATA_ENTRADA,COUNT(R.COD_RESERVA) FROM RESERVA R"+
            		" WHERE R.DATA_ENTRADA BETWEEN :DT1 AND :DT2 AND R.SITUACAO NOT IN ('CANCELADA','UTILIZADA')"+
            		" GROUP BY R.DATA_ENTRADA");

            query2.setParameter("DT1",c1.getTime());
            query2.setParameter("DT2",c2.getTime());
           

            return (List<Object[]>) query2.getResultList();
        }catch(Exception ex){
            return null;
        }
	}
}
