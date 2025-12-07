package br.com.gestaopagamento.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalcularSalarioController {

    @GetMapping("/Calcular") 
<<<<<<< HEAD
    public String abrirPagina() {
=======
    public String CalcularSalarioPagina() {
>>>>>>> 6098cedf34bc367611a069462cf93931f3c48c5f
        return "CalcularSalario"; 
    }
}