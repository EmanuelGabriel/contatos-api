package br.com.srsolution.contatos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.srsolution.contatos.models.Telefone;
import br.com.srsolution.contatos.repository.TelefoneRepository;

@Service
public class TelefoneServico {

	@Autowired
	private TelefoneRepository telefoneRepository;

	public Telefone salvar(Telefone telefone) {
		
		return telefoneRepository.save(telefone);
	}
}
