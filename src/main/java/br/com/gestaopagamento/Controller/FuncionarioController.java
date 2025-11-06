package br.com.gestaopagamento.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

private final FuncionarioService funcionarioService;

public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
}

    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody Funcionario novoFuncionario) {
        Funcionario funcionarioSalvo = funcionarioService.criar(novoFuncionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        List<Funcionario> todos = funcionarioService.listarTodos();
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Funcionario> atualizarFuncionario(@RequestBody Funcionario funcionarioAtualizado) {
        Funcionario funcionario = funcionarioService.atualizar(funcionarioAtualizado);
        return ResponseEntity.ok(funcionario);
    }

}
