package br.com.buch.core.enumerated;

public enum SituacaoApartamento {

	LIVRE("Livre"),
	OCUPADO("Ocupado"),
	RESERVADO("Reservado"),
	EM_MANUTENCAO("Em Manutenção"),
	GOVERNANCA("Governaça");
	
	
	private String label;
	
	private SituacaoApartamento(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
	public static SituacaoApartamento getSituacaoApartamento(Integer ordinal){
		for (SituacaoApartamento situacaoApartamento : SituacaoApartamento.values()) {
			if(ordinal == situacaoApartamento.ordinal()){return situacaoApartamento;}
		}
		return null;
	}
}
