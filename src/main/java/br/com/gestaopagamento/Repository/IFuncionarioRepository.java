package br.com.gestaopagamento.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.gestaopagamento.Models.Funcionario;
import java.util.Optional;

@Repository
public interface IFuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByCpf(String cpf);

}
