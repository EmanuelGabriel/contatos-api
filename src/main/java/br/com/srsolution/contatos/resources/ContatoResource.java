package br.com.srsolution.contatos.resources;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsolution.contatos.models.Contato;
import br.com.srsolution.contatos.repository.ContatoRepository;
import br.com.srsolution.contatos.services.ContatoService;


@RestController
@ExposesResourceFor(Contato.class)
@RequestMapping("/contatos")
public class ContatoResource {

	@Autowired
	private ContatoService contatoServico;

	@Autowired
	private ContatoRepository contatoRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Contato> criar(@Valid @RequestBody Contato contato) {
		Contato contatoSalvo = contatoServico.salvar(contato);
		return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
	}

	@GetMapping
	public Page<Contato> lista(Pageable pages) {
		Page<Contato> contatos = contatoRepository.findAll(pages);
		return contatos;
	}

	@GetMapping("/{codigo}") // --> /contato/1
	public ResponseEntity<Optional<Contato>> buscarPorCodigo(@PathVariable Long codigo) {

		Optional<Contato> contato = contatoServico.buscarPorId(codigo);
		return contato != null ? ResponseEntity.ok(contato) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Contato> atualizar(@PathVariable Long codigo, @Valid @RequestBody Contato contato) {

		contato.setCodigo(codigo);
		contato = contatoServico.atualizar(contato); // .getOne(codigo); caso não deseja utilizar a classe 'Optional'
		return ResponseEntity.noContent().build(); // retorno VERBO HTTP - CÓDIGO 204 - SEM CONTEÚDO

	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> remover(@PathVariable Long codigo) {
		contatoServico.excluir(codigo); // caso não deseja utilizar a classe 'Optional' - usa-se a
		return ResponseEntity.noContent().build(); // retorno VERBO HTTP - CÓDIGO 204 - SEM CONTEÚDO
	}

}
