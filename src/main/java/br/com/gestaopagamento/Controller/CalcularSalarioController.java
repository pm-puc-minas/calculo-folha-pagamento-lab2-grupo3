package br.com.gestaopagamento.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalcularSalarioController {

    @GetMapping("/Calular") 
    public String abrirPagina() {
        return "CalcularSalario"; 
    }
}