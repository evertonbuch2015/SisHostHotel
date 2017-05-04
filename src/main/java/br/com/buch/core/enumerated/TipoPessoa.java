package br.com.buch.core.enumerated;

public enum TipoPessoa {
	F("Fisica"), J("Juridica"),
	C("CEI"), E("Exterior"),
	V("Vinculado");
	
	
	private String label;

	TipoPessoa(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}
}
