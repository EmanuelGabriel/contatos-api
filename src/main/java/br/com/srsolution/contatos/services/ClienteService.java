package br.com.srsolution.contatos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.srsolution.contatos.models.Cliente;
import br.com.srsolution.contatos.repository.ClienteRepository;
import br.com.srsolution.contatos.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class ClienteService implements DAOGenerico<Cliente> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder encriptar;

	@Override
	public Cliente salvar(Cliente cliente) {
		cliente.setSenha(encriptar.encode(cliente.getSenha())); // setando a senha criptografado
		return clienteRepository.save(cliente);
	}

	@Override
	public Optional<Cliente> buscarPorId(Long id) {

		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Cliente não encontrado! - código: " + id);
		}
		return cliente;
	}

	@Override
	public Cliente findPorCodigo(Long codigo) {

		Optional<Cliente> cliente = clienteRepository.findById(codigo);
		return cliente
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Cliente não encontrado! - código: " + codigo));
	}

	@Override
	public Cliente atualizar(Cliente cliente) {
		return null;
	}

	@Override
	public List<Cliente> findTodos() {

		Iterable<Cliente> clientes = clienteRepository.findAll();

		if (clientes == null) {
			throw new ObjetoNaoEncontradoException("Nenhum cliente encontrado! ");
		}
		return (List<Cliente>) clientes;
	}

	@Override
	public void excluir(Long id) {

		findPorCodigo(id);
		clienteRepository.deleteById(id);

	}

}
