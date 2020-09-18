 package br.com.gsn.minhasFinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gsn.minhasFinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
