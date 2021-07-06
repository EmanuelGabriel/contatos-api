package br.com.srsolution.contatos.services.exceptions;

public class AutorizacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AutorizacaoException(String mensagem) {
		super(mensagem);
	}

	// Sobrecarga do Contrutor passando a causa de exception
	public AutorizacaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
