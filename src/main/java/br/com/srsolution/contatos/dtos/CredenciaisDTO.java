package br.com.srsolution.contatos.dtos;

import java.io.Serializable;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CredenciaisDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;

	public CredenciaisDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken getToken() {
		return new UsernamePasswordAuthenticationToken(this.email, this.senha);
	}

}
