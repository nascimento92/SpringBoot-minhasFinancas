package br.com.gsn.minhasFinancas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public Lancamento salvar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		// TODO Auto-generated method stub
		
	}

}
