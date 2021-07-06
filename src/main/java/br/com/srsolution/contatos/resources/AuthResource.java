package br.com.srsolution.contatos.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsolution.contatos.dtos.CredenciaisDTO;
import br.com.srsolution.contatos.models.Usuario;
import br.com.srsolution.contatos.seguranca.JWTUtilitario;
import br.com.srsolution.contatos.services.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthResource {

	@Autowired
	private JWTUtilitario jwtUtilitario;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response, CredenciaisDTO credenciaisDTO) {

		
		UsernamePasswordAuthenticationToken usuarioAutenticadoToken = credenciaisDTO.getToken();
		Authentication authentication = authenticationManager.authenticate(usuarioAutenticadoToken);

		Usuario usuario = UsuarioService.autenticado(); // verifica se o usuário está autenticado
		String token = jwtUtilitario.gerarToken(authentication);

//		String token = "Bearer " + jtwUtil.gerarToken(authentication); // gerarToken(authentication);
		
		//String token = "Bearer " + jwtUtilitario.gerarToken(authentication);
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");

		return ResponseEntity.noContent().build();
		
	}

//	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
//	public ResponseEntity<Void> forgot (@Valid @RequestBody EmailDTO objDTO) {
//		authService.sandNewPassword(objDTO.getEmail());
//		return ResponseEntity.noContent().build();
//	}

}
