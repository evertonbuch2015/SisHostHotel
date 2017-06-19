import java.io.File;

import br.com.buch.core.util.SendEmail;
import br.com.buch.core.util.SendEmailException;

public class testes {
	public static void main(String[] args) {
		testeEmail();
	}
	
	private static void testeEmail() {
		SendEmail email = new SendEmail("smtp.gmail.com", true, "everton.buch@gmail.com", "ev019966");
		email.setAssunto("Teste");
		email.setDestinatarios("everton.buch@gmail.com");
		email.setRemetente("everton.buch@gmail.com");
		email.setMensagem("teste");
		email.adicionarAnexo(new File("C:\\Users\\Everton Buchkorn\\Dropbox\\Faculdade\\Disciplinas.xlsx"));
		
		try {
			email.enviaEmailAnexo();
		} catch (SendEmailException e) {
			e.printStackTrace();
		}
	}
}
