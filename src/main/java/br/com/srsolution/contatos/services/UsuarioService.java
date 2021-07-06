package br.com.srsolution.contatos.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.srsolution.contatos.models.Usuario;

public class UsuarioService {

	public static Usuario autenticado() {
		try {

			return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
