package br.com.buch.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
	
	private static final String CHAVE_HASH = "\"#)G1�G@BOG R2�x@PAF S1�s@AVD S3�wXPAF_-\"";
	
	public static String criptografarSha1(String texto) {
		String textoCriptografado = "";
		texto = texto.concat(CHAVE_HASH);
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			byte textoDigest[] = md.digest(texto.getBytes());

			StringBuilder hexString = new StringBuilder();

			for (byte b : textoDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}

			textoCriptografado = hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		return textoCriptografado;
	}

	
	public static String criptografarSha256(String texto) {
		String textoCriptografado = "";
		texto = texto.concat(CHAVE_HASH);
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte textoDigest[] = md.digest(texto.getBytes());

			StringBuilder hexString = new StringBuilder();

			for (byte b : textoDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}

			textoCriptografado = hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		return textoCriptografado;
	}
}
