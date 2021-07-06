package br.com.srsolution.contatos.resources;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.srsolution.contatos.models.Cliente;
import br.com.srsolution.contatos.services.ClienteService;

/**
 * 
 * @author emanuel.sousa
 *
 */

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<Iterable<Cliente>> lista() {
		Iterable<Cliente> contatos = clienteService.findTodos();
		return contatos != null ? ResponseEntity.ok(contatos) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteService.salvar(cliente);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(clienteSalvo);

	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Cliente>> buscarPorCodigo(@PathVariable Long codigo) {

		Optional<Cliente> cliente = clienteService.buscarPorId(codigo);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> remover(@PathVariable Long codigo) {
		clienteService.excluir(codigo); // caso não deseja utilizar a classe 'Optional' - usa-se a
		return ResponseEntity.noContent().build(); // retorno VERBO HTTP - CÓDIGO 204 - SEM CONTEÚDO
	}

}
