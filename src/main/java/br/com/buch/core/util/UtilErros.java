package br.com.buch.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilErros {

	private static final String ERRO_FK = "violation of FOREIGN KEY";
	
	
	//procura a excessão de nivel mais baixo. 
	public static String getMensagemErro(Exception ex){
		while(ex.getCause() != null){
			ex = (Exception) ex.getCause();
		}
		
		String retorno = ex.getMessage();
		
		if(retorno.contains(ERRO_FK)){
			retorno = getTabelaExcecaoFK(retorno);
		}		
		return retorno;
	}	
	
	
	private static String getTabelaExcecaoFK(String erro){
		String retorno = "";
		
		if(erro.contains("on table "+"\""+"CAD_APARTAMENTO"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Apartamentos!";
		}else if(erro.contains("on table "+"\""+"ADIANTAMENTO_CLIENTE"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Adiantamento de Clientes!";
		}else if(erro.contains("on table "+"\""+"CAD_HOSPEDE"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Hóspedes!";
		}else if(erro.contains("on table "+"\""+"CAD_TARIFARIO"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Tarifas!";
		}else if(erro.contains("on table "+"\""+"CONSUMO"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Consumo!";
		}else if(erro.contains("on table "+"\""+"HOSPEDAGEM"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Hospedagens!";
		}else if(erro.contains("on table "+"\""+"RECEBIMENTO"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Recebimentos!";
		}else if(erro.contains("on table "+"\""+"RESERVA"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Reservas!";
		}else if(erro.contains("on table "+"\""+"SIS_USUARIO_HOTEL"+"\"")){
			retorno = "Registro a ser excluido possui ligação com a tabela de Usuários!";
		}
		return retorno;
	}


	public static void gerarLogErro(String mensagem, Exception ex){		
		
		try {
			File arquivo = new File("c:\\LogErros.txt");
			if(!arquivo.exists()){				
				arquivo.createNewFile();	//Cria o arquivo
			}
						
			FileWriter grava = new FileWriter(arquivo, true);
            PrintWriter writer = new PrintWriter(grava);            
            writer.println("Data: "+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) );
            writer.println("mensagem: " + mensagem);
            writer.println("Erro: " + ex.getMessage());
            writer.println("_________________________________________________________________________________");
                        
            writer.close();
            grava.close();
        } catch (IOException e) {
            
        }
	}
}

