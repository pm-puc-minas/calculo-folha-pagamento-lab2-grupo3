package br.com.gestaopagamento.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestaopagamento.Models.FolhaPagamento;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {

    Optional<FolhaPagamento> findByFuncionarioCpfAndMes(String cpf, int mes);
    List<FolhaPagamento> findByFuncionarioCpf(String cpf);
    
}
