package br.com.buch.core.enumerated;

import java.util.ArrayList;
import java.util.List;

public enum Estados {

	AC, AL, AM, AP, BA, CE, DF, ES, GO, MA, MG, MS, MT,
	PA, PB, PE, PI, PR, RJ, RN, RO, RR, RS, SC, SE, SP, TO;

	private List<Estados> estados;

	public List<Estados> getEstados() {
		if (this.estados == null) {
			this.estados = new ArrayList<Estados>();
		}
		estados.clear();
		
		for (Estados uf : Estados.values()) {
			this.estados.add(uf);
		}

		return this.estados;
	}
}
