package br.com.srsolution.contatos.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.srsolution.contatos.models.Pessoa;
import br.com.srsolution.contatos.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@GetMapping
	public ResponseEntity<List<Pessoa>> exibirTodos() {

		List<Pessoa> pessoas = pessoaService.findTodos();
		return pessoas != null ? ResponseEntity.ok(pessoas) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa) {
		Pessoa salvarPessoa = pessoaService.salvar(pessoa);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvarPessoa);
	}

	@GetMapping("/{codigo}") // -> /pessoas/1
	public ResponseEntity<Optional<Pessoa>> buscarPorCodigo(@PathVariable Long codigo) {

		Optional<Pessoa> pessoa = pessoaService.buscarPorId(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {

		pessoa.setId(codigo);
		pessoa = pessoaService.atualizar(pessoa);
		return ResponseEntity.noContent().build(); // me retorne o código de 204/no Content - sem conteúdo
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> remover(@PathVariable Long codigo) {

		this.pessoaService.findPorCodigo(codigo);
		this.pessoaService.excluir(codigo);
		return ResponseEntity.noContent().build();
	}

}
