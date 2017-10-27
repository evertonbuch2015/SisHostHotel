import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class testes {
	public static void main(String[] args) {
		//testeEmail();
		//testeFile(new File("F:\\"));
		//testeFile(new File("F:\\Programação em Java\\1. Videos Diversos"));
		//metodos("F:\\Programação em Java\\1. Videos Diversos");
	}
	
	public static void metodos(String caminho)
    {
        File arquivo = new File(caminho);
     
        if( arquivo.exists() )
        {
            System.out.println("O caminho especificado existe !\nVamos aos testes:\n");
         
            if(arquivo.isAbsolute())
                System.out.println("É um caminho absoluto");
            else
                System.out.println("Não é um caminho absoluto");
         
            if(arquivo.isFile())
                System.out.printf("É um arquivo de tamanho %s bytes\n"
                        + "Útima vez modificado %s\n"
                        + "Cujo caminho é %s\n"
                        + "De caminho absoluto %s\n"
                        + "E está no diretório pai %s\n",
                        arquivo.length(), arquivo.lastModified(), arquivo.getPath(), arquivo.getAbsolutePath(), arquivo.getParent() );
         
            else
            {
                 System.out.println("É um diretório cujo conteúdo tem os seguintes arquivos: ");
                 String[] arquivos = arquivo.list();
             
                 for( String file : arquivos)
                   System.out.println( file );
            }
         
        }
        else
                 System.out.println("Endereço errado");
    }
	
	private static void testeFile(File directory) {		
		if(directory.isDirectory()) {
            System.out.println(directory.getPath());
            String[] subDirectory = directory.list();
            if(subDirectory != null) {
                for(String dir : subDirectory){
                	testeFile(new File(directory + File.separator  + dir));
                }
            }
        }else{
        	System.out.println("Name :"+directory.getName());
        	//System.out.println("Absolute Path" +directory.getAbsolutePath());
        	System.out.printf("É um arquivo de tamanho %s bytes\n"
                    + "Útima vez modificado %s\n"
                    + "Cujo caminho é %s\n"
                    + "De caminho absoluto %s\n"
                    + "E está no diretório pai %s\n",
                    directory.length(), directory.lastModified(), directory.getPath(), directory.getAbsolutePath(), directory.getParent() );
        	
        	System.out.println("___________________________________________"); 
        	
        }
	}

	private static void testeEmail() {
		
		Calendar c1a = Calendar.getInstance();
		c1a.set(Calendar.DAY_OF_MONTH, 1);
		c1a.set(Calendar.MONTH, 4);
		c1a.set(Calendar.YEAR, 2017);
		
		System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(c1a.getTime()));
		
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.MONTH, 4+1);
		ca.set(Calendar.YEAR, 2017);
		
		ca.add(Calendar.DAY_OF_MONTH, -1);
		
		System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(ca.getTime()));
		
		
		
		
		//new MapaReservaDao().getMapaReserva(ca.getTime(), new Date());
		
		/*SendEmail email = new SendEmail("smtp.gmail.com", true, "everton.buch@gmail.com", "ev019966");
		email.setAssunto("Teste");
		email.setDestinatarios("everton.buch@gmail.com");
		email.setRemetente("everton.buch@gmail.com");
		email.setMensagem("teste");
		email.adicionarAnexo(new File("C:\\Users\\Everton Buchkorn\\Dropbox\\Faculdade\\Disciplinas.xlsx"));
		
		try {
			email.enviaEmailAnexo();
		} catch (SendEmailException e) {
			e.printStackTrace();
		}*/
	}
}
