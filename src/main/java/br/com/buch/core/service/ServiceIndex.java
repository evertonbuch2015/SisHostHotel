package br.com.buch.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import br.com.buch.core.entity.Apartamento;
import br.com.buch.core.util.PersistenciaException;

public class ServiceIndex {
	
	
	public ServiceIndex() {
		 
	}
	
	
	public Map<String,Number> getGraficoApartamentos(){
		
		Map<String,Number> data = new HashedMap<String,Number>();
		List<Apartamento> lista = new ArrayList<Apartamento>();
		Integer livre = 0, ocupado = 0, reservado = 0, manutencao = 0, governaca = 0;
		
		if(lista.isEmpty()){
			
			try {
				lista = new ServiceApartamento().buscarTodos();
			} catch (PersistenciaException e) {
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
