package br.com.srsolution.contatos.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.srsolution.contatos.models.Compra;

public interface CompraRepository extends PagingAndSortingRepository<Compra, Long>{

	
}
