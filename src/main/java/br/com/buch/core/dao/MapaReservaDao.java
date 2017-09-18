package br.com.buch.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.buch.core.entity.MapaReserva;

public class MapaReservaDao {

	public MapaReservaDao() {
		
	}
	
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<MapaReserva> getMapaReserva(Date dtInicial, Date dtFinal){
		EntityManager em = getEntityManager();
    	       
		em.getTransaction().begin();
        String sql ="select * from GET_MAPA_RESERVA(:dtInicial, :dtFinal)";
        
        Query query = getEntityManager().createNativeQuery(sql).setHint("org.hibernate.readOnly", true);
        query.setParameter("dtInicial",dtInicial );
        query.setParameter("dtFinal",dtFinal );
        
        List<MapaReserva> entities = new ArrayList<>();
        try {
        	
        	List<Object[]> objetos = query.getResultList();            
        	
        	for (Object[] obj : objetos) {
        		MapaReserva mapaReserva = new MapaReserva();
        		mapaReserva.setNumeroApartamento(obj[0].toString());
        		mapaReserva.set_1((String) obj[1]);
        		mapaReserva.set_2((String) obj[2]);
        		mapaReserva.set_3((String) obj[3]);
        		mapaReserva.set_4((String) obj[4]);
        		mapaReserva.set_5((String) obj[5]);
        		mapaReserva.set_6((String) obj[6]);
        		mapaReserva.set_7((String) obj[7]);
        		mapaReserva.set_8((String) obj[8]);
        		mapaReserva.set_9((String) obj[9]);
        		mapaReserva.set_10((String) obj[10]);
        		mapaReserva.set_11((String) obj[11]);
        		mapaReserva.set_12((String) obj[12]);
        		mapaReserva.set_13((String) obj[13]);
        		mapaReserva.set_14((String) obj[14]);
        		mapaReserva.set_15((String) obj[15]);
        		mapaReserva.set_16((String) obj[16]);
        		mapaReserva.set_17((String) obj[17]);
        		mapaReserva.set_18((String) obj[18]);
        		mapaReserva.set_19((String) obj[19]);
        		mapaReserva.set_20((String) obj[20]);
        		mapaReserva.set_21((String) obj[21]);
        		mapaReserva.set_22((String) obj[22]);
        		mapaReserva.set_23((String) obj[23]);
        		mapaReserva.set_24((String) obj[24]);
        		mapaReserva.set_25((String) obj[25]);
        		mapaReserva.set_26((String) obj[26]);
        		mapaReserva.set_27((String) obj[27]);
        		mapaReserva.set_28((String) obj[28]);
        		mapaReserva.set_29((String) obj[29]);
        		mapaReserva.set_30((String) obj[30]);
        		mapaReserva.set_31((String) obj[31]);
        		
        		entities.add(mapaReserva);
        	}            
		} catch (Exception e) {			
			throw e;
		}finally{		
			em.getTransaction().commit();
			em.close();
		}
        
        return entities;
	}
	
	
   private EntityManager getEntityManager(){
	   return JPAUtil.GetInstance().getEntityManager();
   }
}
