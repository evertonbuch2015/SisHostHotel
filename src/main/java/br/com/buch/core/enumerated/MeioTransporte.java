package br.com.buch.core.enumerated;

public enum MeioTransporte {

	AVIAO("Avião/Airplane"),
	AUTOMOVEL("Automóvel/Vehicle"),
	ONIBUS("Ônibus/Bus"),
	MOTO("Moto/Motorcycle"),
	NAVIO("Navio/Ship"),
	TREM("Trem/Train"),
	OUTRO("Outro/Other");
	
	
	private String label;
	
	private MeioTransporte(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
}
