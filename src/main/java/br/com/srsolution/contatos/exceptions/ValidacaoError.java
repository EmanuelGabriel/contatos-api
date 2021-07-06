package br.com.srsolution.contatos.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> erros = new ArrayList<>();

	/* Construtor com args(argumentos) */
	public ValidacaoError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addError(String fieldName, String msg) {
		erros.add(new FieldMessage(fieldName, msg));
	}

}
