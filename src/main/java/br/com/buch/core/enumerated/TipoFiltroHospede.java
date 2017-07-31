package br.com.buch.core.enumerated;

public enum TipoFiltroHospede {
		
		NOME("Nome"),
		CODIGO("Código"),
		CPF("CPF");
		
		TipoFiltroHospede(String label) {this.label = label;}
		
		private String label;
		
		public String getLabel(){return this.label;}
}
