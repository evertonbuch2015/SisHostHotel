package br.com.buch.core.enumerated;

public enum RamoAtividade {
	INDUSTRIA("Ind�stria"), TRANSPORTE("Transporte"),
	COMERCIO("Com�rcio"), GOVERNO("Governo"),
	TURISMO("Turismo"), HOSPITAL("Hospital"),
	ESCOLA("Escola"), LAVANDERIA("Lavanderia"); 
	
	
	private String label;

	RamoAtividade(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
}
