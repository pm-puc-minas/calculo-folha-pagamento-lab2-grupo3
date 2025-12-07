package br.com.gestaopagamento.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastroFuncionarioController {

    @GetMapping("/funcionarios/novo") 
    public String abrirPagina() {
        return "CadastroFuncionario";
    }
}