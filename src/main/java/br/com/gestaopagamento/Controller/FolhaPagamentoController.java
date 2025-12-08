package br.com.gestaopagamento.Controller;

import br.com.gestaopagamento.Models.CalculoFolhaRequest;
import br.com.gestaopagamento.Models.FolhaPagamento;
import br.com.gestaopagamento.Service.FolhaPagamentoService;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/folha")
public class FolhaPagamentoController {

    private final FolhaPagamentoService folhaPagamentoService;

    public FolhaPagamentoController(FolhaPagamentoService folhaPagamentoService) {
        this.folhaPagamentoService = folhaPagamentoService;
    }

   @PostMapping("/calcular")
public FolhaPagamento calcularFolha(CalculoFolhaRequest request) {
    
    
    return folhaPagamentoService.calcularFolha(
        request.getCpfFuncionario(), 
        request.getMes(), 
        request.getAno(), 
        request.getHorasTrabalhadas()
    );
}
    @GetMapping("/mostrarFolha")
    public String listarFolhas(Authentication auth, Model model) {
        String cpf = auth.getName();
        var folhas = folhaPagamentoService.MostrarTodosSalariosFuncionario(cpf);

        model.addAttribute("folhas", folhas);
        model.addAttribute("cpf", cpf);

        return "ListarFolhas";
    }


    @GetMapping("/folha-de-pagamento/{id}")
    public String mostrarFolha(@PathVariable Long id, Model model) {
        FolhaPagamento folha = folhaPagamentoService.buscarPorId(id);
        model.addAttribute("folha", folha);
        return "FolhaPagamento";
    }

}




