package br.com.buch.core.enumerated;

public enum TipoLancamento {

	
	DIARIA("Diária"),
	ADIANTAMENTO("Adiantamento"),
	TAXA_SERVICO("Taxa de Serviço"),
	TAXA_TURISMO("Taxa de Turismo"),
	ESTORNO("Estorno"),
	DESCONTO("Desconto"),
	CONSUMO("Consumo");
	
	
	private String label;
	
	private TipoLancamento(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}
}
