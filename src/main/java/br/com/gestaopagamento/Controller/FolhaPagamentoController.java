package br.com.gestaopagamento.Controller;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Service.FolhaPagamentoService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/folha")
public class FolhaPagamentoController {

    private final FolhaPagamentoService folhaPagamentoService = new FolhaPagamentoService();

    @PostMapping("/calcular")
    public FolhaPagamento calcularFolha(@RequestBody FolhaPagamentoRequest request) {
        return folhaPagamentoService.calcularFolha(request.getFuncionario(), request.getMes(), request.getHorasTrabalhadas());
    }
    @GetMapping("/listarTodos")
    public java.util.List<FolhaPagamento> listarFolhas() {
        return folhaPagamentoService.listarTodos();
    }
}

class FolhaPagamentoRequest {
    private Funcionario funcionario;
    private int mes;
    private BigDecimal horasTrabalhadas;

    public Funcionario getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public int getMes() {
        return mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public BigDecimal getHorasTrabalhadas() {
        return horasTrabalhadas;
    }
    public void setHorasTrabalhadas(BigDecimal horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }
}