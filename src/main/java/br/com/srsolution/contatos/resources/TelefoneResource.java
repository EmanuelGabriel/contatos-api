package br.com.srsolution.contatos.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsolution.contatos.models.Telefone;
import br.com.srsolution.contatos.repository.TelefoneRepository;
import br.com.srsolution.contatos.services.TelefoneServico;

@RestController
@RequestMapping("/telefones")
public class TelefoneResource {

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private TelefoneServico telefoneService;

	@GetMapping
	public ResponseEntity<List<Telefone>> exibirTodos() {

		List<Telefone> lista = telefoneRepository.findAll();
		return lista != null ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Telefone> criar(@Valid @RequestBody Telefone telefone) {

		Telefone tel = telefoneService.salvar(telefone);
		return ResponseEntity.status(HttpStatus.CREATED).body(tel);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Telefone>> buscarPorCodigo(@PathVariable Long codigo) {

		Optional<Telefone> telefone = telefoneRepository.findById(codigo);
		return telefone != null ? ResponseEntity.ok(telefone) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Telefone> atualizar(@PathVariable Long codigo, @Valid @RequestBody Telefone telefone) {

		telefone.setId(codigo);
		telefone = telefoneRepository.save(telefone);
		return ResponseEntity.noContent().build();
	}

}
