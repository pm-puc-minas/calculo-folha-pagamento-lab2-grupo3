package br.com.gestaopagamento.Controller;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.CalculoFolhaRequest;
import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Service.FolhaPagamentoService;
import lombok.Getter;
import lombok.Setter;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/folha")
public class FolhaPagamentoController {

    private final FolhaPagamentoService folhaPagamentoService;

    public FolhaPagamentoController(FolhaPagamentoService folhaPagamentoService) {
        this.folhaPagamentoService = folhaPagamentoService;
    }

    @PostMapping("/calcular")
    public FolhaPagamento calcularFolha(@RequestBody CalculoFolhaRequest request) {
        // Agora você passa os dados brutos para o service
        return folhaPagamentoService.calcularFolha(
            request.getCpfFuncionario(), 
            request.getMes(), 
            request.getHorasTrabalhadas()
        );
    }
    @GetMapping("/mostrar_salario/{cpf}")
    public java.util.List<FolhaPagamento> listarFolhas(@PathVariable String cpf) {
        return folhaPagamentoService.MostrarTodosSalariosFuncionario(cpf);
    }

}




