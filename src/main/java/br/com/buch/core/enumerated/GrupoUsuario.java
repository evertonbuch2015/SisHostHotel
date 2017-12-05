package br.com.buch.core.enumerated;

public enum GrupoUsuario {
	ADMINISTRADOR,
	RECEPCAO,
	GERENTE;
	
	
	public static Boolean isAdministrador(GrupoUsuario grupo){
		return grupo.equals(ADMINISTRADOR);
	}
	
	public static Boolean isGerente(GrupoUsuario grupo){
		return grupo.equals(GERENTE);
	}
	
	public static Boolean isRecepcionista(GrupoUsuario grupo){
		return grupo.equals(RECEPCAO);
	}
}
