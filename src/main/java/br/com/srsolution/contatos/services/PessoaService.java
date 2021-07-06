package br.com.srsolution.contatos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.srsolution.contatos.models.Pessoa;
import br.com.srsolution.contatos.repository.PessoaRepository;
import br.com.srsolution.contatos.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class PessoaService implements DAOGenerico<Pessoa> {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public Pessoa salvar(Pessoa pessoa) {

//		for(int posicao = 0; posicao < pessoa.getTelefones().size(); posicao++) {
//			pessoa.getTelefones().get(posicao).setPessoa(pessoa);
//		}

		pessoa.getTelefones().forEach(p -> {
			p.setPessoa(pessoa);
		});

		return pessoaRepository.save(pessoa);
	}

	@Override
	public Optional<Pessoa> buscarPorId(Long id) {

		Optional<Pessoa> pessoas = pessoaRepository.findById(id);

		if (pessoas.isEmpty()) {
			throw new ObjetoNaoEncontradoException(
					"Pessoa não encontrado! - código: " + id + " - " + Pessoa.class.getName());
		}

		return pessoas;
	}

	@Override
	public Pessoa findPorCodigo(Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		return pessoa.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Pessoa não encontrado! - código: " + codigo + " - " + Pessoa.class.getName()));
	}

	@Override
	public Pessoa atualizar(Pessoa pessoa) {

		pessoa.getTelefones().forEach(p -> {
			p.setPessoa(pessoa);
		});

		findPorCodigo(pessoa.getId());
		return pessoaRepository.save(pessoa);
	}

	@Override
	public List<Pessoa> findTodos() {

		List<Pessoa> listaPessoas = pessoaRepository.findAll();

		if (listaPessoas.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Nenhuma pessoa encontrada!");
		}

		return listaPessoas;
	}

	@Override
	public void excluir(Long id) {
		buscarPorId(id);
		this.pessoaRepository.deleteById(id);
	}

}
