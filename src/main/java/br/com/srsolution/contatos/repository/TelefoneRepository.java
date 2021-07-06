package br.com.srsolution.contatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.srsolution.contatos.models.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

}
