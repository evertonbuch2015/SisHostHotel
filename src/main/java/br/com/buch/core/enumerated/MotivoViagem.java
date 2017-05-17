package br.com.buch.core.enumerated;

public enum MotivoViagem {
	LAZER("Lazer/Leisure"),
	NEGOCIOS("Negócios/Business"),
	CONGRESSO("Congresso/Congress"),
	PARENTES("Parentes/Kindred"),
	ESTUDO("Estudo/Study"),
	RELIGIAO("Religião/Religion"),
	SAUDE("Saúde/Health"),
	COMPRAS("Compras/Shopping"),
	OUTROS("Outros/Other");
	
	
	private String label;
	
	private MotivoViagem(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
}
