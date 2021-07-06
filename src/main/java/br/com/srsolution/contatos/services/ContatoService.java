package br.com.srsolution.contatos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.srsolution.contatos.models.Contato;
import br.com.srsolution.contatos.repository.ContatoRepository;
import br.com.srsolution.contatos.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class ContatoService implements DAOGenerico<Contato> {

	@Autowired
	private ContatoRepository contatoRepository;

	@Override
	public Contato salvar(Contato contato) {
		return contatoRepository.save(contato);
	}

	@Override
	public Optional<Contato> buscarPorId(Long codigo) {

		Optional<Contato> contato = contatoRepository.findById(codigo);

		if (contato.isEmpty()) {
			throw new ObjetoNaoEncontradoException(
					"Contato não encontrado! - código: " + codigo + " - " + Contato.class.getName());
		}

		return contato;
	}

	@Override
	public List<Contato> findTodos() {

		List<Contato> listaContatos = contatoRepository.findAll();
		if (listaContatos.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Nenhum contato encontrado!");
		}
		return listaContatos;
	}

	@Override
	public void excluir(Long id) {

		buscarPorId(id);
		this.contatoRepository.deleteById(id);
	}

	@Override
	public Contato atualizar(Contato contato) {
		findPorCodigo(contato.getCodigo());
		return contatoRepository.save(contato);

	}

	@Override
	public Contato findPorCodigo(Long codigo) {

		Optional<Contato> contato = contatoRepository.findById(codigo);
		return contato.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Contato não encontrado! - código: " + codigo + " - " + Contato.class.getName()));

	}

}
