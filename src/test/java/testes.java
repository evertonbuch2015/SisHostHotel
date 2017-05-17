public class testes {
	public static void main(String[] args) {
		teste();
	}
	
	private static void teste(){
		String cpf = "01161224041";
		String cpf2 = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9)
						+ "-" + cpf.substring(9);
		System.out.println(cpf2);
	}
}
