package br.com.srsolution.contatos.services.exceptions;

public class ErrorHttp400 extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErrorHttp400(String mensagem) {
		super(mensagem);
	}

	// Sobrecarga do Contrutor passando a causa de exception
	public ErrorHttp400(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
