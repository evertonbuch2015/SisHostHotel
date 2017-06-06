package br.com.buch.core.enumerated;

public enum SituacaoHospedagem {

	CONFIRMADA("Confirmada"),
	NAO_CONFIRMADA("Não Confirmada"),
	CHECKIN("Check-In"),
	CHECKOUT("Check-Out"),
	CANCELADA("Cancelada");
	
	
	private String label;
	
	private SituacaoHospedagem(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
}
