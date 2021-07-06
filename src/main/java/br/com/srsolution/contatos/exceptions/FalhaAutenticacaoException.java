package br.com.srsolution.contatos.exceptions;

public class FalhaAutenticacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FalhaAutenticacaoException(String mensagem) {
		super(mensagem);
	}

	// Sobrecarga do Contrutor passando uma mensagem e a causa de exception
	public FalhaAutenticacaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
