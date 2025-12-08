package br.com.gestaopagamento.Controller;

import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.FolhaPagamentoService;
import br.com.gestaopagamento.Service.FuncionarioService; // 1. Import necessário

@Controller
public class CalcularSalarioController {

    private final FolhaPagamentoService folhaPagamentoService;
    private final FuncionarioService funcionarioService; // 2. Novo Service injetado

    public CalcularSalarioController(FolhaPagamentoService folhaPagamentoService, FuncionarioService funcionarioService) {
        this.folhaPagamentoService = folhaPagamentoService;
        this.funcionarioService = funcionarioService;
    }

    // A rota básica continua existindo
    @GetMapping("/Calcular") 
    public String abrirPagina() {
        return "CalcularSalario"; 
    }

    // 3. NOVO MÉTODO: Recebe o CPF vindo da tela de busca para preencher o formulário
    @GetMapping("/Calcular/{cpf}") 
    public String abrirPaginaPreenchida(@PathVariable("cpf") String cpf, Model model) {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        if (funcionario != null) {
            model.addAttribute("funcionario", funcionario);
        }
        return "CalcularSalario"; 
    }

    // 4. ATUALIZAÇÃO: Agora recebe 'mes' e 'ano' do formulário
    @PostMapping("/Calcular")
    public String realizarCalculo(
            @RequestParam("cpf") String cpf, 
            @RequestParam("mes") int mes, // <--- Novo
            @RequestParam("ano") int ano, // <--- Novo
            @RequestParam("horasTrabalhadas") BigDecimal horasTrabalhadas,
            Model model
    ) {
        try {
            // Passamos os 4 parâmetros para o serviço
            FolhaPagamento resultado = folhaPagamentoService.calcularFolha(cpf, mes, ano, horasTrabalhadas);
            
            model.addAttribute("folha", resultado);
            model.addAttribute("funcionario", resultado.getFuncionario()); // Garante que os dados não somem da tela
            
        } catch (Exception e) {
            model.addAttribute("erro", "Erro: " + e.getMessage());
            // Se der erro, tenta recarregar o funcionário para a tela não ficar vazia
            Funcionario f = funcionarioService.buscarPorCpf(cpf);
            if (f != null) model.addAttribute("funcionario", f);
        }
        
        return "CalcularSalario";
    }
}