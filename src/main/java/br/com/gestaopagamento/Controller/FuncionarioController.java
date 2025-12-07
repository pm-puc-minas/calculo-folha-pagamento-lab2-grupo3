package br.com.gestaopagamento.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gestaopagamento.Models.Funcionario;
import br.com.gestaopagamento.Service.FuncionarioService;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // Mantivemos a versão HEAD pois ela manda o objeto vazio necessário para o HTML
    @GetMapping("/novo")
    public String cadastroFuncionario(Model model) { 
        model.addAttribute("funcionario", new Funcionario());
        return "CadastroFuncionario"; 
    }

    // Mantivemos a versão HEAD pois já trata o salvamento corretamente
    @PostMapping
    public String criarFuncionario(Funcionario novoFuncionario, RedirectAttributes redirectAttributes) {
        try {
            // O campo 'dependentes' já vem preenchido automaticamente pelo th:field
            funcionarioService.criar(novoFuncionario);
            
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Funcionário cadastrado com sucesso!");
            return "redirect:/home";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro: " + e.getMessage());
            return "redirect:/funcionarios/novo";
        }
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