package br.com.gsn.minhasFinancas.service;

import java.util.List;
import java.util.Optional;

import br.com.gsn.minhasFinancas.model.entity.Lancamento;
import br.com.gsn.minhasFinancas.model.enums.StatusLancamento;

public interface LancamentoService {

	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	void deletar(Lancamento lancamento);

	List<Lancamento> buscar(Lancamento lancamentoFiltro);

	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);
	
	Optional<Lancamento> obterPorId(Long id);
}
