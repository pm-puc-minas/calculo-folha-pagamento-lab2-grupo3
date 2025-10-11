package br.com.gestaopagamento.Service;
import br.com.gestaopagamento.Models.Funcionario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final Map<String, Funcionario> funcionarioDB = new HashMap<>();

    public Funcionario criar(Funcionario funcionario) {
        if (funcionarioDB.containsKey(funcionario.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        funcionarioDB.put(funcionario.getCpf(), funcionario);
        System.out.println("Funcionário criado com o CPF: " + funcionario.getCpf());
        return funcionario;
    }

    public Funcionario buscarPorCpf(String cpf) {
        return funcionarioDB.get(cpf);
    }

    public List<Funcionario> listarTodos(){
        return new ArrayList<>(funcionarioDB.values());
    }
    
    public Funcionario atualizar(Funcionario funcionarioAtualizado) {
        String cpf = funcionarioAtualizado.getCpf();
        if (!funcionarioDB.containsKey(cpf)) {
            throw new IllegalArgumentException("Funcionário com CPF " + cpf + " não encontrado");
        }
        funcionarioDB.put(cpf, funcionarioAtualizado);
        return funcionarioAtualizado;
    }
}
