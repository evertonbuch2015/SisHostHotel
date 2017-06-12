package br.com.buch.core.dao;

import java.util.Date;

import javax.persistence.Query;

import br.com.buch.core.entity.Apartamento;

public class ApartamentoDao extends GenericDao<Apartamento> {

	public ApartamentoDao() {
		super(Apartamento.class);
	}
	
	
	/**
	 * Verifica a Disponibilidade do Apartamento no intervalo de Data Fornecida.
	 * @param Id do Apartamento
	 * @param Data Entrada
	 * @param Data Saida
	 * @return Retorna 1 para Reservado e 0 para Disponivel.
	 * 
	 */
	public Integer verificaDisponibilidade(Integer id, Date dataEntrada,Date dataSaida){
        try{
            Query query2 = getEntityManager().createNativeQuery("select * from SP_DISPONIBILIDADE_AP(:id_apartamento, 0, :data_entrada, :data_saida)");
            query2.setParameter("id_apartamento", id);
            query2.setParameter("data_entrada",dataEntrada);
            query2.setParameter("data_saida", dataSaida);

            
            Integer i = (Integer) query2.getSingleResult();
            return i;
        }catch(Exception ex){
            
            return 0;        
        }
    }
}
