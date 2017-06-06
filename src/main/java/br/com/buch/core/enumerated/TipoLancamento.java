package br.com.buch.core.enumerated;

public enum TipoLancamento {

	
	DIARIA("Diária"),	
	TAXA_SERVICO("Taxa de Serviço"),
	TAXA_TURISMO("Taxa de Turismo"),
	CONSUMO("Consumo"),
	ESTORNO("Estorno"),
	ADIANTAMENTO("Adiantamento"),
	DESCONTO("Desconto");
	
	
	private String label;
	
	private TipoLancamento(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}

	
	public Character getOperacao(TipoLancamento tipoLancamento){
		switch (tipoLancamento) {
		case DIARIA:
			return 'D';
		case TAXA_SERVICO:
			return 'D';
		case TAXA_TURISMO:
			return 'D';
		case CONSUMO:
			return 'D';
		case ESTORNO:
			return 'C';
		case ADIANTAMENTO:
			return 'C';
		case DESCONTO:
			return 'C';
		default:
			return null;
		}
	}
}
