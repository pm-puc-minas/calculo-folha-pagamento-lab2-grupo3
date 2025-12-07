package br.com.gestaopagamento.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.FuncionarioService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

private final FuncionarioService funcionarioService;

public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
}

    @GetMapping("/funcionario")
    public String cadastroFuncionario() { return "CadastroFuncionario"; }

    @PostMapping
    public String criarFuncionario(Funcionario novoFuncionario, RedirectAttributes redirectAttributes) {
        Funcionario funcionarioSalvo = funcionarioService.criar(novoFuncionario);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário criado com sucesso!");

        return "redirect:/home";
    }

    @GetMapping("/listarTodos")
    public String listarFuncionarios(Model model) {
        List<Funcionario> todos = funcionarioService.listarTodos();
        model.addAttribute("funcionarios", todos);
        return "ListarFuncionarios";
    }

    @GetMapping("/editar/{cpf}")
    public String editarFuncionario(@PathVariable String cpf, Model model) {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        model.addAttribute("funcionario", funcionario);
        return "EditarFuncionario";
    }

    @PostMapping("/atualizar")
    public String atualizarFuncionario(Funcionario funcionario, RedirectAttributes redirectAttrs) {
        funcionarioService.atualizar(funcionario);
        redirectAttrs.addFlashAttribute("mensagemSucesso", "Funcionário atualizado com sucesso!");
        return "redirect:/funcionarios/listarTodos";
    }

}
