package br.com.buch.core.enumerated;

public enum TipoFiltroBanco {
	
		CODIGO("Código"), 
		NOME("Nome");
		
		private String label;

		TipoFiltroBanco(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	
}
