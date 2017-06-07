package br.com.buch.core.enumerated;

public enum TipoFiltroCaixaBanco {
	
		CODIGO("Código"), 
		NOME("Nome");
		
		private String label;

		TipoFiltroCaixaBanco(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	
}
