package br.com.buch.core.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import br.com.buch.core.dao.ReservaDao;
import br.com.buch.core.entity.Apartamento;

public class ServiceIndex {
	
	
	public ServiceIndex() {
		 
	}
	
	
	public Map<Object,Number> getGraficoReservas(){
		Map<Object,Number> data = new HashedMap<Object,Number>();
		List<Object[]> lista = new ReservaDao().getReservasAgrupadoPorData();
				
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (Object[] object : lista) {
			data.put(sdf.format((Date)object[0]), (Integer)object[1]);
		}
		return data;
	}
	
	
	public Map<Object,Number> getGraficoApartamentos(){
		
		Map<Object,Number> data = new HashedMap<Object,Number>();
		List<Apartamento> lista = new ArrayList<Apartamento>();
		Integer livre = 0, ocupado = 0, reservado = 0, manutencao = 0, governaca = 0;
		
		if(lista.isEmpty()){
			
			try {
				lista = new ServiceApartamento().buscarTodos();
			} catch (Exception e) {
				e.printStackTrace();
				lista = new ArrayList<Apartamento>(); 				
			}
		}
		
		for (Apartamento apartamento : lista) {
			
			switch (apartamento.getSituacao().ordinal()) {
				case 0:
					livre += 1;
					break;
				case 1:
					ocupado += 1;
					break;
				case 2:
					reservado += 1;
					break;							
				case 3:
					manutencao += 1;
					break;					
				case 4:
					governaca += 1;
					break;
			}
						 
		}
		
		
		data.put("Livres", livre);
		data.put("Ocupados", ocupado);
        data.put("Reservados", reservado);
        data.put("Em Manutenção", manutencao);
        data.put("Governaça", governaca);
		
		return data;
	}
	
}
