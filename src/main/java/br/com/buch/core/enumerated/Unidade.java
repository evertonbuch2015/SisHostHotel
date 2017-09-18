package br.com.buch.core.enumerated;

public enum Unidade {
	//AMPOLA("Ampola"),
	BALDE("Balde"),
	BANDEJ("Bandeja"),
	BARRA("Barra"),
	//BISNAG("Bisnaga"),
	//BLOCO("Bloco"),
	//BOBINA("Bobina"),
	BOMB("Bombona"),
	//CAPS("Capsula"),
	//CART("Cartela"),
	//CENTO("Cento"),
	CJ("Conjunto"),
	CM("Centimetro"),
	CM2("Centimetro Quadrado"),
	CX("Caixa"),
	CX2("Caixa c/ 2"),
	CX3("Caixa c/ 3"),
	CX5("Caixa c/ 5"),
	CX10("Caixa c/ 10"),
	CX15("Caixa c/ 15"),
	CX20("Caixa c/ 20"),
	CX25("Caixa c/ 25"),
	CX50("Caixa c/ 50"),
	CX100("Caixa c/ 100"),
	//DISP("Display"),
	DUZIA("Duzia"),
	EMBAL("Embalagem"),
	FARDO("Fardo"),
//	FOLHA("Folha"),
	FRASCO("Frasco"),
	GALAO("Galão"),
	GF("Garrafa"),
	GRAMAS("Gramas"),
	JOGO("Jogo"),
	KG("Kilograma"),
	KIT("Kit"),
	LATA("Lata"),
	LITRO("Litro"),
	M("Metro"),
	M2("Metro Quadrado"),
	M3("Metro Cúbico"),
	MILHEI("Milheiro"),
	ML("Mililitro"),
	PACOTE("Pacote"),
	PARES("Pares"),
	PC("Peça"),
	POTE("Pote"),
	ROLO("Rolo"),
	SACO("Saco"),
	SACOLA("Sacola"),
	TAMBOR("Tambor"),
	//TON("Tonelada"),
	TUBO("Tubo"),
	UNID("Unidade"),
	VASIL("Vasilhame");
	
	
	private String label;

	Unidade(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
}