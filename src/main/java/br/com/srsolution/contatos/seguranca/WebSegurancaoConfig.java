package br.com.srsolution.contatos.seguranca;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.srsolution.contatos.services.UsuarioDetailsServiceImplementacao;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSegurancaoConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	
//	@Autowired
//	private UserDetailsService userDetailsService;
	
	@Autowired
	private UsuarioDetailsServiceImplementacao usuarioDetailsService;
	
	@Autowired
	private JWTUtilitario jwtUtilitario;
	
	//Informa quais endpoint's são públicos
	private static final String[] LIBERADO_PUBLICO_MATCHERS = { "/h2/**"};
	
	private static final String[] PUBLICO_MATCHES_SOMENTE_GET = { "/contatos/**", "/clientes/", "/compras/**"}; // "/clientes/**"
	
	public static final String[] PUBLICO_MATCHERS_LIBERADO_POST = {"/clientes", "/compras", "/clientes/picture", "/auth/refresh_token", "/auth/forgot/**", "/login", "/v1/usuario"};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		// configuração do banco H2 para que o mesmo seja liberado pelo Spring Security
		if(Arrays.asList(env.getActiveProfiles()).contains("h2dev")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable().cors().configurationSource(corsConfigurationSource());
		
		http.authorizeRequests()
		     //Os caminhos do Public_Matchers_get apenas possibilitará realizar o get como requisição, os outros deve ser feito a autenticação
		    .antMatchers(HttpMethod.GET, PUBLICO_MATCHES_SOMENTE_GET).permitAll() // o usuário pode ver os dados MAS NÃO PODE editar, excluir ou realizar qualquer tipo de operação
		    // Adicionar aqui para as requições do tipo POST
		    .antMatchers(HttpMethod.POST, PUBLICO_MATCHERS_LIBERADO_POST)
		    .permitAll()
		    .antMatchers(LIBERADO_PUBLICO_MATCHERS)
		    .permitAll() // todos os caminhos em PUBLICO_MATCHES serão PÚBLICOS
		    .anyRequest()
		    .authenticated(); // para todas as outras requisições de acesso, o usuário deve está autenticado
		
		
		/*
		 * Realiza os filtros de cada requisição com base no Token gerado
		 */
		http.addFilter(new JWTAutenticacaoFilter(authenticationManager(), jwtUtilitario));
		http.addFilter(new JWTAutorizacaoFilter(authenticationManager(), jwtUtilitario, usuarioDetailsService));
		
		// anotação para informar que o Back-End não criará estados na aplicação
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // serve para não guardar estado da aplicação
		
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	// Realiza a criptografia da senha de cada usuário
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean // Necessário para injetar o ${AuthenticationManager} no controller de login;
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {		
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	

	
}
