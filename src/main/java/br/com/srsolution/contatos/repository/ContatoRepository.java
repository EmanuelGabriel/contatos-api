package br.com.srsolution.contatos.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.srsolution.contatos.models.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long>{
	
	

}
