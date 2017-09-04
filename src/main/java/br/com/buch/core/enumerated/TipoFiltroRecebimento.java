package br.com.buch.core.enumerated;

public enum TipoFiltroRecebimento {
	CODIGO("Código"), 
	FORMA_PAGAMENTO("Forma de Pagamento"),
	LOCAL_RECEBIMENTO("Local de Recebimento"),
	DATA_EMISSAO("Data Entrada");
	

	private String label;

	TipoFiltroRecebimento(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}

}
