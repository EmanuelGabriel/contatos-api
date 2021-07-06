package br.com.srsolution.contatos.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String mensagem) {
		super(mensagem);
	}

	// Sobrecargo do Contrutor passando a causa de exception
	public DataIntegrityException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
