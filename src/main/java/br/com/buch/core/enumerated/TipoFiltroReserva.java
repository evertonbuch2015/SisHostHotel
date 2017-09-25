package br.com.buch.core.enumerated;

public enum TipoFiltroReserva {
	
		CODIGO("Código"), 
		NOME_HOSPEDE("Nome"),
		CPF_HOSPEDE("CPF"),
		DATA_ENTRADA("Data Entrada"),
		SITUACAO("Situação");
		
		private String label;

		TipoFiltroReserva(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	
}
