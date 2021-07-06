package br.com.srsolution.contatos.resources;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.srsolution.contatos.models.Compra;
import br.com.srsolution.contatos.repository.CompraRepository;

@RestController
@RequestMapping("/compras")
public class CompraResource {

	@Autowired
	private CompraRepository compraRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Compra> criar(@Valid @RequestBody Compra compra) {
		Compra compraSalva = compraRepository.save(compra);
		return ResponseEntity.ok(compraSalva);
	}

	@GetMapping
	public ResponseEntity<Iterable<Compra>> listaCompras() {

		Iterable<Compra> compras = compraRepository.findAll();
		return compras != null ? ResponseEntity.ok(compras) : ResponseEntity.notFound().build();

	}

}
