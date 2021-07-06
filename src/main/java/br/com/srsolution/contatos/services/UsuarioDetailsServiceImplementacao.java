package br.com.srsolution.contatos.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.srsolution.contatos.models.Cliente;
import br.com.srsolution.contatos.models.Usuario;
import br.com.srsolution.contatos.repository.ClienteRepository;

@Service
public class UsuarioDetailsServiceImplementacao implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Cliente cliente = clienteRepository.findByEmail(email);

		if (cliente == null) {
			throw new UsernameNotFoundException("Cliente com e-mail não encontrado!");
		}

		return new Usuario(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}
	
	
	/**
	 * Monta um {UserDetails} com base no usuário informado e carrega todas as suas
	 * permissões por ID.
	 * 
	 * @param usuario
	 * @return
	 */
//	private UserDetails montarUserDetails(Usuario usuario) {
//		return org.springframework.security.core.userdetails.User
//                .withUsername(usuario.getUsername())
//                .password(usuario.getPassword())
//                .authorities(getPermissoesPorIdUsuario(usuario.getId()))
//                .accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false)
//                .disabled(false)
//                .build();
//	}
//
//	/**
//	 * Retorna todas as Permissões do usuário com id especificado.
//	 * 
//	 * @return
//	 */
//	private Collection<GrantedAuthority> getPermissoesPorIdUsuario(String id) {
//		Set<Permissao> permissoes = permissaoRepository.findAllByUsuariosId(id);
//		Collection<GrantedAuthority> authorities = new ArrayList<>();
//		permissoes.forEach(
//			p -> authorities.add(
//				() -> p.getNomePermissao()
//			)
//		);
//		return authorities;
//	}

}
