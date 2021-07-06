package br.com.srsolution.contatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.srsolution.contatos.models.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	

}
