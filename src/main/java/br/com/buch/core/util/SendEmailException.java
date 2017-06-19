package br.com.buch.core.util;

public class SendEmailException extends Exception {

	private static final long serialVersionUID = -6096328985639522833L;

	public SendEmailException() {
	}

	public SendEmailException(String message) {
		super(message);
	}

	public SendEmailException(Throwable cause) {
		super(cause);
	}

	public SendEmailException(String message, Throwable cause) {
		super(message, cause);
	}
}
