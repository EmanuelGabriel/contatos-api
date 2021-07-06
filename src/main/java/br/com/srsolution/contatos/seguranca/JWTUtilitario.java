package br.com.srsolution.contatos.seguranca;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.srsolution.contatos.exceptions.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JWTUtilitario {

	@Value("${jwt.secreta}")
	private String secreta;

	@Value("${jwt.expiracao}")
	private long expiracao;

	/*
	 * Método responsável em Gerar o Token
	 */
	public String gerarToken(String usuario) {
		return Jwts.builder().setSubject(usuario).setExpiration(new Date(System.currentTimeMillis() + expiracao))
				.signWith(SignatureAlgorithm.HS512, secreta.getBytes()).compact();

	}

	/*
	 * Sobrecarga de método responsável em Gerar o Token
	 */
	public String gerarToken(Authentication authentication) {
		UserDetails userLogado = (UserDetails) authentication.getPrincipal();
		Date dataAtual = new Date();
		Date dataExpiracao = getDataExpiracao();

		return Jwts.builder().setIssuer("API Restful de Contatos").setSubject(userLogado.getUsername())
				.setIssuedAt(dataAtual).setExpiration(dataExpiracao).signWith(SignatureAlgorithm.HS512, secreta)
				.compact();
	}

	private Date getDataExpiracao() {
		return Date.from(LocalDateTime.now().plusSeconds(expiracao).atZone(ZoneId.systemDefault()).toInstant());
	}

	/*
	 * Método responsável em Validar o Token
	 */
	public boolean tokenValido(String token) {

		Claims claims = getClaims(token);
		if (claims != null) {
			String usuario = claims.getSubject();
			Date dataExpiracao = claims.getExpiration();
			Date dataAtual = new Date(System.currentTimeMillis());
			if (usuario != null && dataExpiracao != null && dataAtual.before(dataExpiracao)) {
				return true;
			}
		}
		return false;

	}

	public String getUsuario(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	public boolean validadeToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secreta).parseClaimsJws(token);

			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;

		} catch (SignatureException e) {
			throw new InvalidJwtAuthenticationException("Token JWT expirado ou inválido " + e.getMessage());
		}
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secreta.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
