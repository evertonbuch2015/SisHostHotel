package br.com.buch.core.enumerated;

public enum TipoFiltroAdiantamento {

	CODIGO("Código"), 
	HOSPEDE_CODIGO("Código Hóspede"),
	HOSPEDE_NOME("Nome Hóspede"),
	HOSPEDE_CPF("CPF Hóspede"),	
	DATA_EMISSAO("Data Emissão");

	private String label;

	TipoFiltroAdiantamento(String label) {
			this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
