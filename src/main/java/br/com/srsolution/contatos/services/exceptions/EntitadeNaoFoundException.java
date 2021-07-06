package br.com.srsolution.contatos.services.exceptions;

public class EntitadeNaoFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntitadeNaoFoundException(String mensagem) {
		super(mensagem);
	}

	// Sobrecarga do Contrutor passando a causa de exception
	public EntitadeNaoFoundException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
