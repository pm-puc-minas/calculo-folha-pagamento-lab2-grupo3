package br.com.gestaopagamento.Service;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Repository.IFuncionarioRepository;
import java.util.Optional;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final IFuncionarioRepository funcionarioRepository;

    public FuncionarioService(IFuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario criar(Funcionario funcionario) {
        if (funcionarioRepository.findByCpf(funcionario.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario buscarPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf).orElse(null);
    }

    public List<Funcionario> listarTodos(){
        return funcionarioRepository.findAll();
    }
    
    public Funcionario atualizar(Funcionario funcionarioAtualizado) {
        String cpf = funcionarioAtualizado.getCpf();

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findByCpf(cpf);
        
        if (optionalFuncionario.isEmpty()) {
            throw new IllegalArgumentException("Funcionário com CPF " + cpf + " não encontrado");
        }

        Long idDoFuncionarioExistente = optionalFuncionario.get().getId();
        funcionarioAtualizado.setId(idDoFuncionarioExistente);
        
        return funcionarioRepository.save(funcionarioAtualizado);
    }
}
