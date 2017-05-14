package br.com.buch.view.util;

public class UtilErros {

	//procura a excessão de nivel mais baixo. 
	public static String getMensagemErro(Exception ex){
		while(ex.getCause() != null){
			ex = (Exception) ex.getCause();
		}
		
		String retorno = ex.getMessage();
		return retorno;
	}
}

