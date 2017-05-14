import br.com.buch.core.entity.Usuario;
import br.com.buch.core.service.ServiceHotel;
import br.com.buch.core.service.ServiceUsuario;

public class testes {
	public static void main(String[] args) {
		teste();
	}
	
	private static void teste(){
		ServiceHotel serviceHotel = new ServiceHotel();
		ServiceUsuario serviceUsuario = new ServiceUsuario();
		
		Usuario usuario = serviceUsuario.carregarEntidade(6);
		
		/*Hotel entidate = new Hotel();
		entidate.setCodigo("21");
		entidate.setFilial("11");
		entidate.setNomeRazao("Host Hotel");
		entidate.setNomeFantasia("Host Hotel");
		entidate.setDataCadastro(new Date());
		entidate.setDocumento("11.711.483/0001-03");
		entidate.setEmail("hosthotel@hosthotel.com.br");
		
		
		
		//serviceHotel.salvar(entidate);
		
		
		
		Usuario usuario = new Usuario();
		usuario.setNomeUsuario("EVERTON");
		usuario.setSenha("00");
		usuario.setNomeColaborador("Everton Buchkorn de Souza");
		usuario.setEmFerias(false);
		usuario.setGrupo("ADMIN");
		usuario.adicionarHotel(serviceHotel.carregarEntidade(2));
		usuario.setSetor("TI");
		
		serviceUsuario.salvar(usuario);*/
	}
}
