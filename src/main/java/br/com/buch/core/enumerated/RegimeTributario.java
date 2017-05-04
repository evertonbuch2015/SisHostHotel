package br.com.buch.core.enumerated;

public enum RegimeTributario {
	LUCRO_PRESUMIDO("Lucro Presumido"),
	LUCRO_REAL("Lucro Real"),
	SIMPLES_NACIONAL("Simples Nacional");
	
	
	private String label;

	RegimeTributario(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
}