package br.com.srsolution.contatos.exceptions;

public class LoginInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginInvalidoException(String mensagem) {
		super(mensagem);
	}

	// Sobrecarga do Contrutor passando a causa de exception
	public LoginInvalidoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
