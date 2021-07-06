package br.com.srsolution.contatos.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsolution.contatos.dtos.CredenciaisDTO;
import br.com.srsolution.contatos.seguranca.JWTUtilitario;


@RestController
@RequestMapping("/login")
public class LoginResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtilitario jtwUtil;

	@PostMapping
	public ResponseEntity<CredenciaisDTO> login(@Valid @RequestBody CredenciaisDTO credenciaisDTO,
			HttpServletResponse response) {

		try {

			UsernamePasswordAuthenticationToken userSenhaToken = credenciaisDTO.getToken();
			Authentication authentication = authenticationManager.authenticate(userSenhaToken);

			String token = "Bearer " + jtwUtil.gerarToken(authentication); // gerarToken(authentication);
			response.addHeader("Authorization", token);
			response.setContentType("application/json");
			response.addHeader("Access-Control-Expose-Headers", "Authorization");

			return ResponseEntity.ok().body(credenciaisDTO);

		} catch (AuthenticationException e) {
			e.printStackTrace();
			// Se os dados de login estiverem errados retorna bad request.
			throw new BadCredentialsException("E-mail e/ou senha não conferem!");
		}
	}

}
