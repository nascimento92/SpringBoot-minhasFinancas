package br.com.gsn.minhasFinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gsn.minhasFinancas.exception.RegraNegocioExpection;
import br.com.gsn.minhasFinancas.model.entity.Lancamento;
import br.com.gsn.minhasFinancas.model.enums.StatusLancamento;
import br.com.gsn.minhasFinancas.model.repository.LancamentoRepository;
import br.com.gsn.minhasFinancas.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	
	private LancamentoRepository repository;
	
	public LancamentoServiceImpl(LancamentoRepository repository) {
		this.repository=repository;
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		validar(lancamento);
		Objects.requireNonNull(lancamento.getId());
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of(lancamentoFiltro, 
				ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);
	}

	@Override
	public void validar(Lancamento lancamento) {
		
		if(lancamento.getDescricao()==null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioExpection("Informe uma Descrição válida!");
		}
		
		if(lancamento.getMes()==null || lancamento.getMes()<1 || lancamento.getMes()>12) {
			throw new RegraNegocioExpection("Informe um Mês válido!");
		}
		
		if(lancamento.getAno()==null || lancamento.getAno().toString().length()!=4) {
			throw new RegraNegocioExpection("Informe um Ano válido!");
		}
		
		Long id = lancamento.getUsuario().getId();
		
		if(lancamento.getUsuario()==null || id==null) {
			throw new RegraNegocioExpection("Informe um Usuário válido!");
		}
		
		if(lancamento.getValor()==null || lancamento.getValor().compareTo(BigDecimal.ZERO)<1) {
			throw new RegraNegocioExpection("Informe um Valor válido!");
		}
		
		if(lancamento.getTipo()==null) {
			throw new RegraNegocioExpection("Informe um Tipo válido!");
		}
	}

	@Override
	public Optional<Lancamento> obterPorId(Long id) {
		return repository.findById(id);
	}

}
