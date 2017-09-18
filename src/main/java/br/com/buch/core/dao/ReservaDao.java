package br.com.buch.core.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import br.com.buch.core.entity.Reserva;

public class ReservaDao extends GenericDao<Reserva> {

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
