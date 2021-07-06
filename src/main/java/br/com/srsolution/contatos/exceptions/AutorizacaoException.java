package br.com.srsolution.contatos.exceptions;

public class AutorizacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AutorizacaoException(String mensagem) {
		super(mensagem);
	}

	// Sobrecarga do Contrutor passando uma mensagem e a causa de exception
	public AutorizacaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
