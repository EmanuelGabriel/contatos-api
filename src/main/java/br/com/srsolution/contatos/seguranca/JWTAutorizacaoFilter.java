package br.com.srsolution.contatos.seguranca;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAutorizacaoFilter extends BasicAuthenticationFilter {

	@Autowired
	private JWTUtilitario jwtUtilitario;

	@Autowired
	private UserDetailsService userDetailsService;

	public JWTAutorizacaoFilter(AuthenticationManager authenticationManager, JWTUtilitario jwtUtilitario,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtilitario = jwtUtilitario;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		// caso contrário, segue o fluxo normal
		chain.doFilter(request, response);
	}

	/*
	 * Verifica se o Token é válido ou inválido
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		if (jwtUtilitario.tokenValido(token)) {
			String usuario = jwtUtilitario.getUsuario(token); // getUsername(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(usuario);
			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
		}else {
			jwtUtilitario.validadeToken(token);
		}
		
		return null;
	}

}
