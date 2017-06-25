package br.com.buch.core.enumerated;

public enum TipoFiltroHospedagem {
	
		CODIGO("Código"), 
		NOME_HOSPEDE("Nome"),
		CPF_HOSPEDE("CPF"),
		DATA_ENTRADA("Data Entrada"),
		NUMERO_APARTAMENTO("Nº Apartamento"),
		SITUACAO("Situação");
		
		private String label;

		TipoFiltroHospedagem(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	
}
