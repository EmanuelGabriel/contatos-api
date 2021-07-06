package br.com.srsolution.contatos.seguranca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.srsolution.contatos.dtos.CredenciaisDTO;
import br.com.srsolution.contatos.exceptions.FalhaAutenticacaoException;
import br.com.srsolution.contatos.models.Usuario;

/**
 * 
 * @author emanuel.sousa
 *
 */
public class JWTAutenticacaoFilter extends UsernamePasswordAuthenticationFilter{

	
	private AuthenticationManager authenticationManager;
	
	private JWTUtilitario jwtUtilitario;
	
	// construtor da classe
	public JWTAutenticacaoFilter(AuthenticationManager authenticationManager, JWTUtilitario jwtUtilitario) {
		super.setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtilitario = jwtUtilitario;
	}
	
	
	/*
	 * Método de Autenticação com o Token - buscando as credenciais do Usuário na Base de Dados
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
		
		CredenciaisDTO credenciamento = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);	
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
		credenciamento.getEmail(), credenciamento.getSenha(), new ArrayList<>());	
		Authentication auth = this.authenticationManager.authenticate(authToken);
        return auth;
        
		} catch (FalhaAutenticacaoException | IOException e) {
			throw new FalhaAutenticacaoException("Nenhum conteúdo para mapear devido a não definição do corpo(body): " + e.getMessage());
		}
	}
	
	/*
	 * Sobrescrevendo o método de autenticação com sucesso e gerando o TOKEN
	 */
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			javax.servlet.FilterChain chain, Authentication auth) throws IOException, ServletException {

		String usuarioPorEmail = ((Usuario) auth.getPrincipal()).getUsername(); // autenticação realizada por e-mail
		String token = jwtUtilitario.gerarToken(usuarioPorEmail);
		response.addHeader("Authorization", "Bearer " + token);
		response.setContentType("application/json"); 
		response.addHeader("access-control-expose-headers", "Authorization");
		
		// escreve token como resposta no corpo http
		response.getWriter().write("{\"Token\": \"" + token + "\"}");
		
	};
	
	
	 private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
	        @Override
	        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
	                throws IOException, ServletException {
	            response.setStatus(401);
	            response.setContentType("application/json"); 
	            response.getWriter().append(json());
	        }
	        
	        private String json() {
	            long date = new Date().getTime();
	            return "{\"timestamp\": " + date + ", "
	                + "\"status\": 401, "
	                + "\"error\": \"Não autorizado\", "
	                + "\"message\": \"Email e/ou senha inválidos\", "
	                + "\"path\": \"/login\"}";
	        }
	    }
	
	
}
