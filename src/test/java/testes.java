import java.text.SimpleDateFormat;
import java.util.Calendar;

public class testes {
	public static void main(String[] args) {
		testeEmail();
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
