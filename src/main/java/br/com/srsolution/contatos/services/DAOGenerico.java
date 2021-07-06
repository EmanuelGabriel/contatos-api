package br.com.srsolution.contatos.services;

import java.util.List;
import java.util.Optional;

public interface DAOGenerico<T> {

	T salvar(T tipo);

	Optional<T> buscarPorId(Long id);

	T findPorCodigo(Long codigo);

	T atualizar(T tipo);

	List<T> findTodos();

	void excluir(Long id);

}
