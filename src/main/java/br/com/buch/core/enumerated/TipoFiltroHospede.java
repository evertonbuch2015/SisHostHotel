package br.com.buch.core.enumerated;

public enum TipoFiltroHospede {
	
		CODIGO("Código"), 
		NOME("Nome"),
		CPF("CPF");
		
		private String label;

		TipoFiltroHospede(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	
}
